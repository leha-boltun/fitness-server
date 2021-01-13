package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DExer {
    @NotNull
    public long id;

    @NotNull
    public String name;

    public DExer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DExer dExer = (DExer) o;
        return id == dExer.id &&
                Objects.equals(name, dExer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "DExer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
