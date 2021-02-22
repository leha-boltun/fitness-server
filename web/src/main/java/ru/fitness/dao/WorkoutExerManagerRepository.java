package ru.fitness.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface WorkoutExerManagerRepository extends Repository<WorkoutExer, Long> {
    @Query("select prev from WorkoutExer we join we.workout.prevWorkout.workoutExers prev where we.exer.id = prev.exer.id and we.id = :workoutExerId")
    List<WorkoutExer> getPrevExer(long workoutExerId, Pageable pageable);
}
