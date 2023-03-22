#ifndef IMAGE_IO_H
#define IMAGE_IO_H

#include "bmp_adapter.h"

enum image_types { BMP };

enum read_status read_image(enum image_types type, FILE *in, struct image *img);

enum write_status write_image(enum image_types type, FILE *out, const struct image *img);

struct image rotate_image(struct image const source);

#endif
