package ru.fitness.exception;

public class EntityNotFoundException extends RuntimeException {
    private final Class<?> clazz;
    private final Object id;

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getId() {
        return id;
    }

    public EntityNotFoundException(Class<?> clazz, Object id) {
        super(clazz.getCanonicalName() + " with id " + id);
        this.clazz = clazz;
        this.id = id;
    }
}
