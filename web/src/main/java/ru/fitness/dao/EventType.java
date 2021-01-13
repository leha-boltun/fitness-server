package ru.fitness.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Objects;

@Entity
public class EventType implements IEventType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventTypeId")
    @SequenceGenerator(
            name = "eventTypeId", sequenceName = "eventTypeId",
            allocationSize = 1
    )
    private Integer id;

    @Version
    private int version;

    private String name;

    private int eventOrder;

    @Override
    public String getEventCode() {
        return eventCode;
    }

    @Override
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    private String eventCode;

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
    public int getEventOrder() {
        return eventOrder;
    }

    @Override
    public void setEventOrder(int eventOrder) {
        this.eventOrder = eventOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventType eventType = (EventType) o;
        return Objects.equals(id, eventType.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
