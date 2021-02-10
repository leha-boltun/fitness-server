package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DWorkoutMain {
    @NotNull
    public int wuserId;

    @NotNull
    public LocalDate wdate;

    @NotNull
    public boolean finished;

    public BigDecimal weight;

    public BigDecimal weightDiff;

    public BigDecimal weightDiffSame;

    public LocalTime totalTime;

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

    public DWorkoutMain(int wuserId, LocalDate wdate, boolean finished,
                        BigDecimal weight, LocalTime totalTime,
                        BigDecimal weightDiff, BigDecimal weightDiffSame) {
        this.wuserId = wuserId;
        this.wdate = wdate;
        this.finished = finished;
        this.weight = weight;
        this.totalTime = totalTime;
        this.weightDiff = weightDiff;
        this.weightDiffSame = weightDiffSame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWorkoutMain that = (DWorkoutMain) o;
        return wuserId == that.wuserId &&
                finished == that.finished &&
                Objects.equals(wdate, that.wdate) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(totalTime, that.totalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wuserId, wdate, finished, weight, totalTime);
    }

    @Override
    public String toString() {
        return "DWorkoutMain{" +
                "wuserId=" + wuserId +
                ", wdate=" + wdate +
                ", finished=" + finished +
                ", weight=" + weight +
                ", totalTime=" + totalTime +
                '}';
    }
}
