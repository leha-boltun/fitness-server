package ru.fitness.logic;

public enum EventCode {
    BEFORE_BEGIN("BEFORE_BEGIN"),
    BEGIN("BEGIN"),
    END("END");

    private final String name;

    EventCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
