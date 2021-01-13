package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Objects;

public class DTimeStampMain {
    @NotNull
    public LocalTime time;

    @NotNull
    public String type;

    public DTimeStampMain(LocalTime time, String type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTimeStampMain that = (DTimeStampMain) o;
        return Objects.equals(time, that.time) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, type);
    }

    @Override
    public String toString() {
        return "DTimeStampMain{" +
                "time=" + time +
                ", type='" + type + '\'' +
                '}';
    }
}
