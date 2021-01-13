package ru.fitness.dao;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Objects;

@Entity
public class WorkoutExer implements IWorkoutExer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workoutExerId")
    @SequenceGenerator(
            name = "workoutExerId", sequenceName = "workoutExerId",
            allocationSize = 1
    )
    private Long id;

    @Version
    private int version;

    private int exerOrder;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "workoutId")
    private Workout workout;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "exerId")
    private Exer exer;

    @Override
    public Long getId() {
        return id;
    }

    public IWorkout getWorkout() {
        return workout;
    }

    public void setWorkout(IWorkout workout) {
        this.workout = (Workout) workout;
    }

    @Override
    public int getExerOrder() {
        return exerOrder;
    }

    @Override
    public void setExerOrder(int exerOrder) {
        this.exerOrder = exerOrder;
    }

    @Override
    public IExer getExer() {
        return exer;
    }

    @Override
    public void setExer(IExer exer) {
        this.exer = (Exer) exer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutExer that = (WorkoutExer) o;
        return Objects.equals(workout.getId(), that.workout.getId()) &&
                Objects.equals(exer.getId(), that.exer.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
