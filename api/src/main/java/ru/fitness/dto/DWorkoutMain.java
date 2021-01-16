package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class DWorkoutMain {
    @NotNull
    public int wuserId;

    @NotNull
    public LocalDate wdate;

    @NotNull
    public boolean finished;

    public BigDecimal weight;

    public DWorkoutMain(int wuserId, LocalDate wdate, boolean finished) {
        this.wuserId = wuserId;
        this.wdate = wdate;
        this.finished = finished;
    }

    public DWorkoutMain(int wuserId, LocalDate wdate, boolean finished, BigDecimal weight) {
        this.wuserId = wuserId;
        this.wdate = wdate;
        this.finished = finished;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWorkoutMain that = (DWorkoutMain) o;
        return finished == that.finished &&
                Objects.equals(wdate, that.wdate) &&
                Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wdate, finished, weight);
    }

    @Override
    public String toString() {
        return "DWorkoutMain{" +
                "wdate=" + wdate +
                ", finished=" + finished +
                ", weight=" + weight +
                '}';
    }
}
