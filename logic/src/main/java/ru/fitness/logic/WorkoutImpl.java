package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.EventTypeRepoAdapter;
import ru.fitness.dao.IEventType;
import ru.fitness.dao.ITimeStamp;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.TimeStampRepoAdapter;
import ru.fitness.dao.WorkoutRepoAdapter;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkoutMain;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class WorkoutImpl implements Workout {
    private long id;
    private final WorkoutRepoAdapter workoutRepo;
    private final TimeStampRepoAdapter timeStampRepo;
    private final EventTypeRepoAdapter eventTypeRepo;

    public WorkoutImpl(WorkoutRepoAdapter workoutRepo, TimeStampRepoAdapter timeStampRepo, EventTypeRepoAdapter eventTypeRepo) {
        this.workoutRepo = workoutRepo;
        this.timeStampRepo = timeStampRepo;
        this.eventTypeRepo = eventTypeRepo;
    }

    @Override
    public void setWorkoutId(long id) {
        this.id = id;
    }

    @Override
    public DWorkoutMain getMain() {
        IWorkout workout = workoutRepo.getById(id);
        return new DWorkoutMain(workout.getWdate(), workout.isFinished());
    }


    @Override
    public List<DTimeStampMain> getTimeStamps() {
        return timeStampRepo.getByWorkoutId(id).stream()
                .map((timeStamp) -> new DTimeStampMain(timeStamp.getWtime(), timeStamp.getEventType().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public DNextEvent getNextEventName() {
        IWorkout workout = workoutRepo.getById(id);
        if (!workout.isFinished()) {
            Optional<IEventType> eType = eventTypeRepo.getNextEventType(id);
            return eType.map(iEventType -> new DNextEvent(iEventType.getName())).orElseGet(() -> new DNextEvent(""));
        } else {
            //TODO
            throw new RuntimeException();
        }
    }


    @Override
    public DNextEvent processNextEvent() {
        IWorkout workout = workoutRepo.getById(id);
        if (!workout.isFinished()) {
            Optional<IEventType> eType = eventTypeRepo.getNextEventType(id);
            if (eType.isPresent()) {
                ITimeStamp timeStamp = timeStampRepo.createTimeStamp();
                timeStamp.setWtime(LocalTime.now());
                timeStamp.setEventType(eType.get());
                timeStamp.setWorkout(workout);
                timeStampRepo.saveTimeStamp(timeStamp);
                timeStampRepo.flush();
                Optional<IEventType> newEType = eventTypeRepo.getNextEventType(id);
                if (newEType.isPresent()) {
                    return new DNextEvent(newEType.get().getName());
                } else {
                    workout.setFinished(true);
                    workoutRepo.saveWorkout(workout);
                    return new DNextEvent("");
                }
            } else {
                //TODO
                throw new RuntimeException("");
            }
        } else {
            //TODO
            throw new RuntimeException("");
        }
    }
}
