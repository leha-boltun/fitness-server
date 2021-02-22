package ru.fitness.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutManagerRepository extends Repository<Workout, Long> {
    @Query("select e from Workout e where e.wuser.id = :userId order by e.wdate desc, e.id desc")
    List<Workout> findByUserId(@Param("userId") int userId);

    @Query("select e from Workout e where e.prog.id = :progId order by e.wdate desc, e.id desc")
    List<Workout> getLastByProgId(long progId, Pageable pageable);

    @Query("select e from Workout e where e.wdate <= " +
            "(select e2.wdate from Workout e2 where e2.id = :workoutId) and e.wuserId = " +
            "(select e2.wuserId from Workout e2 where e2.id = :workoutId) and e.id <> :workoutId " +
            "order by e.wdate desc, e.id desc")
    List<Workout> getPrevById(long workoutId, Pageable pageable);

    @Query("select e from EventType e where e.eventOrder > " +
            "coalesce((select max(e2.eventOrder) from TimeStamp t join t.eventType e2 join t.workout w" +
            " where w.id = :workoutId), 0)")
    List<EventType> getNextEventType(long workoutId, Pageable pageable);

    @Query("select e from TimeStamp e where e.workout.id = :workoutId order by e.wtime desc, e.id desc")
    List<TimeStamp> getStampsByWorkoutId(long workoutId);

    @Query("select t from TimeStamp t join t.eventType e join t.workout w where w.id = :workoutId order by e.eventOrder")
    List<TimeStamp> getFirstTimeStamp(long workoutId, Pageable pageable);

    @Query("select t from TimeStamp t join t.eventType e join t.workout w where w.id = :workoutId order by e.eventOrder desc")
    List<TimeStamp> getLastTimeStamp(long workoutId, Pageable pageable);
}
