package ru.fitness.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.util.Objects;

@Entity
@IdClass(ProgExerKey.class)
public class ProgExer implements IProgExer {
    @Version
    private int version;

    private int exerOrder;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "progId")
    private Prog prog;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "exerId")
    private Exer exer;

    @Override
    public int getExerOrder() {
        return exerOrder;
    }

    @Override
    public void setExerOrder(int exerOrder) {
        this.exerOrder = exerOrder;
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
        ProgExer progExer = (ProgExer) o;
        return Objects.equals(prog.getId(), progExer.prog.getId()) &&
                Objects.equals(exer.getId(), progExer.exer.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
