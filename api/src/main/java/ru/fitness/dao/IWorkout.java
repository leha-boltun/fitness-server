package ru.fitness.dao;

import java.time.LocalDate;

public interface IWorkout {
    IWuser getWUser();

    void setWUser(IWuser wUser);

    Long getId();

    LocalDate getWdate();

    void setWdate(LocalDate wdate);

    int getWuserId();

    void setWuserId(int wuserId);
}
