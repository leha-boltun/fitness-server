package ru.fitness.dao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.time.LocalTime;

@Entity
public class TimeStamp implements ITimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeStampId")
    @SequenceGenerator(
            name = "timeStampId", sequenceName = "timeStampId",
            allocationSize = 1
    )
    private Long id;

    @Version
    private int version;

    private LocalTime wtime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "workoutId", updatable = false)
    private Workout workout;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "eventTypeId", updatable = false)
    private EventType eventType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public LocalTime getWtime() {
        return wtime;
    }

    @Override
    public void setWtime(LocalTime wtime) {
        this.wtime = wtime;
    }

    @Override
    public IWorkout getWorkout() {
        return workout;
    }

    @Override
    public void setWorkout(IWorkout workout) {
        this.workout = (Workout) workout;
    }

    @Override
    public IEventType getEventType() {
        return eventType;
    }

    @Override
    public void setEventType(IEventType eventType) {
        this.eventType = (EventType) eventType;
    }
}
