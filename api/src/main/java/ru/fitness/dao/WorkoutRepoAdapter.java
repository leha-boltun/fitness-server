package ru.fitness.dao;

import java.util.List;

public interface WorkoutRepoAdapter {
    List<IWorkout> findByUserId(int userId);

    IWorkout createWorkout();

    void saveWorkout(IWorkout workout);
}