package ru.fitness.dao;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface WuserManagerRepository extends Repository<Wuser, Integer> {
    List<Wuser> findAll();
}
