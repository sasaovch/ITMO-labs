#include "image.h"
#include "image_utils.h"
#include <errno.h>
#include <stdint.h>
#include <stdio.h>


int read_image_from_file(char* filepath, struct image* img);
int write_image_to_file(char* filepath, struct image* img);

const char *read_err_msg[] = {
    [READ_INVALID_SIGNATURE] = "Invalid file signature",
    [READ_INVALID_BITS] = "Invalid data",
    [READ_INVALID_HEADER] = "Invalid file header"
};

const char *write_err_msg[] = {
    [WRITE_ERROR] = ""
};

int main(int argc, char **argv) {

    if (argc < 3) {
        fprintf(stderr, "Usage: image-transformer <input file> <output file>\n");
        return 1;
    }

    struct image in_image = {0};
    if (read_image_from_file(argv[1], &in_image)) {
        return 1;
    }

    struct image out_image = rotate_image(in_image);
    if (out_image.data == NULL) {
        fprintf(stderr, "Failed to rotate image\n");
        return 1;
    }
    destroy_image(in_image);
    
    if (write_image_to_file(argv[2], &out_image)) {
        return 1;
    }
    destroy_image(out_image);

    return 0;
}

int read_image_from_file(char* filepath, struct image* img) {
    FILE *in_file = fopen(filepath, "rb");

    if (in_file == NULL) {
        fprintf(stderr,
                "Failed to open source file\n");
        return 1;
    }

    enum read_status r_status = read_image(BMP, in_file, img);
    if (r_status) {
        fprintf(stderr,
                "An error occured when trying to read file. %s\n", read_err_msg[r_status]);
        return 1;
    }

    if (fclose(in_file) != 0) {
            fprintf(stderr,
                "Failed to close source file\n");
        return 1;
    }
    return 0;
}

int write_image_to_file(char* filepath, struct image* img) {
    FILE *out_file = fopen(filepath, "wb");

    if (out_file == NULL) {
        fprintf(stderr,
                "Failed to open target file\n");
        return 1;
    }

    enum write_status w_status = write_image(BMP, out_file, img);
    if (w_status) {
        fprintf(stderr,
                "An error occured when trying to write file. %s\n", write_err_msg[w_status]);
        return 1;
    }

    if (fclose(out_file) != 0) {
        fprintf(stderr,
            "Failed to close target file\n");
        return 1;
    }
    return 0;
}
