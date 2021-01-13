package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DNextEvent {
    @NotNull
    public String name;

    public DNextEvent(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNextEvent that = (DNextEvent) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "DNextEvent{" +
                "name='" + name + '\'' +
                '}';
    }
}
