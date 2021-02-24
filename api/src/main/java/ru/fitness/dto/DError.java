package ru.fitness.dto;

import javax.validation.constraints.NotNull;

public class DError {
    @NotNull
    public String message;

    public DError(String message) {
        this.message = message;
    }
}
