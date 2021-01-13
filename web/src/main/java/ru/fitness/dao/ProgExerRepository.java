package ru.fitness.dao;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProgExerRepository extends Repository<ProgExer, ProgExerKey> {
    List<ProgExer> findByProgId(long progId);
}
