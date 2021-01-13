package ru.fitness.logic;

import ru.fitness.dto.DWset;

public interface Wset {
    void setId(long id);

    void createWset(long workoutExerId, DWset data);
}
