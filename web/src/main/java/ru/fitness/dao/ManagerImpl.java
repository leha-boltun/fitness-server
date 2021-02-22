package ru.fitness.dao;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;
import ru.fitness.exception.UnexpectedException;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class ManagerImpl implements Manager {
    private static final Map<Class<?>, Class<?>> interfaceToEntity = new HashMap<>();

    static {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isInterface();
            }
        };
        String packageName = ManagerImpl.class.getPackage().getName();
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(packageName + ".I[A-Z].*")));

        Set<BeanDefinition> classes = provider.findCandidateComponents(packageName);

        for (BeanDefinition bean: classes) {
            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                Class<?> implClazz = Class.forName(packageName + "." + clazz.getSimpleName().substring(1));
                interfaceToEntity.put(clazz, implClazz);
            } catch (ClassNotFoundException exception) {
                throw new UnexpectedException(exception);
            }
        }
    }

    private final EntityManager entityManager;

    public ManagerImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Class<?> getEntityFromInterface(Class<?> clazz) {
        Class<?> entityClazz = interfaceToEntity.get(clazz);
        if (entityClazz == null) {
            throw new UnexpectedException("Wrong class " + clazz.getCanonicalName());
        }
        return entityClazz;
    }

    @Override
    public <T> T getById(Class<T> entityClass, int id) {
        //noinspection unchecked
        return (T) entityManager.find(getEntityFromInterface(entityClass), id);
    }

    @Override
    public <T> T getById(Class<T> entityClass, long id) {
        //noinspection unchecked
        return (T) entityManager.find(getEntityFromInterface(entityClass), id);
    }

    @Override
    public <T> T getRef(Class<T> entityClass, int id) {
        //noinspection unchecked
        return (T) entityManager.getReference(getEntityFromInterface(entityClass), id);
    }

    @Override
    public <T> T getRef(Class<T> entityClass, long id) {
        //noinspection unchecked
        return (T) entityManager.getReference(getEntityFromInterface(entityClass), id);
    }

    @Override
    public <T> void save(T obj) {
        entityManager.persist(obj);
    }

    @Override
    public <T> void remove(T obj) {
        entityManager.remove(obj);
    }

    @Override
    public <T> T create(Class<T> entityClass) {
        //noinspection unchecked
        Class<T> clazz = (Class<T>) getEntityFromInterface(entityClass);
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UnexpectedException(e);
        }
    }

    @Override
    public void flush() {
        entityManager.flush();
    }
}
