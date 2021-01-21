package ru.fitness.dao;

public interface WsetRepoAdapter {
    IWset createWset();

    void saveWset(IWset wset);

    IWset getById(long id);

    int getMaxOrder(long workoutExerId);
}
