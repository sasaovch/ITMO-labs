%define dict_head 0

%macro colon 2
    %ifid %2 ; check if second argument is a identifier
        %2: dq dict_head ; if it is, set it to the current head
    %else
        %error "Second paramenter should be an identifier"
    %endif
    %ifstr %1
        db %1, 0 ; if it is, set it to the current head
    %else
        %error "First paramenter should be a string"
    %endif
    %define dict_head %2 
    ; set the head to the second argument
%endmacro