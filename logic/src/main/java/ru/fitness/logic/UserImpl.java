package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.IProg;
import ru.fitness.dao.ProgRepoAdapter;
import ru.fitness.dao.WUserRepoAdapter;
import ru.fitness.dao.WorkoutRepoAdapter;
import ru.fitness.dto.DProg;
import ru.fitness.dto.DUserMain;
import ru.fitness.dto.DWorkout;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class UserImpl implements User {
    private final WorkoutRepoAdapter workoutRepo;
    private final WUserRepoAdapter userRepo;
    private final Workout workoutLogic;
    private Integer userId;
    private final ProgRepoAdapter progRepo;

    public UserImpl(
            WorkoutRepoAdapter workoutRepo,
            WUserRepoAdapter userRepo,
            Workout workout, ProgRepoAdapter progRepo) {
        this.workoutRepo = workoutRepo;
        this.userRepo = userRepo;
        this.workoutLogic = workout;
        this.progRepo = progRepo;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public List<DWorkout> getWorkouts() {
        return workoutRepo.findByUserId(userId).stream().map((workout) -> {
                workoutLogic.setWorkoutId(workout.getId());
                return new DWorkout(workout.getId(), workout.getWdate(), workout.getProg().getName(),
                        workout.isFinished(), workoutLogic.getTotalTime());
        }).collect(Collectors.toList());
    }

    @Override
    public DUserMain getMain() {
        return new DUserMain(userRepo.getUser(userId).getName());
    }

    @Override
    public List<DProg> getProgs() {
        return progRepo.getActualProgsByWuserId(this.userId).stream().sorted(Comparator.comparing(IProg::getName))
                .map(it -> new DProg(it.getId(), it.getName()))
                .collect(Collectors.toList());
    }
}
