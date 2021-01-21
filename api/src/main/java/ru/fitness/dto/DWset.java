package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DWset {
    @NotNull
    public long id;

    @NotNull
    public String weight;

    @NotNull
    public String count;

    public DWset() {
    }

    public DWset(String weight, String count, long id) {
        this.weight = weight;
        this.count = count;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWset dWset = (DWset) o;
        return id == dWset.id &&
                Objects.equals(weight, dWset.weight) &&
                Objects.equals(count, dWset.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, count);
    }

    @Override
    public String toString() {
        return "DWset{" +
                "id=" + id +
                ", weight='" + weight + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
