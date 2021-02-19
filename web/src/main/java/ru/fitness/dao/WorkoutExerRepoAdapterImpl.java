package ru.fitness.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class WorkoutExerRepoAdapterImpl implements WorkoutExerRepoAdapter {
    private final WorkoutExerRepository workoutExerRepo;
    private final EntityManager entityManager;

    public WorkoutExerRepoAdapterImpl(WorkoutExerRepository workoutRepository, EntityManager entityManager) {
        this.workoutExerRepo = workoutRepository;
        this.entityManager = entityManager;
    }

    @Override
    public IWorkoutExer getExerRef(long id) {
        return entityManager.getReference(WorkoutExer.class, id);
    }

    @Override
    public IWorkoutExer createExer() {
        return new WorkoutExer();
    }

    @Override
    public void saveExer(IWorkoutExer exer) {
        entityManager.persist(exer);
    }

    @Override
    public IWorkoutExer getById(long id) {
        return workoutExerRepo.getById(id);
    }

    @Override
    public IWorkoutExer getPrevExer(long id) {
        List<WorkoutExer> exers = workoutExerRepo.getPrevExer(id, PageRequest.of(0, 1));
        if (exers.isEmpty()) {
            return null;
        } else {
            return exers.get(0);
        }
    }
}
