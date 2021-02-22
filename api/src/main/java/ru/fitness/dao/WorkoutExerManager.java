package ru.fitness.dao;

import java.util.Optional;

public interface WorkoutExerManager {
    Optional<IWorkoutExer> getPrevExer(long id);
}
