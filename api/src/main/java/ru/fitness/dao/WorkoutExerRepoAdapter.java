package ru.fitness.dao;

public interface WorkoutExerRepoAdapter {
    IWorkoutExer getExerRef(long id);

    IWorkoutExer createExer();

    void saveExer(IWorkoutExer exer);

    IWorkoutExer getById(long id);

    IWorkoutExer getPrevExer(long id);
}
