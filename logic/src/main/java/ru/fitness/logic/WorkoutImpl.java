package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.EventTypeRepoAdapter;
import ru.fitness.dao.IEventType;
import ru.fitness.dao.IProg;
import ru.fitness.dao.IProgExer;
import ru.fitness.dao.ITimeStamp;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.ProgRepoAdapter;
import ru.fitness.dao.TimeStampRepoAdapter;
import ru.fitness.dao.WUserRepoAdapter;
import ru.fitness.dao.WorkoutExerRepoAdapter;
import ru.fitness.dao.WorkoutRepoAdapter;
import ru.fitness.dto.DExer;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkout;
import ru.fitness.dto.DWorkoutMain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class WorkoutImpl implements Workout {
    private long id;
    private final WorkoutExerRepoAdapter workoutExerRepo;
    private final WUserRepoAdapter userRepo;
    private final ProgRepoAdapter progRepo;
    private final WorkoutRepoAdapter workoutRepo;
    private final TimeStampRepoAdapter timeStampRepo;
    private final EventTypeRepoAdapter eventTypeRepo;

    public WorkoutImpl(WorkoutExerRepoAdapter workoutExerRepo, WUserRepoAdapter userRepo, ProgRepoAdapter progRepo,
                       WorkoutRepoAdapter workoutRepo, TimeStampRepoAdapter timeStampRepo,
                       EventTypeRepoAdapter eventTypeRepo) {
        this.workoutExerRepo = workoutExerRepo;
        this.userRepo = userRepo;
        this.progRepo = progRepo;
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

    @Override
    public DWorkout createWorkout(int userId, long progId) {
        IWorkout workout = workoutRepo.createWorkout();
        workout.setWUser(userRepo.getUserRef(userId));
        IProg prog = progRepo.getProg(progId);
        List<IProgExer> exers = new ArrayList<>(prog.getProgExers());
        int[] cnt = new int[1];
        Set<IWorkoutExer> workoutExers = exers.stream().sorted(Comparator.comparingInt(IProgExer::getExerOrder)).map(
            (programExer) -> {
                IWorkoutExer workoutExer = workoutExerRepo.createExer();
                workoutExer.setExer(programExer.getExer());
                workoutExer.setWorkout(workout);
                workoutExer.setExerOrder(cnt[0]);
                cnt[0]++;
                return workoutExer;
            }
        ).collect(Collectors.toSet());
        workout.setWorkoutExers(workoutExers);
        workout.setProg(prog);
        workout.setWdate(LocalDate.now());
        workout.setFinished(false);
        workoutRepo.saveWorkout(workout);
        return new DWorkout(workout.getId(), workout.getWdate(), workout.getProg().getName(), workout.isFinished());
    }

    @Override
    public List<DExer> getExers() {
        IWorkout workout = workoutRepo.getById(id);
        return workout.getWorkoutExers().stream()
                .sorted(Comparator.comparingInt(IWorkoutExer::getExerOrder))
                .map((exer) -> new DExer(exer.getId(), exer.getExer().getName())).collect(Collectors.toList());
    }
}
