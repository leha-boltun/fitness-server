package ru.fitness.dao;

import java.util.List;
import java.util.Optional;

public interface WorkoutManager {
    Optional<IEventType> getNextEventType(long workoutId);

    List<IWorkout> findByUserId(int userId);

    Optional<IWorkout> getLastByProgId(long progId);

    Optional<IWorkout> getPrevById(long workoutId);

    Optional<ITimeStamp> getFirstTimeStamp(long workoutId);

    Optional<ITimeStamp> getLastTimeStamp(long workoutId);

    List<ITimeStamp> getByWorkoutId(long workoutId);
}
