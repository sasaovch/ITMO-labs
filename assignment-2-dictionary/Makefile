target := main

obj_dir := obj

obj_files := $(patsubst %.asm, $(obj_dir)/%.o, $(wildcard *.asm))

all : $(target)

$(target) : $(obj_files)
	ld -o $@ $(obj_files)

$(obj_dir)/%.o : %.asm
	mkdir -p $(obj_dir)
	nasm -felf64 -o $@ $<

clean :
	rm -rf $(obj_dir) 
	rm -f $(target)