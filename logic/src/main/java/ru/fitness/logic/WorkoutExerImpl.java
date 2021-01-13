package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWset;
import ru.fitness.dao.WorkoutExerRepoAdapter;
import ru.fitness.dto.DWset;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class WorkoutExerImpl implements WorkoutExer {
    private long id;
    private final WorkoutExerRepoAdapter workoutExerRepoAdapter;

    public WorkoutExerImpl(WorkoutExerRepoAdapter workoutExerRepoAdapter) {
        this.workoutExerRepoAdapter = workoutExerRepoAdapter;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public List<DWset> getWsets() {
        IWorkoutExer workoutExer = workoutExerRepoAdapter.getById(id);
        return workoutExer.getWsets().stream().sorted(Comparator.comparingInt(IWset::getWsetOrder))
                .map(wset -> new DWset(wset.getWeight(), wset.getCount())).collect(Collectors.toList());
    }
}
