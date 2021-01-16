package ru.fitness.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeStampRepoAdapterImpl implements TimeStampRepoAdapter {
    private final TimeStampRepository repo;
    private final EntityManager entityManager;

    public TimeStampRepoAdapterImpl(TimeStampRepository repo, EntityManager entityManager) {
        this.repo = repo;
        this.entityManager = entityManager;
    }

    @Override
    public List<ITimeStamp> getByWorkoutId(long workoutId) {
        return new ArrayList<>(repo.findByWorkoutId(workoutId));
    }

    @Override
    public ITimeStamp createTimeStamp() {
        return new TimeStamp();
    }

    @Override
    public void saveTimeStamp(ITimeStamp timeStamp) {
        entityManager.persist(timeStamp);
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public ITimeStamp getFirstEvent(long workoutId) {
        return repo.getFirstTimeStamp(workoutId, PageRequest.of(0, 1)).get(0);
    }
}
