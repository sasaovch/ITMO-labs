#include "image_utils.h"
#include "bmp_adapter.h"
#include <stdlib.h>


enum read_status (*readers[]) (FILE *, struct image *) = {
    [BMP] = from_bmp,
};

enum write_status (*writers[]) (FILE *, struct image const *) = {
    [BMP] = to_bmp,
};

enum read_status read_image(enum image_types type, FILE *in, struct image *img) {
    return readers[type](in, img);
}

enum write_status write_image(enum image_types type, FILE *out, const struct image *img) {
    return writers[type](out, img);
}

struct image rotate_image(struct image const source) {
    struct pixel *rotated_data = malloc(sizeof(struct pixel) * source.width * source.height);
    if (rotated_data == NULL) {
        return create_image(0, 0, NULL);
    }

    for (size_t i = 0; i < source.height; i++) {
        for (size_t j = 0; j < source.width; j++) {
            rotated_data[(source.width - j - 1) * source.height + i] =
                source.data[i * source.width + j];
        }
    }

    struct image rotatedImg = create_image(source.height, source.width, rotated_data);
    return rotatedImg;
}
