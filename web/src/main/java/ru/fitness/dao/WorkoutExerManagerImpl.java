package ru.fitness.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutExerManagerImpl implements WorkoutExerManager {
    private final WorkoutExerManagerRepository workoutExerRepo;

    public WorkoutExerManagerImpl(WorkoutExerManagerRepository workoutRepository) {
        this.workoutExerRepo = workoutRepository;
    }

    @Override
    public Optional<IWorkoutExer> getPrevExer(long id) {
        List<WorkoutExer> exers = workoutExerRepo.getPrevExer(id, PageRequest.of(0, 1));
        if (exers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(exers.get(0));
        }
    }
}
