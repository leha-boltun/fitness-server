package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class DWorkoutMain {
    @NotNull
    public LocalDate wdate;

    @NotNull
    public boolean finished;

    public DWorkoutMain(LocalDate wdate, boolean finished) {
        this.wdate = wdate;
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWorkoutMain that = (DWorkoutMain) o;
        return finished == that.finished &&
                Objects.equals(wdate, that.wdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wdate, finished);
    }

    @Override
    public String toString() {
        return "DWorkoutMain{" +
                "wdate=" + wdate +
                ", finished=" + finished +
                '}';
    }
}
