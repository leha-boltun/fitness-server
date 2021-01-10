package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class DWorkout {
    @NotNull
    public final long id;

    @NotNull
    public final LocalDate wdate;

    public DWorkout(long id, LocalDate wdate) {
        this.id = id;
        this.wdate = wdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWorkout dWorkout = (DWorkout) o;
        return Objects.equals(id, dWorkout.id) &&
                Objects.equals(wdate, dWorkout.wdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wdate);
    }

    @Override
    public String toString() {
        return "DWorkout{" +
                "id=" + id +
                ", wdate=" + wdate +
                '}';
    }
}
