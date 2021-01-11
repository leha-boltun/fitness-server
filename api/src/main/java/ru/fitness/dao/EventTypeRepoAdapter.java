package ru.fitness.dao;

import java.util.Optional;

public interface EventTypeRepoAdapter {
    Optional<IEventType> getNextEventType(long workoutId);
}
