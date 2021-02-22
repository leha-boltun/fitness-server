package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWset;
import ru.fitness.dao.Manager;
import ru.fitness.dao.WsetManager;
import ru.fitness.dto.DWset;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WsetTest {
    private Manager manager;
    private WsetManager wsetManager;
    private Wset wset;

    @BeforeEach
    void beforeEach() {
        manager = Mockito.mock(Manager.class);
        wsetManager = Mockito.mock(WsetManager.class);
        wset = new WsetImpl(manager, wsetManager);
    }

    @Test
    void createWset() {
        IWorkoutExer workoutExer = Mockito.mock(IWorkoutExer.class);
        when(manager.getRef(IWorkoutExer.class, 100L)).thenReturn(workoutExer);
        IWset iWset = Mockito.mock(IWset.class);
        when(manager.create(IWset.class)).thenReturn(iWset);
        when(wsetManager.getMaxOrder(100L)).thenReturn(10);
        wset.createWset(100, new DWset("100", "5", 1));
        verify(iWset).setWeight("100");
        verify(iWset).setCount("5");
        verify(iWset).setWsetOrder(11);
        verify(iWset).setWorkoutExer(workoutExer);
        verify(manager).save(iWset);
    }

    @Test
    void editWset() {
        IWorkoutExer workoutExer = Mockito.mock(IWorkoutExer.class);
        when(manager.getRef(IWorkoutExer.class, 100L)).thenReturn(workoutExer);
        IWset iWset = Mockito.mock(IWset.class);
        when(manager.getById(IWset.class, 100L)).thenReturn(iWset);
        wset.editWset(new DWset("100", "5", 100));
        verify(iWset).setWeight("100");
        verify(iWset).setCount("5");
        verify(manager).save(iWset);
    }
}
