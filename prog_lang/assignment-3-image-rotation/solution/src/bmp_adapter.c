#include "image_utils.h"
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>


#define BF_TYPE 0x4d42
#define BF_SIZE 14
#define BF_RESERVED 0
#define BF_OFF_BITS (BF_SIZE + BI_SIZE)
#define BI_SIZE 40
#define BI_PLANES 1
#define BI_BIT_COUNT 24
#define BI_COMPRESSION 0
#define X_PELS_PER_METER 2834
#define Y_PELS_PER_METER 2834
#define BI_CLR_USED 0
#define BI_CLR_IMPORTANT 0

static bool check_header_validity(const struct bmp_header *header);

static uint8_t calc_padding(size_t width) { 
    return 4 - (width * 3) % 4; 
}

static size_t calc_data_size(size_t width, size_t height) {
    return (width * 3 + calc_padding(width)) * height;
}

static enum read_status get_data_from_file(struct image *img, struct bmp_header header, FILE *file) {
    uint8_t padding = calc_padding(header.biWidth);
    for (size_t i = header.biHeight; i != 0; i--) {
        size_t stream_read = fread(img->data + (i - 1) * header.biWidth,
                            sizeof(struct pixel) * header.biWidth, 1, file);

        if (stream_read != 1) {
            free(img->data);
            return READ_INVALID_BITS;
        }
        if (fseek(file, +padding, SEEK_CUR) == -1) {
            free(img->data);
            return READ_INVALID_BITS;
        };
    }
    return READ_OK;
}

enum read_status from_bmp(FILE *in_file, struct image *img) {

    struct bmp_header header = {0};
    size_t stream_read = fread(&header, sizeof(struct bmp_header), 1, in_file);
    if (stream_read != 1) { return READ_INVALID_BITS; }
    if (!check_header_validity(&header)) { return READ_INVALID_HEADER; }

    struct pixel *data = malloc(sizeof(struct pixel) * header.biWidth * header.biHeight);
    if (data == NULL) { return MEMORY_ERROR; }
    *img = create_image(header.biWidth, header.biHeight, data);

    return get_data_from_file(img, header, in_file);
}

static struct bmp_header create_bmp_header( struct image const *img ) {
    size_t data_size = calc_data_size(img->width, img->height);
    return (struct bmp_header){
        .bfType = BF_TYPE,
        .bfSize = BF_OFF_BITS + data_size,
        .bfReserved = BF_RESERVED,
        .bfOffBits = BF_OFF_BITS,
        .biSize = BI_SIZE,
        .biWidth = img->width,
        .biHeight = img->height,
        .biPlanes = BI_PLANES,
        .biBitCount = BI_BIT_COUNT,
        .biCompression = BI_COMPRESSION,
        .biSizeImage = data_size,
        .biXPelsPerMeter = X_PELS_PER_METER,
        .biYPelsPerMeter = Y_PELS_PER_METER,
        .biClrUsed = BI_CLR_USED,
        .biClrImportant = BI_CLR_IMPORTANT
    };
}

enum write_status give_data_to_file(struct image const *img, struct bmp_header header, FILE *file) {
    uint8_t padding = calc_padding(img->width);
    for (size_t i = header.biHeight; i != 0; i--) {
        size_t stream_write = fwrite(img->data + (i - 1) * header.biWidth,
                               sizeof(struct pixel) * header.biWidth, 1, file);

        if (stream_write != 1) { return WRITE_ERROR; }
        if (fseek(file, padding, SEEK_CUR) == -1) { return WRITE_ERROR; };
    }
    return WRITE_OK;
}

enum write_status to_bmp(FILE *out_file, struct image const *img) {
    struct bmp_header header = create_bmp_header(img);

    size_t stream_write = fwrite(&header, sizeof(struct bmp_header), 1, out_file);
    if (stream_write != 1) { return WRITE_ERROR; }
    return give_data_to_file(img, header, out_file);
}

static bool check_header_validity(const struct bmp_header *header) {
    size_t data_size = calc_data_size(header->biWidth, header->biHeight);

    if (header->bfType != BF_TYPE) { return false; }
    if (header->bfSize < data_size + BF_OFF_BITS) { return false; }
    if (header->biSizeImage < data_size) { return false; }
    if (header->bfReserved != BF_RESERVED) { return false; }
    if (header->bfOffBits != BF_OFF_BITS) { return false; }
    if (header->biSize != BI_SIZE) { return false; }
    if (header->biPlanes != BI_PLANES) { return false; }
    if (header->biBitCount != BI_BIT_COUNT) { return false; }
    if (header->biCompression != BI_COMPRESSION) { return false; }
    if (header->biXPelsPerMeter != X_PELS_PER_METER) { return false; }
    if (header->biYPelsPerMeter != Y_PELS_PER_METER) { return false; }
    if (header->biClrUsed != BI_CLR_USED) { return false; }
    if (header->biClrImportant != BI_CLR_IMPORTANT) { return false; }

    return true;
}
