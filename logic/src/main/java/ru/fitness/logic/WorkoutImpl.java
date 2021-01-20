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
import ru.fitness.exception.NoTimestampException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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

    private LocalTime doGetTotalTime() {
        return
                timeStampRepo.getLastTimeStamp(id).getWtime()
                        .minusNanos(timeStampRepo.getFirstTimeStamp(id).getWtime().toNanoOfDay());
    }

    @Override
    public DWorkoutMain getMain() {
        IWorkout workout = workoutRepo.getById(id);
        LocalTime totalTime = null;
        try {
            totalTime = doGetTotalTime();
        } catch (NoTimestampException ignored) {}
        return new DWorkoutMain(workout.getWuserId(), workout.getWdate(), workout.isFinished(),
                workout.getWeight(), totalTime);
    }

    @Override
    public void setWeight(BigDecimal weight) {
        IWorkout workout = workoutRepo.getById(id);
        workout.setWeight(weight);
        workoutRepo.saveWorkout(workout);
    }

    @Override
    public List<DTimeStampMain> getTimeStamps() {
        DTimeStampMain[] prevStamp = new DTimeStampMain[1];
        List<ITimeStamp> stamps = timeStampRepo.getByWorkoutId(id);
        Collections.reverse(stamps);
        List<DTimeStampMain> result = stamps.stream()
                .map((timeStamp) -> {
                    DTimeStampMain curStamp;
                    if (prevStamp[0] == null) {
                        curStamp = new DTimeStampMain(timeStamp.getWtime(), timeStamp.getEventType().getName());
                    } else {
                        curStamp = new DTimeStampMain(
                                timeStamp.getWtime(), timeStamp.getEventType().getName(),
                                timeStamp.getWtime().minusNanos(prevStamp[0].time.toNanoOfDay()));
                    }
                    prevStamp[0] = curStamp;
                    return curStamp;
                })
                .collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    @Override
    public DNextEvent getNextEventName() {
        IWorkout workout = workoutRepo.getById(id);
        if (!workout.isFinished()) {
            Optional<IEventType> eType = eventTypeRepo.getNextEventType(id);
            return eType.map(iEventType -> new DNextEvent(
                    iEventType.getName(),
                    iEventType.getEventCode().equals(EventCode.END.getName()),
                    iEventType.getEventCode().equals(EventCode.BEFORE_BEGIN.getName())
            )).orElseGet(() -> new DNextEvent("", false, false));
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
                LocalTime currentTime = LocalTime.now();
                timeStamp.setWtime(currentTime);
                timeStamp.setEventType(eType.get());
                timeStamp.setWorkout(workout);
                timeStampRepo.saveTimeStamp(timeStamp);
                timeStampRepo.flush();
                Optional<IEventType> newEType = eventTypeRepo.getNextEventType(id);
                if (newEType.isPresent()) {
                    return new DNextEvent(newEType.get().getName(),
                            newEType.get().getEventCode().equals(EventCode.END.getName()),
                            newEType.get().getEventCode().equals(EventCode.BEFORE_BEGIN.getName()));
                } else {
                    workout.setFinished(true);
                    workoutRepo.saveWorkout(workout);
                    return new DNextEvent("", false, false);
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
    public DWorkout createWorkout(int userId, long progId, long prevId) {
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
        if (prevId != -1) {
            workout.setPrevWorkout(workoutRepo.getLastByProgId(prevId));
        }
        workout.setWorkoutExers(workoutExers);
        workout.setProg(prog);
        workout.setWdate(LocalDate.now());
        workout.setFinished(false);
        workoutRepo.saveWorkout(workout);
        return new DWorkout(workout.getId(), workout.getWdate(), workout.getProg().getName(),
                workout.isFinished(), null);
    }

    @Override
    public List<DExer> getExers() {
        class ItemAndOrder {
            public final DExer exer;
            public final int order;

            public int getOrder() {
                return order;
            }

            ItemAndOrder(DExer exer, int order) {
                this.exer = exer;
                this.order = order;
            }
        }

        IWorkout workout = workoutRepo.getById(id);
        Map<Long, IWorkoutExer> curExers =
                workout.getWorkoutExers().stream().collect(Collectors.toMap(it -> it.getExer().getId(), Function.identity()));
        Map<Long, IWorkoutExer> prevExers = (workout.getPrevWorkout() == null) ? Collections.emptyMap() :
                workout.getPrevWorkout().getWorkoutExers().stream()
                        .collect(Collectors.toMap(it -> it.getExer().getId(), Function.identity()));
        List<ItemAndOrder> result = new ArrayList<>(curExers.size());

        curExers.forEach(
                (k, v) -> {
                    if (prevExers.containsKey(k)) {
                        result.add(
                                new ItemAndOrder(
                                        new DExer(v.getId(), v.getExer().getName(), prevExers.remove(k).getId()),
                                        v.getExerOrder()
                                )
                        );
                    } else {
                        result.add(
                                new ItemAndOrder(
                                        new DExer(v.getId(), v.getExer().getName()),
                                        v.getExerOrder()
                                )
                        );
                    }
                }
        );

        int addOrder = result.stream().max(Comparator.comparingInt(ItemAndOrder::getOrder))
                .orElse(new ItemAndOrder(null, 0)).getOrder();

        prevExers.forEach((k, v) -> result.add(
                new ItemAndOrder(
                        new DExer(v.getExer().getName(), v.getId()),
                        v.getExerOrder() + addOrder
                )
        ));

        return result.stream().sorted(Comparator.comparingInt(ItemAndOrder::getOrder)).map(it -> it.exer)
                .collect(Collectors.toList());
    }

    @Override
    public LocalTime getTotalTime() {
        try {
            return doGetTotalTime();
        } catch (NoTimestampException ex) {
            return null;
        }
    }
}
