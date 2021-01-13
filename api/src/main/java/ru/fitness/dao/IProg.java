package ru.fitness.dao;

import java.util.Set;

public interface IProg {
    Set<IProgExer> getProgExers();

    Long getId();

    String getName();

    void setName(String name);
}
