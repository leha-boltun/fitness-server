package ru.fitness.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProgRepository extends Repository<Prog, Long> {
    Prog getProgById(long id);
    List<Prog> findAll();
    @Query("select e from Prog e join e.wusers u where e.isPrevious = false and u.id = :wuserId")
    List<Prog> getActualProgsByWuserId(int wuserId);
}
