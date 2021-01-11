package ru.fitness.dao;

public interface IEventType {

    Integer getId();

    String getName();

    void setName(String name);

    int getEventOrder();

    void setEventOrder(int eventOrder);
}
