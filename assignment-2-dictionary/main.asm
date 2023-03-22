%define BUFFER_SIZE 256
%define NEW_LINE_CODE 10


%include 'words.inc'
%include 'lib.inc'

section .rodata
buffer_overflow_msg: db "Buffer overflow detected. Input is longer than 255 characters", 0xA, 0
not_found_msg: db "The key you are looking for is not in the dictionary", 0xA, 0

section .bss
buffer: resb BUFFER_SIZE

section .text

global _start

_start:
    ; save rsp
    xor rcx, rcx ; rcx - counter for chars

.read_word:
    push rcx ; save rcx
    call read_char
    pop rcx ; restore rcx

    cmp al, 0 ; check if we have reached the end of the word
    je .end_of_word
    cmp al, NEW_LINE_CODE ; check if we have reached the end of the line
    je .end_of_word

    mov [buffer + rcx], al ; save char to buffer
    inc rcx ; increment counter

    cmp rcx, BUFFER_SIZE ; check if we have reached the end of the buffer
    jne .read_word

    mov rdi, buffer_overflow_msg ; set message to print
    call print_string

    mov rdi, 1 ; error code
    call exit

.end_of_word:
    mov byte[buffer + rcx] , 0 ; add null terminator

    mov rsi, dict_head ; rsi - pointer to the first word in the dictionary
    mov rdi, buffer ; rdi - word
    call find_word ; find word in dictionary
    
    test rax, rax ; check if word was found
    jz .not_found

    mov rdi, rax ; rdi - word
    add rdi, 8
    push rdi
    call string_length
    pop rdi
    lea rdi, [rdi + rax + 1]
    call print_string
    call print_newline
    mov rdi, 0 ; success code
    call exit

.not_found:
	mov rdi, not_found_msg
	call print_string

	mov rdi, 0 ; success code
	call exit