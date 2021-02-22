package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWset;
import ru.fitness.dao.Manager;
import ru.fitness.dao.WorkoutExerManager;
import ru.fitness.dto.DWSetsPrev;
import ru.fitness.dto.DWset;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class WorkoutExerTest {
    private Manager manager;
    private WorkoutExerManager workoutExerManager;
    private WorkoutExerImpl workoutExer;
    private IWorkoutExer iWorkoutExer;

    @BeforeEach
    public void beforeEach() {
        manager = Mockito.mock(Manager.class);
        workoutExerManager = Mockito.mock(WorkoutExerManager.class);
        workoutExer = new WorkoutExerImpl(manager, workoutExerManager);

        iWorkoutExer = Mockito.mock(IWorkoutExer.class);

        IWset wset1 = Mockito.mock(IWset.class);
        IWset wset2 = Mockito.mock(IWset.class);
        when(wset1.getWsetOrder()).thenReturn(1);
        when(wset2.getWsetOrder()).thenReturn(2);
        when(wset1.getWeight()).thenReturn("56");
        when(wset2.getWeight()).thenReturn("59");
        when(wset1.getCount()).thenReturn("5");
        when(wset2.getCount()).thenReturn("6");
        when(wset1.getId()).thenReturn(1L);
        when(wset2.getId()).thenReturn(2L);
        when(iWorkoutExer.getWsets()).thenReturn(new HashSet<>(Arrays.asList(wset2, wset1)));
    }

    @Test
    public void getWSets() {
        when(manager.getById(IWorkoutExer.class, 57L)).thenReturn(iWorkoutExer);

        workoutExer.setId(57);
        assertThat(workoutExer.getWsets(), equalTo(Arrays.asList(
                new DWset("56", "5", 1L),
                new DWset("59", "6", 2L))));
    }

    @Test
    public void getPrevWsets() {
        when(workoutExerManager.getPrevExer(57L)).thenReturn(Optional.of(iWorkoutExer));
        when(iWorkoutExer.getId()).thenReturn(57L);
        IWorkout workout = Mockito.mock(IWorkout.class);
        when(workout.getWdate()).thenReturn(LocalDate.of(2020, 1, 2));
        when(iWorkoutExer.getWorkout()).thenReturn(workout);

        workoutExer.setId(57);
        assertThat(workoutExer.getWsetsAndPrevId(), equalTo(new DWSetsPrev(Arrays.asList(
                new DWset("56", "5", 1L),
                new DWset("59", "6", 2L)), 57L, LocalDate.of(2020, 1, 2))));
    }
}
