package ru.fitness.dao;

public interface IEventType {

    String getEventCode();

    void setEventCode(String eventCode);

    Integer getId();

    String getName();

    void setName(String name);

    int getEventOrder();

    void setEventOrder(int eventOrder);
}
