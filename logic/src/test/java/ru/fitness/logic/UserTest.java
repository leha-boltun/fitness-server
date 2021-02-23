package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IProg;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.IWuser;
import ru.fitness.dao.Manager;
import ru.fitness.dao.ProgManager;
import ru.fitness.dao.WorkoutManager;
import ru.fitness.dto.DProg;
import ru.fitness.dto.DUserMain;
import ru.fitness.dto.DWorkout;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTest {
    private Manager manager;
    private WorkoutManager workoutManager;
    private ProgManager progManager;
    private User user;
    private Workout workout;
    private IWorkout workout1;
    private IWorkout workout2;

    @BeforeEach
    public void beforeEach() {
        manager = Mockito.mock(Manager.class);
        workoutManager = Mockito.mock(WorkoutManager.class);
        workout = Mockito.mock(Workout.class);
        progManager = Mockito.mock(ProgManager.class);
        user = new UserImpl(manager, workoutManager, workout, progManager);
        user.setUserId(5);
        workout1 = Mockito.mock(IWorkout.class);
        workout2 = Mockito.mock(IWorkout.class);
        when(workout1.getId()).thenReturn(1L);
        when(workout2.getId()).thenReturn(2L);
    }

    @Test
    public void getWorkoutsTest() {
        LocalDate cur = LocalDate.now();
        when(workout1.getWdate()).thenReturn(cur);
        when(workout2.getWdate()).thenReturn(cur);
        IProg prog = Mockito.mock(IProg.class);
        when(prog.getName()).thenReturn("Program name 1");
        when(workout1.getProg()).thenReturn(prog);
        when(workout2.getProg()).thenReturn(prog);
        when(workout.getTotalTime()).thenReturn(Optional.empty()).thenReturn(Optional.of(LocalTime.of(2, 0)));
        when(workoutManager.findByUserId(5)).thenReturn(Arrays.asList(workout1, workout2));
        assertThat(user.getWorkouts(),
                equalTo(Arrays.asList(new DWorkout(1L, cur, "Program name 1", false),
                        new DWorkout(2L, cur, "Program name 1", false, LocalTime.of(2, 0)))));
        verify(workoutManager).findByUserId(5);
    }

    @Test
    public void getMainTest() {
        IWuser wuser = Mockito.mock(IWuser.class);
        when(wuser.getName()).thenReturn("user1");
        when(manager.getById(IWuser.class, 5)).thenReturn(wuser);
        user.setUserId(5);
        assertThat(user.getMain(), equalTo(new DUserMain("user1")));
    }

    @Test
    public void getProgsTest() {
        IProg prog1 = Mockito.mock(IProg.class);
        IProg prog2 = Mockito.mock(IProg.class);
        when(prog1.getName()).thenReturn("prog1");
        when(prog1.getId()).thenReturn(1L);
        when(prog2.getName()).thenReturn("prog2");
        when(prog2.getId()).thenReturn(2L);
        when(progManager.getActualProgsByWuserId(7)).thenReturn(Arrays.asList(prog2, prog1));
        user.setUserId(7);
        assertThat(user.getProgs(),
                equalTo(Arrays.asList(new DProg(1, "prog1"), new DProg(2, "prog2"))));
    }
}
