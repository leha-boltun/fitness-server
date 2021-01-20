package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DExer {
    public Long id;

    public Long prevId;

    @NotNull
    public String name;

    public DExer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DExer(Long id, String name, Long prevId) {
        this.id = id;
        this.name = name;
        this.prevId = prevId;
    }

    public DExer(String name, Long prevId) {
        this.name = name;
        this.prevId = prevId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DExer dExer = (DExer) o;
        return Objects.equals(id, dExer.id) &&
                Objects.equals(prevId, dExer.prevId) &&
                Objects.equals(name, dExer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prevId, name);
    }

    @Override
    public String toString() {
        return "DExer{" +
                "id=" + id +
                ", prevId=" + prevId +
                ", name='" + name + '\'' +
                '}';
    }
}
