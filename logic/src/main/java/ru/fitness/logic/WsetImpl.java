package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.IWset;
import ru.fitness.dao.WorkoutExerRepoAdapter;
import ru.fitness.dao.WsetRepoAdapter;
import ru.fitness.dto.DWset;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class WsetImpl implements Wset {
    private long id;
    private final WsetRepoAdapter wsetRepo;
    private final WorkoutExerRepoAdapter workoutExerRepo;

    public WsetImpl(WsetRepoAdapter wsetRepo, WorkoutExerRepoAdapter workoutExerRepo) {
        this.wsetRepo = wsetRepo;
        this.workoutExerRepo = workoutExerRepo;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void createWset(long workoutExerId, DWset data) {
        IWset wset = wsetRepo.createWset();
        wset.setWorkoutExer(workoutExerRepo.getExerRef(workoutExerId));
        wset.setWeight(data.weight);
        wset.setCount(data.count);
        wset.setWsetOrder(wsetRepo.getMaxOrder(workoutExerId) + 1);
        wsetRepo.saveWset(wset);
    }

    @Override
    public void editWset(DWset data) {
        this.id = data.id;
        IWset wset = wsetRepo.getById(id);
        wset.setWeight(data.weight);
        wset.setCount(data.count);
        wsetRepo.saveWset(wset);
    }
}
