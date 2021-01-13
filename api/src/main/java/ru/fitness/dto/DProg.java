package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DProg {
    @NotNull
    public long id;

    @NotNull
    public String name;

    public DProg(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DProg dProg = (DProg) o;
        return id == dProg.id &&
                Objects.equals(name, dProg.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "DProg{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
