package ru.fitness.dao;

import org.springframework.data.repository.Repository;

public interface WorkoutExerRepository extends Repository<WorkoutExer, Long> {
    WorkoutExer getById(long id);
}
