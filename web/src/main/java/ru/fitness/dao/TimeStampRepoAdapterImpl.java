package ru.fitness.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.fitness.exception.NoTimestampException;

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
    public void removeTimeStamp(ITimeStamp timeStamp) {
        entityManager.remove(timeStamp);
    }

    @Override
    public ITimeStamp getFirstTimeStamp(long workoutId) {
        List<TimeStamp> stamps = repo.getFirstTimeStamp(workoutId, PageRequest.of(0, 1));
        if (stamps.isEmpty()) {
            throw new NoTimestampException();
        } else {
            return stamps.get(0);
        }
    }

    @Override
    public ITimeStamp getLastTimeStamp(long workoutId) {
        List<TimeStamp> stamps = repo.getLastTimeStamp(workoutId, PageRequest.of(0, 1));
        if (stamps.isEmpty()) {
            throw new NoTimestampException();
        } else {
            return stamps.get(0);
        }
    }
}
