package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class DWorkout {
    @NotNull
    public long id;

    @NotNull
    public LocalDate wdate;

    @NotNull
    public String programName;

    @NotNull
    public boolean finished;

    public DWorkout(long id, LocalDate wdate, @NotNull String programName, boolean finished) {
        this.id = id;
        this.wdate = wdate;
        this.programName = programName;
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWorkout dWorkout = (DWorkout) o;
        return id == dWorkout.id &&
                finished == dWorkout.finished &&
                Objects.equals(wdate, dWorkout.wdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wdate, finished);
    }

    @Override
    public String toString() {
        return "DWorkout{" +
                "id=" + id +
                ", wdate=" + wdate +
                ", finished=" + finished +
                '}';
    }
}
