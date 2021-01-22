package ru.fitness.dao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Prog implements IProg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progId")
    @SequenceGenerator(
            name = "progId", sequenceName = "progId",
            allocationSize = 1
    )
    private Long id;

    @OneToMany(mappedBy = "prog", cascade = CascadeType.ALL)
    private Set<ProgExer> progExers;

    @ManyToMany(mappedBy = "progs")
    private Set<Wuser> wusers;

    @Version
    private int version;

    private String name;

    @Override
    public Set<IProgExer> getProgExers() {
        return new HashSet<>(progExers);
    }

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
        Prog prog = (Prog) o;
        return Objects.equals(id, prog.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
