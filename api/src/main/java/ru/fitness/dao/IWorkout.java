package ru.fitness.dao;

import java.time.LocalDate;

public interface IWorkout {
    boolean isFinished();

    void setFinished(boolean finished);

    IWuser getWUser();

    void setWUser(IWuser wUser);

    Long getId();

    LocalDate getWdate();

    void setWdate(LocalDate wdate);
}
