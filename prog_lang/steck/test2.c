#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

enum opcode { BC_PUSH, BC_IPRINT, BC_IREAD, BC_IADD, BC_STOP };

struct maybe_int64 {
  int64_t value;
  int is_valid;
};

struct bc_noarg {
  enum opcode opcode;
};
struct bc_arg64 {
  enum opcode opcode;
  int64_t arg;
};
union ins {
  enum opcode opcode;
  struct bc_arg64 as_arg64;
  struct bc_noarg as_noarg;
};

/* ------------------------ */

struct stack {
    int64_t *data;
    size_t size;
    size_t capacity;
};
struct vm_state {
  const union ins *ip;
  struct stack data_stack;
};

struct stack stack_create(size_t size) {
    struct stack stack;
    stack.data = malloc(size * sizeof(int64_t));
    stack.size = 0;
    stack.capacity = size;
    return stack;
}
typedef void (*func_type)(struct vm_state* state);

/* Начальная вместимость стека задаётся определением STACK_CAPACITY */
struct vm_state state_create(const union ins *ip) {
  return (struct vm_state){.ip = ip,
                           .data_stack = stack_create(10)};
}

// Как правильно деинициализировать состояние, освободить все ресурсы?

void maybe_int64_print(struct maybe_int64 value) {
  if (value.is_valid) {
    printf("%" PRId64, value.value);
    } else {
    printf("error");
    }
}

/* Вы можете использовать эти функции: */
void print_int64(int64_t);
struct maybe_int64 maybe_read_int64() {
    int64_t value;
    if (scanf("%" SCNd64, &value) == 1) {
        return (struct maybe_int64){.value = value, .is_valid = 1};
    } else {
        return (struct maybe_int64){.is_valid = 0};
    }
}

int stack_push(struct stack *s, int64_t value) {
    if (s->size == s->capacity) {
        return 0;
    }
    s->data[s->size] = value;
    s->size++;
    return 1;
}
int64_t stack_pop(struct stack *s) {
    if (s->size == 0) {
        return 0;
    }
    s->size--;
    return s->data[s->size];
}

void interpret_push(struct vm_state* state) {
  stack_push(&state->data_stack, state->ip->as_arg64.arg);
}

void interpret_iread(struct vm_state* state ) {
      stack_push(&(state->data_stack), maybe_read_int64().value);
}
void interpret_iadd(struct vm_state* state ) {
  stack_push(&(state->data_stack), stack_pop(&(state->data_stack)) + stack_pop(&(state->data_stack)));
}
void interpret_iprint(struct vm_state* state ) {
  maybe_int64_print( (struct maybe_int64) {.value = stack_pop(&state->data_stack)});
}

/* Подсказка: можно выполнять программу пока ip != NULL,
    тогда чтобы её остановить достаточно обнулить ip */
void interpret_stop(struct vm_state* state ) {
    state->ip = NULL;
}

func_type interpreters[] = {
  [BC_PUSH] = (func_type)interpret_push,
  [BC_IREAD] = (func_type)interpret_iread,
  [BC_IADD] = (func_type)interpret_iadd,
  [BC_IPRINT] = (func_type)interpret_iprint,
  [BC_STOP] = (func_type)interpret_stop
};

void interpret(struct vm_state* state) {
    size_t ps = 0;
    const union ins *cmd = state->ip;
    while (state->ip != NULL) {
        cmd = state->ip;
        interpreters[cmd->opcode](state);
        if (state->ip != NULL) state->ip = state->ip + 1;
    }
}

void interpret_program(const union ins *program) {
  struct vm_state state = state_create(program);
  interpret(&state);
}

int main() {
  union ins program[] = {
      {.as_arg64 = {.opcode = BC_PUSH, .arg = 42}},
      {.as_arg64 = {.opcode = BC_PUSH, .arg = 13}},
      {.as_arg64 = {.opcode = BC_IADD}},
      {.as_arg64 = {.opcode = BC_IPRINT}},
      {.as_noarg = {.opcode = BC_STOP}},
  };
  interpret_program(program);
  return 0;
}