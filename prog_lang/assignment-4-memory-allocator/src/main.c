
#include "tests.h"
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#define DEBUG_FILE stdout

int main() {
  printf("------------------------\n");
  printf("Runnig tests\n");
  if (run_tests()) {
    printf("All tests passed\n");
  } else {
    printf("Tests failed\n");
  }
}