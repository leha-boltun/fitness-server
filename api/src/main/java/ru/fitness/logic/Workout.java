package ru.fitness.logic;

import ru.fitness.dto.DExer;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkout;
import ru.fitness.dto.DWorkoutMain;

import java.util.List;

public interface Workout {
    void setWorkoutId(long id);

    DWorkoutMain getMain();

    List<DTimeStampMain> getTimeStamps();

    DNextEvent getNextEventName();

    DNextEvent processNextEvent();

    DWorkout createWorkout(int userId, long progId);

    List<DExer> getExers();
}
