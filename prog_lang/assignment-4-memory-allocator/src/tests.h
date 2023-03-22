
#ifndef TESTS_H
#define TESTS_H

#include <stdint.h>
#define __USE_MISC 1

#include <sys/mman.h>

#include "mem.h"
#include "mem_internals.h"
#include "util.h"
#include <stdbool.h>

typedef bool (*test_func)();

int run_tests();

#endif