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
import java.util.Objects;

@Entity
public class Wset implements IWset {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wsetId")
    @SequenceGenerator(
            name = "wsetId", sequenceName = "wsetId",
            allocationSize = 1
    )
    private Long id;

    @Version
    private int version;

    private String weight;

    private String count;

    private int wsetOrder;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "workoutExerId", updatable = false)
    private WorkoutExer workoutExer;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getWeight() {
        return weight;
    }

    @Override
    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public IWorkoutExer getWorkoutExer() {
        return workoutExer;
    }

    @Override
    public void setWorkoutExer(IWorkoutExer workoutExer) {
        this.workoutExer = (WorkoutExer) workoutExer;
    }

    @Override
    public int getWsetOrder() {
        return wsetOrder;
    }

    @Override
    public void setWsetOrder(int wsetOrder) {
        this.wsetOrder = wsetOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wset wset = (Wset) o;
        return Objects.equals(id, wset.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String getCount() {
        return count;
    }

    @Override
    public void setCount(String count) {
        this.count = count;
    }
}
