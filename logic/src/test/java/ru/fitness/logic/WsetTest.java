package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWset;
import ru.fitness.dao.WorkoutExerRepoAdapter;
import ru.fitness.dao.WsetRepoAdapter;
import ru.fitness.dto.DWset;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WsetTest {
    private WsetRepoAdapter wsetRepo;
    private WorkoutExerRepoAdapter workoutExerRepo;
    private Wset wset;

    @BeforeEach
    void beforeEach() {
        wsetRepo = Mockito.mock(WsetRepoAdapter.class);
        workoutExerRepo = Mockito.mock(WorkoutExerRepoAdapter.class);
        wset = new WsetImpl(wsetRepo, workoutExerRepo);
    }

    @Test
    void createWset() {
        IWorkoutExer workoutExer = Mockito.mock(IWorkoutExer.class);
        when(workoutExerRepo.getExerRef(100)).thenReturn(workoutExer);
        IWset iWset = Mockito.mock(IWset.class);
        when(wsetRepo.createWset()).thenReturn(iWset);
        wset.createWset(100, new DWset("100", "5", 1));
        verify(iWset).setWeight("100");
        verify(iWset).setCount("5");
        verify(iWset).setWorkoutExer(workoutExer);
        verify(wsetRepo).saveWset(iWset);
    }

    @Test
    void editWset() {
        IWorkoutExer workoutExer = Mockito.mock(IWorkoutExer.class);
        when(workoutExerRepo.getExerRef(100)).thenReturn(workoutExer);
        IWset iWset = Mockito.mock(IWset.class);
        when(wsetRepo.getById(100)).thenReturn(iWset);
        wset.editWset(new DWset("100", "5", 100));
        verify(iWset).setWeight("100");
        verify(iWset).setCount("5");
        verify(wsetRepo).saveWset(iWset);
    }
}
