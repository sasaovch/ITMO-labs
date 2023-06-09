#include <stddef.h>
#define _DEFAULT_SOURCE

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "mem.h"
#include "mem_internals.h"
#include "util.h"

void debug_block(struct block_header *b, const char *fmt, ...);
void debug(const char *fmt, ...);

extern inline block_size size_from_capacity(block_capacity cap);
extern inline block_capacity capacity_from_size(block_size sz);

static bool block_is_big_enough(size_t query, struct block_header *block) {
  return block->capacity.bytes >= query;
}
static size_t pages_count(size_t mem) {
  return mem / getpagesize() + ((mem % getpagesize()) > 0);
}
static size_t round_pages(size_t mem) {
  return getpagesize() * pages_count(mem);
}

static void block_init(void *restrict addr, block_size block_sz,
                       void *restrict next) {
  *((struct block_header *)addr) = (struct block_header){
      .next = next, .capacity = capacity_from_size(block_sz), .is_free = true};
}

static size_t region_actual_size(size_t query) {
  return size_max(round_pages(query), REGION_MIN_SIZE);
}

extern inline bool region_is_invalid(const struct region *r);

static void *map_pages(void const *addr, size_t length, int additional_flags) {
  return mmap((void *)addr, length, PROT_READ | PROT_WRITE,
              MAP_PRIVATE | MAP_ANONYMOUS | additional_flags, -1, 0);
}

/*  аллоцировать регион памяти и инициализировать его блоком */
static struct region alloc_region(void const *addr, size_t query) {
  size_t actual_size =
      region_actual_size(size_from_capacity((block_capacity){query}).bytes);

  /* create mapping with fixed address */
  /*  MAP_FIXED_NOREPLACE - не перезаписывать существующую память */
  void *mapped_pages = map_pages(addr, actual_size, MAP_FIXED_NOREPLACE);

  /* if mapping failed, try to map without fixed address */
  if (mapped_pages == MAP_FAILED) {
    mapped_pages = map_pages(addr, actual_size, 0);
    /* if mapping failed again, return invalid region */
    if (mapped_pages == MAP_FAILED) {
      return REGION_INVALID;
    }
  }

  struct region alloc_region = {.addr = mapped_pages,
                                .size = actual_size,
                                .extends = addr == mapped_pages};

  block_init(mapped_pages, (block_size){alloc_region.size}, NULL);

  return alloc_region;
}

static void *block_after(struct block_header const *block);

void *heap_init(size_t initial) {
  const struct region region = alloc_region(HEAP_START, initial);
  if (region_is_invalid(&region))
    return NULL;

  return region.addr;
}

#define BLOCK_MIN_CAPACITY 24

/*  --- Разделение блоков (если найденный свободный блок слишком большой )--- */

static bool block_splittable(struct block_header *restrict block,
                             size_t query) {
  return block->is_free &&
         query + offsetof(struct block_header, contents) + BLOCK_MIN_CAPACITY <=
             block->capacity.bytes;
}

static bool split_if_too_big(struct block_header *block, size_t query) {

  if (block_splittable(block, query)) {
    void *new_block_addr = block->contents + query;
    block_init(new_block_addr, (block_size){block->capacity.bytes - query},
               block->next);
    block->capacity.bytes = query;
    block->next = (struct block_header *)(new_block_addr);
    return true;
  }

  return false;
}

/*  --- Слияние соседних свободных блоков --- */

static void *block_after(struct block_header const *block) {
  return (void *)(block->contents + block->capacity.bytes);
}
static bool blocks_continuous(struct block_header const *fst,
                              struct block_header const *snd) {
  return (void *)snd == block_after(fst);
}

static bool mergeable(struct block_header const *restrict fst,
                      struct block_header const *restrict snd) {
  return fst->is_free && snd->is_free && blocks_continuous(fst, snd);
}

static bool try_merge_with_next(struct block_header *block) {
  struct block_header *curr_block = block;
  struct block_header *next_block = block->next;

  if (next_block != NULL && mergeable(curr_block, next_block)) {
    curr_block->capacity.bytes +=
        size_from_capacity(next_block->capacity).bytes;
    curr_block->next = next_block->next;
    return true;
  }

  return false;
}

/*  --- ... ecли размера кучи хватает --- */

struct block_search_result {
  enum { BSR_FOUND_GOOD_BLOCK, BSR_REACHED_END_NOT_FOUND, BSR_CORRUPTED } type;
  struct block_header *block;
};

static struct block_search_result
find_good_or_last(struct block_header *restrict block, size_t sz) {
  while (block != NULL) {

    /* Merge blocks until we can't merge */
    while (try_merge_with_next(block))
      ;

    /* Finish merge */
    if (block->is_free && block_is_big_enough(sz, block)) {
      return (struct block_search_result){BSR_FOUND_GOOD_BLOCK, block};
    }

    if (block->next == NULL) {
      return (struct block_search_result){BSR_REACHED_END_NOT_FOUND, block};
    }

    block = block->next;
  }

  return (struct block_search_result){BSR_CORRUPTED, block};
}

/*  Попробовать выделить память в куче начиная с блока `block` не пытаясь
 расширить кучу Можно переиспользовать как только кучу расширили. */
static struct block_search_result
try_memalloc_existing(size_t query, struct block_header *block) {

  struct block_search_result block_search_result =
      find_good_or_last(block, query);

  if (block_search_result.type == BSR_FOUND_GOOD_BLOCK) {
    split_if_too_big(block_search_result.block, query);
    block_search_result.block->is_free = false;
  }

  return block_search_result;
}

static struct block_header *grow_heap(struct block_header *restrict last,
                                      size_t query) {
  if (last == NULL) {
    return NULL;
  }

  void *next_block = block_after(last);
  /* Allocate region */
  struct region region = alloc_region(next_block, query);

  if (region_is_invalid(&region)) {
    return NULL;
  }

  if (last->is_free && region.extends) {
    last->capacity.bytes += region.size;
    return last;
  }

  last->next = region.addr;
  return region.addr;
}

/*  Реализует основную логику malloc и возвращает заголовок выделенного блока */
static struct block_header *memalloc(size_t query,
                                     struct block_header *heap_start) {

  size_t query_size = size_max(query, BLOCK_MIN_CAPACITY);
  struct block_search_result block_search_result =
      try_memalloc_existing(query_size, heap_start);

  while (block_search_result.type == BSR_REACHED_END_NOT_FOUND) {

    struct block_header *last =
        grow_heap(block_search_result.block, query_size);
    if (last == NULL) {
      return NULL;
    }
    block_search_result = try_memalloc_existing(query_size, last);
  }

  if (block_search_result.type == BSR_FOUND_GOOD_BLOCK) {
    return block_search_result.block;
  }
  return NULL;
}

void *_malloc(size_t query) {
  struct block_header *const addr =
      memalloc(query, (struct block_header *)HEAP_START);
  if (addr)
    return addr->contents;
  else
    return NULL;
}

static struct block_header *block_get_header(void *contents) {
  return (struct block_header *)(((uint8_t *)contents) -
                                 offsetof(struct block_header, contents));
}

void _free(void *mem) {
  if (!mem)
    return;
  struct block_header *header = block_get_header(mem);
  header->is_free = true;
  while (try_merge_with_next(header))
    ;
}
