#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

/* Описание инструкций (см. предыдущий шаг) */
enum opcode { BC_PUSH, BC_IPRINT, BC_IREAD, BC_IADD, BC_STOP };

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

/* Начальная вместимость стека задаётся определением STACK_CAPACITY */
struct vm_state state_create(const union ins *ip) {
  return (struct vm_state){.ip = ip,
                           .data_stack = stack_create(10)};
}

// Как правильно деинициализировать состояние, освободить все ресурсы?



/* Вы можете использовать эти функции: */
void print_int64(int64_t);
struct maybe_int64 maybe_read_int64();

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

/* Опишите цикл интерпретации с выборкой и выполнением команд (пока не выполним STOP) */
void interpret(struct vm_state *state) {
    size_t ps = 0;
    int64_t* value;
    const union ins* cmd = state->ip;
    while ((cmd + ps)->opcode != BC_STOP) {
        switch ((cmd + ps)->opcode) {
            case BC_PUSH: 
                    stack_push(&(state->data_stack), (cmd+ps)->as_arg64.arg);
                    break;
            case BC_IPRINT:
                    printf("%" PRId64, stack_pop(&(state->data_stack)));
                    break;
            case BC_IREAD:
                    scanf("%" PRId64, value);
                    stack_push(&(state->data_stack), *value);
                    break;
            case BC_IADD:
                    stack_push(&(state->data_stack), stack_pop(&(state->data_stack)) + stack_pop(&(state->data_stack)));
                    break;
            default:
                    printf("ERROR");
        }
        ps++;
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