package ru.fitness.dao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Workout implements IWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workoutId")
    @SequenceGenerator(
            name = "workoutId", sequenceName = "workoutId",
            allocationSize = 1
    )
    private Long id;

    @Version
    private int version;

    private LocalDate wdate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "wuserId", updatable = false)
    private Wuser wuser;

    @Column(updatable = false, insertable = false)
    private Integer wuserId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "progId", updatable = false)
    private Prog prog;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private Set<WorkoutExer> workoutExers;

    private boolean finished;

    private BigDecimal weight;

    private LocalTime totalTime;

    @Override
    public LocalTime getTotalTime() {
        return totalTime;
    }

    @Override
    public void setTotalTime(LocalTime totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

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

    @Override
    public IProg getProg() {
        return prog;
    }

    @Override
    public void setProg(IProg prog) {
        this.prog = (Prog) prog;
    }

    @Override
    public Set<IWorkoutExer> getWorkoutExers() {
        return new HashSet<>(workoutExers);
    }

    @Override
    public void setWorkoutExers(Set<IWorkoutExer> workoutExers) {
        this.workoutExers = workoutExers.stream().map((e) -> (WorkoutExer) e).collect(Collectors.toSet());
    }

    @Override
    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public Integer getWuserId() {
        return wuserId;
    }
}
