#ifndef BMP_ADAPTER_H
#define BMP_ADAPTER_H

#include "image.h"
#include "io_status.h"
#include <stdint.h>
#include <stdio.h>

struct __attribute__((packed)) bmp_header {
    uint16_t bfType;
    uint32_t bfSize;
    uint32_t bfReserved;
    uint32_t bfOffBits;
    uint32_t biSize;
    uint32_t biWidth;
    uint32_t biHeight;
    uint16_t biPlanes;
    uint16_t biBitCount;
    uint32_t biCompression;
    uint32_t biSizeImage;
    uint32_t biXPelsPerMeter;
    uint32_t biYPelsPerMeter;
    uint32_t biClrUsed;
    uint32_t biClrImportant;
};

enum read_status from_bmp(FILE *in, struct image *img);

enum write_status to_bmp(FILE *out, struct image const *img);

#endif
