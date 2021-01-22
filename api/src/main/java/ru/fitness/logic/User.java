package ru.fitness.logic;

import ru.fitness.dto.DProg;
import ru.fitness.dto.DUserMain;
import ru.fitness.dto.DWorkout;

import java.util.List;

public interface User {
    void setUserId(Integer userId);

    List<DWorkout> getWorkouts();

    DUserMain getMain();

    List<DProg> getProgs();
}
