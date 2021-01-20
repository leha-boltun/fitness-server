package ru.fitness.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutRepository extends Repository<Workout, Long> {
    @Query("select e from Workout e where e.wuser.id = :userId order by e.wdate desc, e.id desc")
    List<Workout> findByUserId(@Param("userId") int userId);

    Workout getById(long id);


    @Query("select e from Workout e where e.prog.id = :progId order by e.wdate desc, e.id desc")
    List<Workout> getLastByProgId(long progId, Pageable pageable);
}
