package com.lrparser;

public enum TokeEnum {
    EQUATION("EQUATION"),
    EXPRESSION("EXPRESSION"),
    MULTIPLYEXP("MULTIPLYEXP"),
    ATOM("ATOM"),
    SCINTIFIC_NUMBER("SCINTIFIC_NUMBER"),
    NUMBER("NUMBER"),
    LPAREN("("),
    RPAREN(")"),
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIV("/"),
    WS("WS"),
    EOF("EOF");
    private String value;
    private TokeEnum(String value) {
        this.value = value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
