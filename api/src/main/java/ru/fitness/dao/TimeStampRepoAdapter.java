package ru.fitness.dao;

import java.util.List;

public interface TimeStampRepoAdapter {
    List<ITimeStamp> getByWorkoutId(long workoutId);

    ITimeStamp createTimeStamp();

    void saveTimeStamp(ITimeStamp timeStamp);

    void flush();

    ITimeStamp getFirstEvent(long workoutId);
}
