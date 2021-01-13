package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DWset {
    @NotNull
    public String weight;

    @NotNull
    public String count;

    public DWset() {
    }

    public DWset(String weight, String count) {
        this.weight = weight;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWset dWset = (DWset) o;
        return Objects.equals(weight, dWset.weight) &&
                Objects.equals(count, dWset.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, count);
    }

    @Override
    public String toString() {
        return "DWset{" +
                "weight='" + weight + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
