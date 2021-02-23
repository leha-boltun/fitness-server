package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.IProg;
import ru.fitness.dao.IWuser;
import ru.fitness.dao.Manager;
import ru.fitness.dao.ProgManager;
import ru.fitness.dao.WorkoutManager;
import ru.fitness.dto.DProg;
import ru.fitness.dto.DUserMain;
import ru.fitness.dto.DWorkout;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class UserImpl implements User {
    private final Manager manager;
    private final WorkoutManager workoutManager;
    private final Workout workoutLogic;
    private Integer userId;
    private final ProgManager progManager;

    public UserImpl(
            Manager manager, WorkoutManager workoutManager,
            Workout workout, ProgManager progManager) {
        this.manager = manager;
        this.workoutManager = workoutManager;
        this.workoutLogic = workout;
        this.progManager = progManager;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public List<DWorkout> getWorkouts() {
        return workoutManager.findByUserId(userId).stream().map((workout) -> {
                workoutLogic.setWorkoutId(workout.getId());
                return new DWorkout(workout.getId(), workout.getWdate(), workout.getProg().getName(),
                        workout.isFinished(), workoutLogic.getTotalTime().orElse(null));
        }).collect(Collectors.toList());
    }

    @Override
    public DUserMain getMain() {
        return new DUserMain(manager.getById(IWuser.class, userId).getName());
    }

    @Override
    public List<DProg> getProgs() {
        return progManager.getActualProgsByWuserId(this.userId).stream().sorted(Comparator.comparing(IProg::getName))
                .map(it -> new DProg(it.getId(), it.getName()))
                .collect(Collectors.toList());
    }
}
