package ru.fitness.dao;

import java.util.List;

public interface ProgRepoAdapter {
    IProg getProg(long id);

    List<IProg> getProgs();

    List<IProg> getActualProgsByWuserId(int userId);
}
