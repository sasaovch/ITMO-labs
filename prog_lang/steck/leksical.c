#include <stdint.h>
#include <stdlib.h>

enum opcode { 
  BC_PUSH, BC_POP, BC_SWAP, BC_DUP,
  BC_IADD, BC_ISUB, BC_IMUL, BC_IDIV, 
  BC_INEG,
  BC_IPRINT, BC_IREAD, 
  BC_ICMP, 
  BC_STOP 
};

// Инструкция в одном из двух форматов: bc_noarg или bc_arg64
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

struct vm_state {
  const union ins *ip;
  struct stack data_stack;
};

struct stack {
    int64_t *data;
    size_t size;
    size_t capacity;
};