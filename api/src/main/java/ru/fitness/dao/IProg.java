package ru.fitness.dao;

import java.util.Optional;
import java.util.Set;

public interface IProg {
    Optional<Long> getPrevProgId();

    boolean isPrevious();

    IProg getPrevProg();

    Set<IProgExer> getProgExers();

    Long getId();

    String getName();

    void setName(String name);
}
