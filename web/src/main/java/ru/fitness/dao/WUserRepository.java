package ru.fitness.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WUserRepository extends Repository<WUser, Long> {
    List<WUser> findAll();
}
