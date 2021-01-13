package ru.fitness.dao;

public interface IWset {
    Long getId();

    String getWeight();

    void setWeight(String weight);

    IWorkoutExer getWorkoutExer();

    void setWorkoutExer(IWorkoutExer workoutExer);

    int getWsetOrder();

    void setWsetOrder(int wsetOrder);

    String getCount();

    void setCount(String count);
}
