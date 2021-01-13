package ru.fitness.dao;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface WsetRepository extends Repository<Wset, Long> {
    List<Wset> getByWorkoutExerId(long id);
}
