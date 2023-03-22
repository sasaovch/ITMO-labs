section .data
%define EXIT_SYSCALL 60
%define WRITE_SYSCALL 1
%define STDOUT 1
%define SYMBOL_LENGTH 1
%define BASE_NUMBER_SYSTEM 10
%define TRANSLATE_TO_ASCII 48
%define TABULAR_CODE 9
%define NEW_LINE_CODE 10
%define SPACE_CODE 32
%define MINUS_CODE 45

global string_equals
global read_char
global print_string
global exit
global print_newline
global string_length

section .text
 
; Принимает код возврата и завершает текущий процесс
; rdi stores the return code
exit: 
    mov rax, EXIT_SYSCALL ; exit syscall
    syscall

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax ; in rax we will store the length of the string
    .loop:
        cmp byte[rdi + rax], 0 ; check if the current symbol is 0
        je .end ; if equal, jump to .end
        inc rax
        jmp .loop ; jump to .loop
    .end:
        ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    push rdi
    call string_length
    pop rdi
    mov rdx, rax ; rdx stores the length of the string
    mov rsi, rdi ; rsi stores pointer to the first symbol of the string
    mov rax, WRITE_SYSCALL ; write syscall
    mov rdi, STDOUT ; stdout
    syscall
    ret

; Принимает код символа и выводит его в stdout
print_char:
    push rdi ; save rdi for use pointer to the string (mov rsi, rdi isn't correct, because system call expects rsi to be a pointer to the first symbol of the string, but not to the code of the symbol)
    mov rsi, rsp ; system call expects the pointer to the string in rsi
    mov rdx, SYMBOL_LENGTH ; the length of string is 1
    mov rax, WRITE_SYSCALL ; write syscall
    mov rdi, STDOUT ; stdout
    syscall
    pop rdi ; restore rdi
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rdi, NEW_LINE_CODE
    call print_char
    ret

; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    mov rax, rdi ; rax stores number
    mov r8, BASE_NUMBER_SYSTEM ; base of the number system
    mov r9, rsp ; save stack pointer

    push 0 ; null-terminator for the string of numbers
    .loop:
        xor rdx, rdx ; rdx stores the remainder of the division
        div r8 ; divide rax by r8, rdx stores the remainder of the division
        add rdx, TRANSLATE_TO_ASCII ; add 48 to the remainder for ASCII code
        dec rsp ; move stack pointer down
        mov byte[rsp], dl ; store the remainder in the stack
        cmp rax, 0 ; if quotient is 0, then we have finished
        jne .loop ; if not, jump to .loop

    .end:
        mov rdi, rsp ; rdi stores pointer to the first symbol of the string for print_string
        push rdi ; save rdi
        call print_string
        pop rdi ; restore rdi
        mov rsp, r9 ; restore stack pointer
        ret

; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    test rdi, rdi ; set flags
    jns .print ; if rdi > 0 print

    push rdi ; save int
    mov rdi, MINUS_CODE ; print '-'
    call print_char

    pop rdi ; restore int
    neg rdi ; convert to abs(int)

    .print:
        call print_uint
        ret

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
    xor rax, rax ; in rax we will store the result
    xor rcx, rcx ; in rcx we will store the pointer to the current symbol
    .loop:
        mov r8b, byte[rdi + rcx]
        cmp r8b, byte[rsi + rcx] ; check if the current symbols are equal
        jne .no ; if not equal, jump to .end
        cmp r8b, 0 ; check if the current symbol is 0
        je .yes ; if not equal, jump to .loop
        inc rcx
        jmp .loop ; jump to .loop
    .yes:
        inc rax
        ret
    .no:
        ret

; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    xor rax, rax ; read syscall
    xor rdi, rdi ; stdin
    mov rdx, SYMBOL_LENGTH ; length of the string
    push 0 ; null-terminator put in the stack
    mov rsi, rsp ; address where the string will be stored
    syscall
    pop rax ; load the symbol from the stack
    ret

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    ; rdi - buffer start address
    ; rsi - buffer size
    xor rcx, rcx ; rcx - current symbol index
    mov r8, rdi ; save buffer start address
    mov r9, rsi ; save buffer size

    .loop:
        push rcx ; save rcx
        push r8 ; save r8
        push r9 ; save r9
        call read_char ; read symbol
        pop r9 ; restore r9
        pop r8 ; restore r8
        pop rcx ; restore rcx

    .check_whitespace:
        cmp al, SPACE_CODE ; check if the char is ' '
        je .skip_or_not ; if it is, jump to .loop
        cmp al, TABULAR_CODE ; check if the char is '\t'
        je .skip_or_not ; if it is, jump to .loop
        cmp al, NEW_LINE_CODE ; check if the char is '\n'
        je .skip_or_not ; if it is, jump to .loop
        jmp .continue

    .continue:
        cmp rax, 0 ; check if the end of the input is reached
        je .end

    .save:
        cmp rcx, r9 ; check if the buffer is full
        jge .error ; if it is, jump to .end
        mov byte[r8 + rcx], al ; save the char in the buffer
        inc rcx
        jmp .loop

    .skip_or_not:
        cmp rcx, 0 ; check if we have already read a word
        je .loop ; if not, jump to .loop

    .end:
        mov byte[r8 + rcx], 0 ; add null-terminator
        mov rax, r8 ; return buffer start address
        mov rdx, rcx ; return word length
        ret

    .error:
        xor rax, rax
        ret

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    ; in r9b we will store the current symbol
    xor rax, rax ; in rax we will store the result
    xor rcx, rcx ; in rcx we will store the number of digits
    mov r8, 10 ; base of the number system
    .loop:
        movzx r9, byte[rdi + rcx] ; extend symbol with 0, save it in r9

        cmp r9b, '0' ; if code of the symbol is less than '0', jump to .end
        jl .end
        cmp r9b, '9' ; if code of the symbol is greater than '9', jump to .end
        jg .end

        mul r8 ; multiply rax by 10
        sub r9b, TRANSLATE_TO_ASCII ; convert to ASCII
        add rax, r9 ; rax = rax*10 + r9
        inc rcx
        jmp .loop

    .end:
        mov rdx, rcx ; save the number of digits in rdx
        ret

; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    xor rax, rax ; in rax we will store the result
    xor r9, r9 ; in r9 we will store the sign
    mov r9b, byte[rdi] ; save the first symbol in r9
    cmp r9b, '-' ; check if the first symbol is '-'
    je .minus 
    jmp .plus

    .minus:
        inc rdi ; move pointer to the next symbol
        call parse_uint ; parse the number
        inc rdx ; increase the number of digits by 1
        neg rax ; negate the number
        ret
    
    .plus:
        call parse_uint ; parse the number
        ret

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
    ; rdi - string start address
    ; rsi - buffer start address
    ; rax - buffer size
    xor rax, rax
    xor rcx, rcx

    .loop:
        mov rax, [rdi + rcx] ; save the current symbol in rax
        cmp rax, 0 ; check if the end of the string is reached
        je .end ; if it is, jump to .end
        cmp rdx, rcx ; check if the buffer is full
        jl .error ; if it is, jump to .error
        mov [rsi + rcx], rax ; save the symbol in the buffer
        inc rcx ; decrease the buffer size by 1
        jmp .loop
    
    .error:
        xor rax, rax
        ret
    
    .end:
        mov byte[rsi + rcx], 0 ; add null-terminator
        inc rcx
        mov rax, rcx ; restore buffer size
        ret
