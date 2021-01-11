package ru.fitness.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface EventTypeRepository extends Repository<EventType, Integer> {
    @Query("select e from EventType e where e.eventOrder > " +
            "coalesce((select max(e2.eventOrder) from TimeStamp t join t.eventType e2 join t.workout w" +
            " where w.id = :workoutId), 0)")
    List<EventType> getNextEventType(long workoutId, Pageable pageable);
}
