package ru.fitness.dao;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface WUserRepository extends Repository<Wuser, Integer> {
    List<Wuser> findAll();
    Wuser findById(int id);
}
