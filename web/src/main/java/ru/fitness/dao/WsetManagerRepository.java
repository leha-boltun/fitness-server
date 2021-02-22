package ru.fitness.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface WsetManagerRepository extends Repository<Wset, Long> {
    @Query("select coalesce(max(w.wsetOrder), 0) from Wset w join w.workoutExer e where e.id = :id")
    int getMaxOrder(long id);
}
