package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DNextEvent {
    @NotNull
    public String name;

    @NotNull
    public boolean canAddWsets;

    public DNextEvent(String name, boolean canAddWsets) {
        this.name = name;
        this.canAddWsets = canAddWsets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNextEvent that = (DNextEvent) o;
        return canAddWsets == that.canAddWsets &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, canAddWsets);
    }

    @Override
    public String toString() {
        return "DNextEvent{" +
                "name='" + name + '\'' +
                ", canAddWsets=" + canAddWsets +
                '}';
    }
}
