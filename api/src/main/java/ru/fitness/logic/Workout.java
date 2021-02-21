package ru.fitness.logic;

import ru.fitness.dto.DExer;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkout;
import ru.fitness.dto.DWorkoutMain;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public interface Workout {
    void setWorkoutId(long id);

    int getMaxUndoSeconds();

    DWorkoutMain getMain();

    void setWeight(BigDecimal weight);

    List<DTimeStampMain> getTimeStamps();

    DNextEvent getNextEventName();

    DNextEvent undoEvent();

    DNextEvent processNextEvent();

    DWorkout createWorkout(int userId, long progId, long prevId);

    List<DExer> getExers();

    LocalTime getTotalTime();
}
