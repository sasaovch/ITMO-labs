#ifndef IMAGE_H
#define IMAGE_H

#include <stdint.h>

struct __attribute__((packed)) pixel {
    uint8_t b;
    uint8_t g;
    uint8_t r;
};

struct image {
    uint64_t width, height;
    struct pixel *data;
};

struct image create_image(uint64_t width, uint64_t height, struct pixel *data);

void destroy_image(struct image img);

#endif
