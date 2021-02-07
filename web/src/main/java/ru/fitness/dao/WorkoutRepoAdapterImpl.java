package ru.fitness.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutRepoAdapterImpl implements WorkoutRepoAdapter {
    private final WorkoutRepository workoutRepo;
    private final EntityManager entityManager;

    public WorkoutRepoAdapterImpl(
            WorkoutRepository workoutRepo,
            EntityManager entityManager) {
        this.workoutRepo = workoutRepo;
        this.entityManager = entityManager;
    }

    @Override
    public List<IWorkout> findByUserId(int userId) {
        return new ArrayList<>(workoutRepo.findByUserId(userId));
    }

    @Override
    public IWorkout getLastByProgId(long progId) {
        List<Workout> workouts = workoutRepo.getLastByProgId(progId, PageRequest.of(0, 1));
        return (workouts.isEmpty() ? null: workouts.get(0));
    }

    @Override
    public IWorkout getWorkoutRef(long id) {
        return entityManager.getReference(Workout.class, id);
    }

    @Override
    public IWorkout createWorkout() {
        return new Workout();
    }

    @Override
    public void saveWorkout(IWorkout workout) {
        entityManager.persist(workout);
    }

    @Override
    public IWorkout getById(long id) {
        return workoutRepo.getById(id);
    }
}
