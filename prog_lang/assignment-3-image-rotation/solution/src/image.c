#include "image.h"
#include <stdlib.h>


struct image create_image(uint64_t width, uint64_t height, struct pixel *data) {
    return (struct image){
        .width = width, 
        .height = height, 
        .data = data
    };
}

void destroy_image(struct image img) { 
    free(img.data); 
}
