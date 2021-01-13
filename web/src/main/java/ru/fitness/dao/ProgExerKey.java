package ru.fitness.dao;

import java.io.Serializable;
import java.util.Objects;

public class ProgExerKey implements Serializable {
    private Long prog;

    private Long exer;

    public Long getProg() {
        return prog;
    }

    public void setProg(Long prog) {
        this.prog = prog;
    }

    public Long getExer() {
        return exer;
    }

    public void setExer(Long exer) {
        this.exer = exer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgExerKey that = (ProgExerKey) o;
        return Objects.equals(prog, that.prog) &&
                Objects.equals(exer, that.exer);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
