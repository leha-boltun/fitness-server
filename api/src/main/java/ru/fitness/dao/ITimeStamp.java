package ru.fitness.dao;

import java.time.LocalTime;

public interface ITimeStamp {
    Long getId();

    LocalTime getWtime();

    void setWtime(LocalTime wtime);

    IWorkout getWorkout();

    void setWorkout(IWorkout workout);

    IEventType getEventType();

    void setEventType(IEventType eventType);
}
