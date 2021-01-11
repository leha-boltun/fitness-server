package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.fitness.dao.EventTypeRepoAdapter;
import ru.fitness.dao.IEventType;
import ru.fitness.dao.ITimeStamp;
import ru.fitness.dao.IWorkout;
import ru.fitness.dao.TimeStampRepoAdapter;
import ru.fitness.dao.WorkoutRepoAdapter;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkoutMain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WorkoutTest {
    private WorkoutRepoAdapter workoutRepo;
    private TimeStampRepoAdapter timeStampRepo;
    private EventTypeRepoAdapter eventTypeRepo;
    private IEventType eventType1;
    private IEventType eventType2;
    private ITimeStamp timeStamp1;
    private LocalTime time1;
    private Workout workout;
    private IWorkout iWorkout;
    private LocalDate workoutDate1;

    @BeforeEach
    public void beforeEach() {
        workoutRepo = Mockito.mock(WorkoutRepoAdapter.class);
        timeStampRepo = Mockito.mock(TimeStampRepoAdapter.class);
        eventTypeRepo = Mockito.mock(EventTypeRepoAdapter.class);
        workout = new WorkoutImpl(workoutRepo, timeStampRepo, eventTypeRepo);

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
        workout.setWorkoutId(67);
        assertThat(workout.getNextEventName(), equalTo(new DNextEvent("eventName1")));
    }

    @Test
    public void processEventNotLast() {
        when(eventTypeRepo.getNextEventType(67)).thenReturn(Optional.of(eventType1)).thenReturn(Optional.of(eventType2));
        when(timeStampRepo.createTimeStamp()).thenReturn(timeStamp1);
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        when(iWorkout.isFinished()).thenReturn(false);
        workout.setWorkoutId(67);
        assertThat(workout.processNextEvent(), equalTo(new DNextEvent("eventName2")));

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
        when(timeStampRepo.createTimeStamp()).thenReturn(timeStamp1);
        when(workoutRepo.getById(67)).thenReturn(iWorkout);
        when(iWorkout.isFinished()).thenReturn(false);
        workout.setWorkoutId(67);
        assertThat(workout.processNextEvent(), equalTo(new DNextEvent("")));

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
}
