package ru.fitness.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TimeStampRepository extends Repository<TimeStamp, Long> {
    @Query("select e from TimeStamp e where e.workout.id = :workoutId order by e.wtime desc, e.id desc")
    List<TimeStamp> findByWorkoutId(long workoutId);

    @Query("select t from TimeStamp t join t.eventType e join t.workout w order by e.eventOrder")
    List<TimeStamp> getFirstTimeStamp(long workoutId, Pageable pageable);
}
