package ru.fitness.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.fitness.exception.NoTimestampException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutManagerImpl implements WorkoutManager {
    private final WorkoutManagerRepository workoutRepo;

    public WorkoutManagerImpl(
            WorkoutManagerRepository workoutRepo
    ) {
        this.workoutRepo = workoutRepo;
    }

    @Override
    public Optional<IEventType> getNextEventType(long workoutId) {
        List<EventType> eventTypes = workoutRepo.getNextEventType(workoutId, PageRequest.of(0, 1, Sort.by("eventOrder").ascending()));
        return eventTypes.isEmpty() ? Optional.empty(): Optional.of(eventTypes.get(0));
    }

    @Override
    public List<IWorkout> findByUserId(int userId) {
        return new ArrayList<>(workoutRepo.findByUserId(userId));
    }

    @Override
    public Optional<IWorkout> getLastByProgId(long progId) {
        List<Workout> workouts = workoutRepo.getLastByProgId(progId, PageRequest.of(0, 1));
        return workouts.isEmpty() ? Optional.empty() : Optional.of(workouts.get(0));
    }

    @Override
    public Optional<IWorkout> getPrevById(long workoutId) {
        List<Workout> prevWorkoutList = workoutRepo.getPrevById(workoutId, PageRequest.of(0, 1));
        return (prevWorkoutList.isEmpty()) ? Optional.empty() : Optional.of(prevWorkoutList.get(0));
    }

    @Override
    public ITimeStamp getFirstTimeStamp(long workoutId) {
        List<TimeStamp> stamps = workoutRepo.getFirstTimeStamp(workoutId, PageRequest.of(0, 1));
        if (stamps.isEmpty()) {
            throw new NoTimestampException();
        } else {
            return stamps.get(0);
        }
    }

    @Override
    public ITimeStamp getLastTimeStamp(long workoutId) {
        List<TimeStamp> stamps = workoutRepo.getLastTimeStamp(workoutId, PageRequest.of(0, 1));
        if (stamps.isEmpty()) {
            throw new NoTimestampException();
        } else {
            return stamps.get(0);
        }
    }

    @Override
    public List<ITimeStamp> getByWorkoutId(long workoutId) {
        return new ArrayList<>(workoutRepo.getStampsByWorkoutId(workoutId));
    }
}
