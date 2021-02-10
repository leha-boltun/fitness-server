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
import ru.fitness.exception.NoTimestampException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        time1 = LocalTime.of(10, 0, 0);
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
        when(exer1.getId()).thenReturn(1L);
        when(exer2.getId()).thenReturn(2L);
    }

    @Test
    public void getMain() {
        when(iWorkout.getWuserId()).thenReturn(77);
        when(iWorkout.isFinished()).thenReturn(true);
        when(iWorkout.getWeight()).thenReturn(new BigDecimal("2.4"));
        when(workoutRepo.getById(55)).thenReturn(iWorkout);
        when(timeStampRepo.getFirstTimeStamp(55)).thenThrow(NoTimestampException.class);
        when(timeStampRepo.getLastTimeStamp(55)).thenThrow(NoTimestampException.class);
        IWorkout workout2 = Mockito.mock(IWorkout.class);
        when(iWorkout.getPrevWorkout()).thenReturn(workout2);
        when(workout2.getWeight()).thenReturn(new BigDecimal("1.2"));
        IWorkout workout3 = Mockito.mock(IWorkout.class);
        when(workoutRepo.getPrevById(55)).thenReturn(workout3);
        when(workout3.getWeight()).thenReturn(new BigDecimal("1.0"));
        workout.setWorkoutId(55);

        assertThat(workout.getMain(),
                equalTo(new DWorkoutMain(77, workoutDate1, true,
                        new BigDecimal("2.4"), null, new BigDecimal("1.4"), new BigDecimal("1.2"))));
    }

    @Test
    public void getTimeStamps() {
        ITimeStamp timeStamp2 = Mockito.mock(ITimeStamp.class);
        LocalTime time2 = LocalTime.of(8, 0, 0);
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
                equalTo(Arrays.asList(new DTimeStampMain(time1, "name1", LocalTime.of(2, 0, 0)),
                        new DTimeStampMain(time2, "name2"))));
    }

    @Test
    public void getNextEventName() {
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        when(iWorkout.isFinished()).thenReturn(false);
        when(eventTypeRepo.getNextEventType(67)).thenReturn(Optional.of(eventType1));
        when(eventType1.getEventCode()).thenReturn(EventCode.END.getName());
        workout.setWorkoutId(67);
        assertThat(workout.getNextEventName(), equalTo(new DNextEvent("eventName1", true, false)));
    }

    @Test
    public void processEventNotLast() {
        when(eventTypeRepo.getNextEventType(67)).thenReturn(Optional.of(eventType1)).thenReturn(Optional.of(eventType2));
        when(eventType1.getEventCode()).thenReturn(EventCode.BEFORE_BEGIN.getName());
        when(eventType2.getEventCode()).thenReturn("rnd2");
        when(timeStampRepo.createTimeStamp()).thenReturn(timeStamp1);
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        when(iWorkout.isFinished()).thenReturn(false);
        workout.setWorkoutId(67);
        assertThat(workout.processNextEvent(), equalTo(new DNextEvent("eventName2", false, true)));

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
        assertThat(workout.processNextEvent(), equalTo(new DNextEvent("", false, true)));

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
        IWorkout iWorkoutPrev = Mockito.mock(IWorkout.class);
        when(workoutRepo.getLastByProgId(1)).thenReturn(iWorkoutPrev);
        assertThat(workout.createWorkout(5, 66, 1),
                equalTo(new DWorkout(21L, cur, "Prog name 1", false)));
        verify(iWorkout).setFinished(false);
        verify(iWorkout).setProg(prog);
        verify(workoutExer1).setExer(exer1);
        verify(workoutExer1).setWorkout(iWorkout);
        verify(workoutExer1).setExerOrder(0);
        verify(iWorkout).setPrevWorkout(iWorkoutPrev);
    }

    @Test
    public void createWorkoutPrevProg() {
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
        IWorkout iWorkoutPrev = Mockito.mock(IWorkout.class);
        when(workoutRepo.getLastByProgId(1)).thenReturn(null);
        when(progRepo.getProg(1)).thenReturn(prog);
        when(prog.getPrevProgId()).thenReturn(81);
        when(workoutRepo.getLastByProgId(81)).thenReturn(iWorkoutPrev);
        assertThat(workout.createWorkout(5, 66, 1),
                equalTo(new DWorkout(21L, cur, "Prog name 1", false)));
        verify(iWorkout).setFinished(false);
        verify(iWorkout).setProg(prog);
        verify(workoutExer1).setExer(exer1);
        verify(workoutExer1).setWorkout(iWorkout);
        verify(workoutExer1).setExerOrder(0);
        verify(iWorkout).setPrevWorkout(iWorkoutPrev);
    }

    @Test
    public void setWeight() {
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        workout.setWorkoutId(67);
        BigDecimal weight = new BigDecimal("68.9");
        workout.setWeight(weight);
        verify(iWorkout).setWeight(weight);
        verify(workoutRepo).saveWorkout(iWorkout);
    }

    @Test
    public void getTotalTime() {
        ITimeStamp timeStamp2 = Mockito.mock(ITimeStamp.class);
        when(timeStampRepo.getFirstTimeStamp(67)).thenReturn(timeStamp2);
        LocalTime firstTime = LocalTime.now().minus(3, ChronoUnit.MINUTES);
        when(timeStamp2.getWtime()).thenReturn(firstTime);

        ITimeStamp timeStamp3 = Mockito.mock(ITimeStamp.class);
        when(timeStampRepo.getLastTimeStamp(67)).thenReturn(timeStamp3);
        LocalTime lastTime = firstTime.plus(1, ChronoUnit.MINUTES);
        when(timeStamp3.getWtime()).thenReturn(lastTime);

        when(timeStampRepo.getFirstTimeStamp(67)).thenReturn(timeStamp2);
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        workout.setWorkoutId(67);
        assertThat(workout.getTotalTime(), equalTo(LocalTime.of(0, 1, 0)));
    }

    @Test
    public void getExersNoPrev() {
        when(workoutRepo.getById(51)).thenReturn(iWorkout);
        when(iWorkout.getWorkoutExers()).thenReturn(new HashSet<>(Arrays.asList(workoutExer1, workoutExer2)));
        when(workoutExer1.getExer()).thenReturn(exer1);
        when(workoutExer1.getId()).thenReturn(1L);
        when(workoutExer2.getId()).thenReturn(2L);
        when(workoutExer1.getExerOrder()).thenReturn(1);
        when(workoutExer2.getExer()).thenReturn(exer2);
        when(workoutExer2.getExerOrder()).thenReturn(5);
        workout.setWorkoutId(51);
        assertThat(workout.getExers(), equalTo(Arrays.asList(new DExer(1L, "Exer name 1"),
                new DExer(2L, "Exer name 2"))));
    }

    @Test
    public void getExersPrev() {
        when(workoutRepo.getById(51)).thenReturn(iWorkout);
        when(iWorkout.getWorkoutExers()).thenReturn(new HashSet<>(Arrays.asList(workoutExer1, workoutExer2)));
        when(workoutExer1.getExer()).thenReturn(exer1);
        when(workoutExer1.getId()).thenReturn(1L);
        when(workoutExer1.getExerOrder()).thenReturn(1);
        when(workoutExer2.getExer()).thenReturn(exer2);
        when(workoutExer2.getId()).thenReturn(2L);
        when(workoutExer2.getExerOrder()).thenReturn(5);

        IExer exer4 = Mockito.mock(IExer.class);
        when(exer4.getName()).thenReturn("Exer name 4");
        when(exer4.getId()).thenReturn(16L);

        IWorkout workout2 = Mockito.mock(IWorkout.class);
        when(iWorkout.getPrevWorkout()).thenReturn(workout2);
        IWorkoutExer workoutExer3 = Mockito.mock(IWorkoutExer.class);
        when(workoutExer3.getExer()).thenReturn(exer4);
        when(workoutExer3.getId()).thenReturn(7L);
        when(workoutExer3.getExerOrder()).thenReturn(10);
        IWorkoutExer workoutExer4 = Mockito.mock(IWorkoutExer.class);
        when(workoutExer4.getExer()).thenReturn(exer1);
        when(workoutExer4.getId()).thenReturn(9L);
        when(workoutExer4.getExerOrder()).thenReturn(2);
        when(workout2.getWorkoutExers()).thenReturn(new HashSet<>(Arrays.asList(workoutExer3, workoutExer4)));

        workout.setWorkoutId(51);
        assertThat(workout.getExers(), equalTo(Arrays.asList(new DExer(1L, "Exer name 1", 9L),
                new DExer(2L, "Exer name 2"), new DExer("Exer name 4", 7L))));
    }
}
