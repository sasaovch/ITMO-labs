section .text

extern string_equals

; rdi - Указатель на нуль-терминированную строку.
; rsi - Указатель на начало словаря.
; rax - Возвращает 0, если строка не найдена, иначе возвращает указетль строки в словаре.
global find_word
find_word:
    .loop:
        push rsi ; save rsi
        push rdi ; save rdi
        add rsi, 8

        call string_equals

        pop rdi ; restore rdi
        pop rsi ; restore rsi

        test rax, rax ; if rax != 0 then string is found
        jnz .entry_found

        mov rsi, [rsi]
        test rsi, rsi
        jnz .loop

    .entry_found:
        mov rax, rsi
        ret