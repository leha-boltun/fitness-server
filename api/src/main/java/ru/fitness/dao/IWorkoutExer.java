package ru.fitness.dao;

public interface IWorkoutExer {
    int getExerOrder();

    void setExerOrder(int exerOrder);

    Long getId();

    IWorkout getWorkout();

    void setWorkout(IWorkout prog);

    IExer getExer();

    void setExer(IExer exer);
}
