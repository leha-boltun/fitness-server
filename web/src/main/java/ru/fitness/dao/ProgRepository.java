package ru.fitness.dao;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProgRepository extends Repository<Prog, Long> {
    Prog getProgById(long id);
    List<Prog> findAll();
}
