package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWset;
import ru.fitness.dao.Manager;
import ru.fitness.dao.WorkoutExerManager;
import ru.fitness.dto.DWSetsPrev;
import ru.fitness.dto.DWset;
import ru.fitness.exception.EntityNotFoundException;
import ru.fitness.exception.LogicException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class WorkoutExerImpl implements WorkoutExer {
    private final Manager manager;
    private final WorkoutExerManager workoutExerManager;
    private long id;

    public WorkoutExerImpl(Manager manager, WorkoutExerManager workoutExerManager) {
        this.manager = manager;
        this.workoutExerManager = workoutExerManager;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    private List<DWset> doGetWsets(IWorkoutExer workoutExer) {
        return workoutExer.getWsets().stream().sorted(Comparator.comparingInt(IWset::getWsetOrder))
                .map(wset -> new DWset(wset.getWeight(), wset.getCount(), wset.getId())).collect(Collectors.toList());
    }

    @Override
    public List<DWset> getWsets() {
        IWorkoutExer workoutExer;
        try {
            workoutExer = manager.getById(IWorkoutExer.class, id);
        } catch (EntityNotFoundException ex) {
            throw new LogicException("Workout exercise with id " + id + " was not found", ex, LogicException.ErrorCode.NOT_FOUND);
        }
        return doGetWsets(workoutExer);
    }

    @Override
    public DWSetsPrev getWsetsAndPrevId() {
        Optional<IWorkoutExer> workoutExer = workoutExerManager.getPrevExer(id);
        return workoutExer.map(iWorkoutExer -> new DWSetsPrev(doGetWsets(iWorkoutExer), iWorkoutExer.getId(),
                iWorkoutExer.getWorkout().getWdate())).orElseGet(() -> new DWSetsPrev(Collections.emptyList(), null, null));
    }
}
