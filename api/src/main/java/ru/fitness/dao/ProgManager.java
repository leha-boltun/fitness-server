package ru.fitness.dao;

import java.util.List;

public interface ProgManager {
    List<IProg> getProgs();

    List<IProg> getActualProgsByWuserId(int userId);
}
