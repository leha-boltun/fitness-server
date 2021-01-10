package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.WUserRepoAdapter;
import ru.fitness.dao.WorkoutRepoAdapter;
import ru.fitness.dto.DUserMain;
import ru.fitness.dto.DWorkout;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class UserImpl implements User {
    private final WorkoutRepoAdapter workoutRepo;
    private final WUserRepoAdapter userRepo;
    private Integer userId;

    public UserImpl(
            WorkoutRepoAdapter workoutRepo,
            WUserRepoAdapter userRepo
            ) {
        this.workoutRepo = workoutRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public List<DWorkout> getWorkouts() {
        return workoutRepo.findByUserId(userId).stream().map((workout) ->
                new DWorkout(workout.getId(), workout.getWdate())).collect(Collectors.toList());
    }

    @Override
    public DUserMain getMain() {
        return new DUserMain(userRepo.getUser(userId).getName());
    }
}
