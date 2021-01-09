package ru.fitness.dao;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface WUserRepository extends Repository<WUser, Long> {
    List<WUser> findAll();
}
