package ru.fitness.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Workout implements IWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workoutId")
    @SequenceGenerator(
            name = "workoutId", sequenceName = "workoutId",
            allocationSize = 10
    )
    private Long id;

    @Version
    private int version;

    private LocalDate wdate;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Wuser wuser;

    @Override
    public IWuser getWUser() {
        return wuser;
    }

    @Override
    public void setWUser(IWuser wUser) {
        this.wuser = (Wuser) wUser;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public LocalDate getWdate() {
        return wdate;
    }

    @Override
    public void setWdate(LocalDate wdate) {
        this.wdate = wdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
