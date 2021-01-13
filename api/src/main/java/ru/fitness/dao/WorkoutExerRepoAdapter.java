package ru.fitness.dao;

public interface WorkoutExerRepoAdapter {
    IWorkoutExer createExer();

    void saveExer(IWorkoutExer exer);
}
