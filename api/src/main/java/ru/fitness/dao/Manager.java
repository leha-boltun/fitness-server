package ru.fitness.dao;

public interface Manager {
    <T> T getById(Class<T> entityClass, int id);
    <T> T getById(Class<T> entityClass, long id);
    <T> T getRef(Class<T> entityClass, int id);
    <T> T getRef(Class<T> entityClass, long id);
    <T> void save(T obj);
    <T> void remove(T obj);
    <T> T create(Class<T> entityClass);
    void flush();

}
