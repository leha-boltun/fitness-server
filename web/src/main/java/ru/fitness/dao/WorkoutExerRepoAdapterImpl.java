package ru.fitness.dao;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class WorkoutExerRepoAdapterImpl implements WorkoutExerRepoAdapter {
    private final EntityManager entityManager;

    public WorkoutExerRepoAdapterImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public IWorkoutExer createExer() {
        return new WorkoutExer();
    }

    @Override
    public void saveExer(IWorkoutExer exer) {
        entityManager.persist(exer);
    }
}
