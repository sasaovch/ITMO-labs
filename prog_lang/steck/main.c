#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include "leksical.c"

const union ins program[] = {
  { BC_IREAD },
  { BC_IREAD },
  { BC_DUP },
  { .as_arg64 = { BC_PUSH, .arg = 10 } },
  { BC_IMUL },
  { BC_IADD },
  { BC_IADD },
  { BC_IPRINT },
  { BC_STOP }
  };