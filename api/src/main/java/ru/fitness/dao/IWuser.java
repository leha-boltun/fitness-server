package ru.fitness.dao;

import java.util.Set;

public interface IWuser {
    Set<IProg> getProgs();

    void setProgs(Set<IProg> progs);

    Integer getId();

    String getName();

    void setName(String name);
}
