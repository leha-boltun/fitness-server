package ru.fitness.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Objects;

@Entity
public class Wuser implements IWuser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wuserId")
    @SequenceGenerator(
            name = "wuserId", sequenceName = "wuserId",
            allocationSize = 10
    )
    private Integer id;

    @Version
    private int version;

    private String name;

    @Override
    public Integer getId() {
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
        Wuser wuser = (Wuser) o;
        return Objects.equals(id, wuser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
