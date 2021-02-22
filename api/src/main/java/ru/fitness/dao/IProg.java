package ru.fitness.dao;

import java.util.Set;

public interface IProg {
    Long getPrevProgId();

    boolean isPrevious();

    IProg getPrevProg();

    Set<IProgExer> getProgExers();

    Long getId();

    String getName();

    void setName(String name);
}
