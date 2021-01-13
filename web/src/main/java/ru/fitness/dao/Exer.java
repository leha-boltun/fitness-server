package ru.fitness.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Objects;

@Entity
public class Exer implements IExer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exerId")
    @SequenceGenerator(
            name = "exerId", sequenceName = "exerId",
            allocationSize = 1
    )
    private Long id;

    @Version
    private int version;

    private String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exer exer = (Exer) o;
        return Objects.equals(id, exer.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
