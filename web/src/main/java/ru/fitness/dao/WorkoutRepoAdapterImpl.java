package ru.fitness.dao;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutRepoAdapterImpl implements WorkoutRepoAdapter {
    private final WorkoutRepository workoutRepo;

    public WorkoutRepoAdapterImpl(WorkoutRepository workoutRepo) {
        this.workoutRepo = workoutRepo;
    }

    @Override
    public List<IWorkout> findByUserId(int userId) {
        return new ArrayList<>(workoutRepo.findByUserId(userId));
    }
}
