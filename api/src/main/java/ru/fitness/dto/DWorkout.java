package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public LocalTime totalTime;

    public DWorkout(long id, LocalDate wdate, String programName, boolean finished) {
        this.id = id;
        this.wdate = wdate;
        this.programName = programName;
        this.finished = finished;
    }

    public DWorkout(long id, LocalDate wdate, String programName, boolean finished, LocalTime totalTime) {
        this.id = id;
        this.wdate = wdate;
        this.programName = programName;
        this.finished = finished;
        this.totalTime = totalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWorkout dWorkout = (DWorkout) o;
        return id == dWorkout.id &&
                finished == dWorkout.finished &&
                Objects.equals(wdate, dWorkout.wdate) &&
                Objects.equals(programName, dWorkout.programName) &&
                Objects.equals(totalTime, dWorkout.totalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wdate, programName, finished, totalTime);
    }

    @Override
    public String toString() {
        return "DWorkout{" +
                "id=" + id +
                ", wdate=" + wdate +
                ", programName='" + programName + '\'' +
                ", finished=" + finished +
                ", totalTime=" + totalTime +
                '}';
    }
}
