package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IProg;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.IWuser;
import ru.fitness.dao.WUserRepoAdapter;
import ru.fitness.dao.WorkoutRepoAdapter;
import ru.fitness.dto.DUserMain;
import ru.fitness.dto.DWorkout;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTest {
    private WorkoutRepoAdapter workoutRepo;
    private WUserRepoAdapter userRepo;
    private User user;
    private IWorkout workout1;
    private IWorkout workout2;

    @BeforeEach
    public void beforeEach() {
        workoutRepo = Mockito.mock(WorkoutRepoAdapter.class);
        userRepo = Mockito.mock(WUserRepoAdapter.class);
        user = new UserImpl(workoutRepo, userRepo);
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
        when(workoutRepo.findByUserId(5)).thenReturn(Arrays.asList(workout1, workout2));
        assertThat(user.getWorkouts(),
                equalTo(Arrays.asList(new DWorkout(1L, cur, "Program name 1", false), new DWorkout(2L, cur, "Program name 1", false))));
        verify(workoutRepo).findByUserId(5);
    }

    @Test
    public void getMainTest() {
        IWuser wuser = Mockito.mock(IWuser.class);
        when(wuser.getName()).thenReturn("user1");
        when(userRepo.getUser(5)).thenReturn(wuser);
        user.setUserId(5);
        assertThat(user.getMain(), equalTo(new DUserMain("user1")));
    }
}
