package ru.fitness.dao;

import java.util.Set;

public interface IWorkoutExer {
    int getExerOrder();

    void setExerOrder(int exerOrder);

    Set<IWset> getWsets();

    void setWsets(Set<IWset> wsets);

    Long getId();

    IWorkout getWorkout();

    void setWorkout(IWorkout prog);

    IExer getExer();

    void setExer(IExer exer);
}
