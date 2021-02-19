package ru.fitness.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class DWSetsAndPrevId {
    public Long prevId;
    @NotNull
    public List<DWset> wsets;

    public DWSetsAndPrevId(List<DWset> wsets, Long prevId) {
        this.wsets = wsets;
        this.prevId = prevId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWSetsAndPrevId that = (DWSetsAndPrevId) o;
        return Objects.equals(prevId, that.prevId) &&
                Objects.equals(wsets, that.wsets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prevId, wsets);
    }
}
