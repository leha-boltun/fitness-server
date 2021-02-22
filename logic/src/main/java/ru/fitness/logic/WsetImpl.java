package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWset;
import ru.fitness.dao.Manager;
import ru.fitness.dao.WsetManager;
import ru.fitness.dto.DWset;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class WsetImpl implements Wset {
    private final Manager manager;
    private long id;
    private final WsetManager wsetManager;

    public WsetImpl(Manager manager, WsetManager wsetManager) {
        this.manager = manager;
        this.wsetManager = wsetManager;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void createWset(long workoutExerId, DWset data) {
        IWset wset = manager.create(IWset.class);
        wset.setWorkoutExer(manager.getRef(IWorkoutExer.class, workoutExerId));
        wset.setWeight(data.weight);
        wset.setCount(data.count);
        wset.setWsetOrder(wsetManager.getMaxOrder(workoutExerId) + 1);
        manager.save(wset);
    }

    @Override
    public void editWset(DWset data) {
        this.id = data.id;
        IWset wset = manager.getById(IWset.class, id);
        wset.setWeight(data.weight);
        wset.setCount(data.count);
        manager.save(wset);
    }
}
