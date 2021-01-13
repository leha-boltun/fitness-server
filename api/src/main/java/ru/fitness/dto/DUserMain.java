package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DUserMain {
    @NotNull
    public String name;

    public DUserMain(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DUserMain dUserMain = (DUserMain) o;
        return Objects.equals(name, dUserMain.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "DUserMain{" +
                "name='" + name + '\'' +
                '}';
    }
}
