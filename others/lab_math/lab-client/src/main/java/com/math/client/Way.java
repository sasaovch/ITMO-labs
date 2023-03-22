package com.math.client;

public enum Way {
    RECTANGLE("rectangle"),
    TRAPEZOID("trapezoid"),
    SIMPSON("Simpson");

    private String name;

    Way(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
