package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.IWset;
import ru.fitness.dao.WorkoutExerRepoAdapter;
import ru.fitness.dto.DWset;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class WorkoutExerTest {
    private WorkoutExerRepoAdapter workoutExerRepo;
    private WorkoutExerImpl workoutExer;

    @BeforeEach
    public void beforeEach() {
        workoutExerRepo = Mockito.mock(WorkoutExerRepoAdapter.class);
        workoutExer = new WorkoutExerImpl(workoutExerRepo);
    }

    @Test
    public void getWSets() {
        IWorkoutExer iWorkoutExer = Mockito.mock(IWorkoutExer.class);
        when(workoutExerRepo.getById(57)).thenReturn(iWorkoutExer);

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

        workoutExer.setId(57);
        assertThat(workoutExer.getWsets(), equalTo(Arrays.asList(
                new DWset("56", "5", 1L),
                new DWset("59", "6", 2L))));
    }
}
