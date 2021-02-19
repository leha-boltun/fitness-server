package ru.fitness.logic;

import ru.fitness.dto.DWSetsAndPrevId;
import ru.fitness.dto.DWset;

import java.util.List;

public interface WorkoutExer {
    void setId(long id);

    List<DWset> getWsets();

    DWSetsAndPrevId getWsetsAndPrevId();
}
