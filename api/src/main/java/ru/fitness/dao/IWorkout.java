package ru.fitness.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface IWorkout {
    Optional<IWorkout> getPrevWorkout();

    void setPrevWorkout(IWorkout prevWorkout);

    boolean isFinished();

    void setFinished(boolean finished);

    IWuser getWUser();

    void setWUser(IWuser wUser);

    Long getId();

    LocalDate getWdate();

    void setWdate(LocalDate wdate);

    IProg getProg();

    void setProg(IProg prog);

    Set<IWorkoutExer> getWorkoutExers();

    void setWorkoutExers(Set<IWorkoutExer> workoutExers);

    Optional<BigDecimal> getWeight();

    void setWeight(BigDecimal weight);

    Integer getWuserId();
}
