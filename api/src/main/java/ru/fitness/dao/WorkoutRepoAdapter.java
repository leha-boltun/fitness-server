package ru.fitness.dao;

import java.util.List;

public interface WorkoutRepoAdapter {
    List<IWorkout> findByUserId(int userId);

    IWorkout getWorkoutRef(long id);

    IWorkout createWorkout();

    void saveWorkout(IWorkout workout);

    IWorkout getById(long id);
}
