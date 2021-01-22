package ru.fitness.dao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Wuser implements IWuser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wuserId")
    @SequenceGenerator(
            name = "wuserId", sequenceName = "wuserId",
            allocationSize = 1
    )
    private Integer id;

    @Version
    private int version;

    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "WuserProg",
            joinColumns = { @JoinColumn(name = "wuserId") },
            inverseJoinColumns = { @JoinColumn(name = "progId") }
    )
    private Set<Prog> progs;

    @Override
    public Set<IProg> getProgs() {
        return new HashSet<>(progs);
    }

    @Override
    public void setProgs(Set<IProg> progs) {
        this.progs = progs.stream().map(p -> (Prog) p).collect(Collectors.toSet());
    }

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
