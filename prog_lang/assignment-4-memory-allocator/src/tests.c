#include "tests.h"

#define DEBUG_FILE stderr
#define TEST_1_CAPACITY 1024
#define DEBUG_OUTPUT stderr
#define OUTPUT stdout
#define HEAP_SIZE 16384
#define TEST2_SIZE 1024
#define TEST3_SIZE 512
#define TEST4_SIZE 1024

static void *get_content(struct block_header *block) {
  return (struct block_header *)((void *)block +
                                 offsetof(struct block_header, contents));
}

static struct block_header *get_header(void *content) {
  return (struct block_header *)(content -
                                 offsetof(struct block_header, contents));
}

void free_block(void *block) { _free(get_content(block)); }

void print_test_init_heap() {
  fprintf(OUTPUT, "Test 1: init heap \n");
  fprintf(OUTPUT, "Init heap with size %d \n", REGION_MIN_SIZE);
  fprintf(OUTPUT, "Malloc %d bytes \n", TEST_1_CAPACITY);
}

void print_test_free_one_block() {
  fprintf(OUTPUT, "Test 2: free one block \n");
  fprintf(OUTPUT, "Init heap with size %d \n", HEAP_SIZE);
  fprintf(OUTPUT, "Malloc %d bytes \n", TEST2_SIZE);
}

void print_test_free_two_blocks() {
  fprintf(OUTPUT, "Test 3: free two blocks \n");
  fprintf(OUTPUT, "Init heap with size %d \n", HEAP_SIZE);
  fprintf(OUTPUT, "Malloc %d bytes \n", TEST3_SIZE);
}

void print_test_expand_region() {
  fprintf(OUTPUT, "Test 4: expand region \n");
  fprintf(OUTPUT, "Init heap with size %d \n", REGION_MIN_SIZE);
  fprintf(OUTPUT, "Malloc %d bytes \n", TEST4_SIZE);
}

void print_test_expand_region_after() {
  fprintf(OUTPUT, "Test 5: expand region after \n");
  fprintf(OUTPUT, "Init heap with size %d \n", REGION_MIN_SIZE);
  fprintf(OUTPUT, "Malloc %d bytes \n", REGION_MIN_SIZE);
}

static bool test_init_heap() {
  print_test_init_heap();
  void *heap = heap_init(REGION_MIN_SIZE);
  void *attempt = _malloc(TEST_1_CAPACITY);

  if (heap == NULL || attempt == NULL) {
    return false;
  }

  debug_heap(OUTPUT, heap);
  _free(attempt);
  munmap(heap,
         size_from_capacity((block_capacity){.bytes = REGION_MIN_SIZE}).bytes);
  return true;
}

static bool test_free_one_block() {
  print_test_free_one_block();

  void *heap = heap_init(HEAP_SIZE);
  void *block1 = _malloc(TEST2_SIZE);
  void *block2 = _malloc(TEST2_SIZE);
  void *block3 = _malloc(TEST2_SIZE);
  struct block_header *block_header1 = get_header(block1);
  struct block_header *block_header2 = get_header(block2);
  struct block_header *block_header3 = get_header(block3);

  if (block1 == NULL || block_header1->capacity.bytes < TEST2_SIZE) {
    return false;
  }
  if (block2 == NULL || block_header2->capacity.bytes < TEST2_SIZE) {
    return false;
  }
  if (block3 == NULL || block_header3->capacity.bytes < TEST2_SIZE) {
    return false;
  }

  debug_heap(OUTPUT, heap);
  free_block(block1);
  if (block_header1->is_free == 0) {
    return false;
  }
  debug_heap(OUTPUT, heap);

  free_block(block3);
  free_block(block2);
  munmap(heap, size_from_capacity(block_header2->capacity).bytes);
  return true;
}

static bool test_free_two_blocks() {

  void *heap = heap_init(HEAP_SIZE);
  void *block1 = _malloc(TEST3_SIZE);
  void *block2 = _malloc(TEST3_SIZE);
  void *block3 = _malloc(TEST3_SIZE);
  struct block_header *block_header1 = get_header(block1);
  struct block_header *block_header2 = get_header(block2);
  struct block_header *block_header3 = get_header(block3);

  if (block1 == NULL || block_header1->capacity.bytes < TEST3_SIZE) {
    return false;
  }
  if (block2 == NULL || block_header2->capacity.bytes < TEST3_SIZE) {
    return false;
  }
  if (block3 == NULL || block_header3->capacity.bytes < TEST3_SIZE) {
    return false;
  }
  debug_heap(OUTPUT, heap);

  free_block(block2);
  if (block_header2->is_free == 0) {
    return false;
  }

  free_block(block3);
  if (block_header3->is_free == 0) {
    return false;
  }

  debug_heap(OUTPUT, heap);
  free_block(block1);
  munmap(heap, size_from_capacity(block_header1->capacity).bytes);
  return true;
}

static bool test_expand_region() {
  void *heap = heap_init(REGION_MIN_SIZE);
  void *block1 = _malloc(REGION_MIN_SIZE);
  struct block_header *block_header1 = get_header(block1);

  if (block1 == NULL || block_header1->capacity.bytes < REGION_MIN_SIZE) {
    return false;
  }

  debug_heap(OUTPUT, heap);

  free_block(block_header1);
  void *block2 = _malloc(TEST4_SIZE);
  struct block_header *block_header2 = get_header(block2);

  if (block2 == NULL || block_header2->capacity.bytes < TEST4_SIZE) {
    return false;
  }

  debug_heap(OUTPUT, heap);
  free_block(block_header2);
  munmap(heap, size_from_capacity(block_header2->capacity).bytes);
  return true;
}

static bool test_expand_region_after() {
  void *heap = heap_init(REGION_MIN_SIZE);
  void *block1 = _malloc(REGION_MIN_SIZE);
  struct block_header *block_header1 = get_header(block1);

  if (block1 == NULL || block_header1->capacity.bytes < REGION_MIN_SIZE) {
    return false;
  }

  debug_heap(OUTPUT, heap);
  void *addr = mmap(block_header1->contents + block_header1->capacity.bytes,
                    REGION_MIN_SIZE, PROT_READ | PROT_WRITE,
                    MAP_PRIVATE | MAP_ANONYMOUS | MAP_FIXED_NOREPLACE, -1, 0);

  if (addr == MAP_FAILED) {
    return false;
  }

  void *block2 = _malloc(REGION_MIN_SIZE);
  struct block_header *block_header2 = get_header(block2);
  debug_heap(OUTPUT, heap);

  if (block_header1->next != block_header2 ||
      block_header1->contents + block_header1->capacity.bytes ==
          (void *)block_header2) {
    return false;
  }

  free_block(block_header1);
  free_block(block_header2);
  munmap(heap, REGION_MIN_SIZE);
  munmap(addr, REGION_MIN_SIZE);
  return false;
}

test_func testers[] = {
    test_init_heap,     test_free_one_block,      test_free_two_blocks,
    test_expand_region, test_expand_region_after,
};

int run_tests() {
  int failed = 0;
  for (size_t i = 0; i < sizeof(testers) / sizeof(testers[0]); i++) {
    printf("Running test %zu \n", i);
    if (testers[i]()) {
      printf("Test %zu passed \n", i);
    } else {
      fprintf(OUTPUT, "Test %zu failed \n", i);
      failed++;
    }
  }
  return failed;
}