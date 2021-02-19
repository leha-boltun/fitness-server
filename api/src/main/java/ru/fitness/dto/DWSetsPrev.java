package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DWSetsPrev {
    public Long prevId;
    @NotNull
    public List<DWset> wsets;

    public LocalDate date;

    public DWSetsPrev(List<DWset> wsets, Long prevId, LocalDate date) {
        this.wsets = wsets;
        this.prevId = prevId;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWSetsPrev that = (DWSetsPrev) o;
        return Objects.equals(prevId, that.prevId) &&
                Objects.equals(wsets, that.wsets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prevId, wsets);
    }
}
