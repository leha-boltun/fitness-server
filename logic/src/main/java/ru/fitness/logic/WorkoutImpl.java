package ru.fitness.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.IEventType;
import ru.fitness.dao.IProg;
import ru.fitness.dao.IProgExer;
import ru.fitness.dao.ITimeStamp;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWuser;
import ru.fitness.dao.Manager;
import ru.fitness.dao.WorkoutManager;
import ru.fitness.dto.DExer;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkout;
import ru.fitness.dto.DWorkoutMain;
import ru.fitness.exception.UnexpectedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
    private final Manager manager;
    private final WorkoutManager workoutManager;
    private long id;
    private final int maxUndoSeconds;

    public WorkoutImpl(Manager manager, WorkoutManager workoutManager, @Value("${fitness.logic.max-undo-seconds}") int maxUndoSeconds) {
        this.manager = manager;
        this.workoutManager = workoutManager;
        this.maxUndoSeconds = maxUndoSeconds;
    }

    @Override
    public void setWorkoutId(long id) {
        this.id = id;
    }

    @Override
    public int getMaxUndoSeconds() {
        return maxUndoSeconds;
    }

    private Optional<LocalTime> doGetTotalTime() {
        Optional<ITimeStamp> lastStamp = workoutManager.getLastTimeStamp(id);
        Optional<ITimeStamp> firstStamp = workoutManager.getFirstTimeStamp(id);
        if (!lastStamp.isPresent() && !firstStamp.isPresent()) {
            return Optional.empty();
        }
        if (!lastStamp.isPresent() || !firstStamp.isPresent()) {
            throw new UnexpectedException("One timestamp must be first and last");
        }
        return Optional.of(lastStamp.get().getWtime().minusNanos(firstStamp.get().getWtime().toNanoOfDay()));
    }

    @Override
    public DWorkoutMain getMain() {
        IWorkout workout = manager.getById(IWorkout.class, id);
        Optional<LocalTime> totalTime = doGetTotalTime();
        Optional<BigDecimal> weightDiffSame = Optional.empty();
        if (workout.getPrevWorkout().isPresent() &&
                workout.getWeight().isPresent() &&
                workout.getPrevWorkout().get().getWeight().isPresent()) {
            BigDecimal prevWeight = workout.getPrevWorkout().get().getWeight().get();
            weightDiffSame = Optional.of(workout.getWeight().get().subtract(prevWeight));
        }
        Optional<BigDecimal> weightDiff = Optional.empty();
        if (workout.getWeight().isPresent()) {
            Optional<IWorkout> prevWorkoutOther = workoutManager.getPrevById(id);
            if (prevWorkoutOther.isPresent() && prevWorkoutOther.get().getWeight().isPresent()) {
                weightDiff = Optional.of(workout.getWeight().get().subtract(prevWorkoutOther.get().getWeight().get()));
            }
        }
        return new DWorkoutMain(workout.getWuserId(), workout.getWdate(), workout.isFinished(),
                workout.getWeight().orElse(null), totalTime.orElse(null), weightDiff.orElse(null),
                weightDiffSame.orElse(null));
    }

    @Override
    public void setWeight(BigDecimal weight) {
        IWorkout workout = manager.getById(IWorkout.class, id);
        workout.setWeight(weight);
        manager.save(workout);
    }

    @Override
    public List<DTimeStampMain> getTimeStamps() {
        List<ITimeStamp> stamps = workoutManager.getByWorkoutId(id);
        Collections.reverse(stamps);
        List<DTimeStampMain> result = stamps.stream().collect(ArrayList::new, (prevStamps, timeStamp) -> {
                    DTimeStampMain curStamp;
                    if (prevStamps.isEmpty()) {
                        curStamp = new DTimeStampMain(timeStamp.getWtime(), timeStamp.getEventType().getName());
                    } else {
                        curStamp = new DTimeStampMain(
                                timeStamp.getWtime(), timeStamp.getEventType().getName(),
                                timeStamp.getWtime().minusNanos(prevStamps.get(0).time.toNanoOfDay()));
                    }
                    prevStamps.add(curStamp);
                }, ArrayList::addAll);
        Collections.reverse(result);
        return result;
    }

    private DNextEvent doGetNextEvent(IWorkout workout) {
        if (!workout.isFinished()) {
            Optional<IEventType> eType = workoutManager.getNextEventType(id);
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
    public DNextEvent getNextEventName() {
        IWorkout workout = manager.getById(IWorkout.class, id);
        return doGetNextEvent(workout);
    }

    @Override
    public DNextEvent undoEvent() {
        IWorkout workout = manager.getById(IWorkout.class, id);
        // TODO
        ITimeStamp timeStamp = workoutManager.getLastTimeStamp(id).orElseThrow(() -> new RuntimeException(""));
        LocalDateTime lastDate = timeStamp.getWtime().atDate(LocalDate.now());
        if (lastDate.until(LocalDateTime.now(), ChronoUnit.SECONDS) > maxUndoSeconds) {
            // TODO
            throw new RuntimeException("");
        }
        manager.remove(timeStamp);
        if (workout.isFinished()) {
            workout.setFinished(false);
        }
        manager.flush();
        return doGetNextEvent(workout);
    }

    @Override
    public DNextEvent processNextEvent() {
        IWorkout workout = manager.getById(IWorkout.class, id);
        if (!workout.isFinished()) {
            Optional<IEventType> eType = workoutManager.getNextEventType(id);
            if (eType.isPresent()) {
                ITimeStamp timeStamp = manager.create(ITimeStamp.class);
                LocalTime currentTime = LocalTime.now();
                timeStamp.setWtime(currentTime);
                timeStamp.setEventType(eType.get());
                timeStamp.setWorkout(workout);
                manager.save(timeStamp);
                manager.flush();
                Optional<IEventType> newEType = workoutManager.getNextEventType(id);
                if (newEType.isPresent()) {
                    return new DNextEvent(newEType.get().getName(),
                            newEType.get().getEventCode().equals(EventCode.END.getName()),
                            newEType.get().getEventCode().equals(EventCode.BEFORE_BEGIN.getName()));
                } else {
                    workout.setFinished(true);
                    manager.save(workout);
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
        IWorkout workout = manager.create(IWorkout.class);
        workout.setWUser(manager.getRef(IWuser.class, userId));
        IProg prog = manager.getById(IProg.class, progId);
        List<IProgExer> exers = new ArrayList<>(prog.getProgExers());
        int[] cnt = new int[1];
        Set<IWorkoutExer> workoutExers = exers.stream().sorted(Comparator.comparingInt(IProgExer::getExerOrder)).map(
            (programExer) -> {
                IWorkoutExer workoutExer = manager.create(IWorkoutExer.class);
                workoutExer.setExer(programExer.getExer());
                workoutExer.setWorkout(workout);
                workoutExer.setExerOrder(cnt[0]);
                cnt[0]++;
                return workoutExer;
            }
        ).collect(Collectors.toSet());
        if (prevId != -1) {
            Optional<IWorkout> prevWorkout = workoutManager.getLastByProgId(prevId);
            if (!prevWorkout.isPresent()) {
                prevWorkout = workoutManager.getLastByProgId(manager.getById(IProg.class, prevId).getPrevProgId());
            }
            prevWorkout.ifPresent(workout::setPrevWorkout);
        }
        workout.setWorkoutExers(workoutExers);
        workout.setProg(prog);
        workout.setWdate(LocalDate.now());
        workout.setFinished(false);
        manager.save(workout);
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

        IWorkout workout = manager.getById(IWorkout.class, id);
        Map<Long, IWorkoutExer> curExers =
                workout.getWorkoutExers().stream().collect(Collectors.toMap(it -> it.getExer().getId(), Function.identity()));
        Map<Long, IWorkoutExer> prevExers = (!workout.getPrevWorkout().isPresent()) ? Collections.emptyMap() :
                workout.getPrevWorkout().get().getWorkoutExers().stream()
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
    public Optional<LocalTime> getTotalTime() {
        return doGetTotalTime();
    }
}
