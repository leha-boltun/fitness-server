package ru.fitness.logic;

public enum EventCode {
    END("END");

    private final String name;

    EventCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
