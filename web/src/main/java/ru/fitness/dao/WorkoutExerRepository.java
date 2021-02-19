package ru.fitness.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface WorkoutExerRepository extends Repository<WorkoutExer, Long> {
    WorkoutExer getById(long id);
    @Query("select prev from WorkoutExer we join we.workout.prevWorkout.workoutExers prev where we.exer.id = prev.exer.id and we.id = :workoutExerId")
    List<WorkoutExer> getPrevExer(long workoutExerId, Pageable pageable);
}
