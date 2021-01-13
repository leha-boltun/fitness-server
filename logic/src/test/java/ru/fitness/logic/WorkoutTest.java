package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.fitness.dao.EventTypeRepoAdapter;
import ru.fitness.dao.IEventType;
import ru.fitness.dao.IExer;
import ru.fitness.dao.IProg;
import ru.fitness.dao.IProgExer;
import ru.fitness.dao.ITimeStamp;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.IWorkoutExer;
import ru.fitness.dao.ProgRepoAdapter;
import ru.fitness.dao.TimeStampRepoAdapter;
import ru.fitness.dao.WUserRepoAdapter;
import ru.fitness.dao.WorkoutExerRepoAdapter;
import ru.fitness.dao.WorkoutRepoAdapter;
import ru.fitness.dto.DExer;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkout;
import ru.fitness.dto.DWorkoutMain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WorkoutTest {
    private WorkoutRepoAdapter workoutRepo;
    private TimeStampRepoAdapter timeStampRepo;
    private EventTypeRepoAdapter eventTypeRepo;
    private WorkoutExerRepoAdapter workoutExerRepo;
    private ProgRepoAdapter progRepo;
    private IEventType eventType1;
    private IEventType eventType2;
    private ITimeStamp timeStamp1;
    private LocalTime time1;
    private Workout workout;
    private IWorkout iWorkout;
    private LocalDate workoutDate1;
    private IWorkoutExer workoutExer1;
    private IWorkoutExer workoutExer2;
    private IExer exer1;
    private IExer exer2;

    @BeforeEach
    public void beforeEach() {
        workoutRepo = Mockito.mock(WorkoutRepoAdapter.class);
        timeStampRepo = Mockito.mock(TimeStampRepoAdapter.class);
        eventTypeRepo = Mockito.mock(EventTypeRepoAdapter.class);
        WUserRepoAdapter userRepo = Mockito.mock(WUserRepoAdapter.class);
        progRepo = Mockito.mock(ProgRepoAdapter.class);
        workoutExerRepo = Mockito.mock(WorkoutExerRepoAdapter.class);
        workout = new WorkoutImpl(workoutExerRepo, userRepo, progRepo, workoutRepo, timeStampRepo, eventTypeRepo);

        eventType1 = Mockito.mock(IEventType.class);
        when(eventType1.getName()).thenReturn("eventName1");
        when(eventType1.getEventOrder()).thenReturn(100);
        eventType2 = Mockito.mock(IEventType.class);
        when(eventType2.getName()).thenReturn("eventName2");
        when(eventType2.getEventOrder()).thenReturn(200);

        timeStamp1 = Mockito.mock(ITimeStamp.class);
        time1 = LocalTime.of(10, 10, 10);
        when(timeStamp1.getWtime()).thenReturn(time1);

        iWorkout = Mockito.mock(IWorkout.class);
        workoutDate1 = LocalDate.now();
        when(iWorkout.getWdate()).thenReturn(workoutDate1);
        when(iWorkout.isFinished()).thenReturn(false);

        workoutExer1 = Mockito.mock(IWorkoutExer.class);
        workoutExer2 = Mockito.mock(IWorkoutExer.class);

        exer1 = Mockito.mock(IExer.class);
        exer2 = Mockito.mock(IExer.class);
        when(exer1.getName()).thenReturn("Exer name 1");
        when(exer2.getName()).thenReturn("Exer name 2");
    }

    @Test
    public void getMain() {
        when(workoutRepo.getById(55)).thenReturn(iWorkout);
        workout.setWorkoutId(55);
        assertThat(workout.getMain(), equalTo(new DWorkoutMain(workoutDate1, false)));
    }

    @Test
    public void getTimeStamps() {
        ITimeStamp timeStamp2 = Mockito.mock(ITimeStamp.class);
        LocalTime time2 = LocalTime.of(12, 10, 10);
        when(timeStamp2.getWtime()).thenReturn(time2);
        IEventType eventType1 = Mockito.mock(IEventType.class);
        IEventType eventType2 = Mockito.mock(IEventType.class);
        when(eventType1.getName()).thenReturn("name1");
        when(eventType2.getName()).thenReturn("name2");
        when(timeStamp1.getEventType()).thenReturn(eventType1);
        when(timeStamp2.getEventType()).thenReturn(eventType2);
        when(timeStampRepo.getByWorkoutId(56)).thenReturn(Arrays.asList(timeStamp1, timeStamp2));
        workout.setWorkoutId(56);
        assertThat(workout.getTimeStamps(),
                equalTo(Arrays.asList(new DTimeStampMain(time1, "name1"), new DTimeStampMain(time2, "name2"))));
    }

    @Test
    public void getNextEventName() {
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        when(iWorkout.isFinished()).thenReturn(false);
        when(eventTypeRepo.getNextEventType(67)).thenReturn(Optional.of(eventType1));
        when(eventType1.getEventCode()).thenReturn(EventCode.END.getName());
        workout.setWorkoutId(67);
        assertThat(workout.getNextEventName(), equalTo(new DNextEvent("eventName1", true)));
    }

    @Test
    public void processEventNotLast() {
        when(eventTypeRepo.getNextEventType(67)).thenReturn(Optional.of(eventType1)).thenReturn(Optional.of(eventType2));
        when(eventType1.getEventCode()).thenReturn("rnd1");
        when(eventType2.getEventCode()).thenReturn("rnd2");
        when(timeStampRepo.createTimeStamp()).thenReturn(timeStamp1);
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        when(iWorkout.isFinished()).thenReturn(false);
        workout.setWorkoutId(67);
        assertThat(workout.processNextEvent(), equalTo(new DNextEvent("eventName2", false)));

        ArgumentCaptor<IEventType> nextType = ArgumentCaptor.forClass(IEventType.class);
        verify(timeStamp1).setEventType(nextType.capture());
        assertThat(nextType.getValue(), equalTo(eventType1));
        ArgumentCaptor<IWorkout> workoutArg = ArgumentCaptor.forClass(IWorkout.class);
        verify(timeStamp1).setWorkout(workoutArg.capture());
        assertThat(workoutArg.getValue(), equalTo(iWorkout));
        verify(timeStampRepo).saveTimeStamp(timeStamp1);
    }

    @Test
    public void processEventLast() {
        when(eventTypeRepo.getNextEventType(67)).thenReturn(Optional.of(eventType1)).thenReturn(Optional.empty());
        when(eventType2.getEventCode()).thenReturn("rnd3");
        when(timeStampRepo.createTimeStamp()).thenReturn(timeStamp1);
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        when(iWorkout.isFinished()).thenReturn(false);
        workout.setWorkoutId(67);
        assertThat(workout.processNextEvent(), equalTo(new DNextEvent("", false)));

        ArgumentCaptor<IEventType> nextType = ArgumentCaptor.forClass(IEventType.class);
        verify(timeStamp1).setEventType(nextType.capture());
        assertThat(nextType.getValue(), equalTo(eventType1));
        ArgumentCaptor<IWorkout> workoutArg = ArgumentCaptor.forClass(IWorkout.class);
        verify(timeStamp1).setWorkout(workoutArg.capture());
        assertThat(workoutArg.getValue(), equalTo(iWorkout));
        verify(timeStampRepo).saveTimeStamp(timeStamp1);
        verify(iWorkout).setFinished(true);
        verify(workoutRepo).saveWorkout(iWorkout);
    }

    @Test
    public void createWorkout() {
        IWorkout iWorkout = Mockito.mock(IWorkout.class);
        when(workoutRepo.createWorkout()).thenReturn(iWorkout);
        IProg prog = Mockito.mock(IProg.class);
        when(progRepo.getProg(66)).thenReturn(prog);
        when(prog.getName()).thenReturn("Prog name 1");
        IProgExer progExer1 = Mockito.mock(IProgExer.class);
        IProgExer progExer2 = Mockito.mock(IProgExer.class);
        when(progExer1.getExer()).thenReturn(exer1);
        when(progExer1.getExerOrder()).thenReturn(1);
        when(progExer2.getExer()).thenReturn(exer2);
        when(progExer2.getExerOrder()).thenReturn(5);
        when(prog.getProgExers()).thenReturn(new HashSet<>(Arrays.asList(progExer2, progExer1)));
        when(workoutExerRepo.createExer()).thenReturn(workoutExer1).thenReturn(workoutExer2);
        LocalDate cur = LocalDate.now();
        when(iWorkout.getWdate()).thenReturn(cur);
        when(iWorkout.getId()).thenReturn(21L);
        when(iWorkout.getProg()).thenReturn(prog);
        assertThat(workout.createWorkout(5, 66), equalTo(new DWorkout(21L, cur, "Prog name 1", false)));
        verify(iWorkout).setFinished(false);
        verify(iWorkout).setProg(prog);
        verify(workoutExer1).setExer(exer1);
        verify(workoutExer1).setWorkout(iWorkout);
        verify(workoutExer1).setExerOrder(0);
    }

    @Test
    public void getExers() {
        when(workoutRepo.getById(51)).thenReturn(iWorkout);
        when(iWorkout.getWorkoutExers()).thenReturn(new HashSet<>(Arrays.asList(workoutExer1, workoutExer2)));
        when(workoutExer1.getExer()).thenReturn(exer1);
        when(workoutExer1.getId()).thenReturn(1L);
        when(workoutExer1.getExerOrder()).thenReturn(1);
        when(workoutExer2.getExer()).thenReturn(exer2);
        when(workoutExer2.getId()).thenReturn(2L);
        when(workoutExer2.getExerOrder()).thenReturn(5);
        workout.setWorkoutId(51);
        assertThat(workout.getExers(), equalTo(Arrays.asList(new DExer(1, "Exer name 1"), new DExer(2, "Exer name 2"))));
    }
}
