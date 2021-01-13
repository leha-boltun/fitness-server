package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DUser {
    @NotNull
    public int id;

    @NotNull
    public String name;

    public DUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DUser dUser = (DUser) o;
        return id == dUser.id &&
                name.equals(dUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "DUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
