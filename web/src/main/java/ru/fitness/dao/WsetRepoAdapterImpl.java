package ru.fitness.dao;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class WsetRepoAdapterImpl implements WsetRepoAdapter {
    private final WsetRepository wsetRepository;
    private final EntityManager entityManager;

    public WsetRepoAdapterImpl(WsetRepository wsetRepository, EntityManager entityManager) {
        this.wsetRepository = wsetRepository;
        this.entityManager = entityManager;
    }

    @Override
    public IWset createWset() {
        return new Wset();
    }

    @Override
    public void saveWset(IWset wset) {
        entityManager.persist(wset);
    }

    @Override
    public IWset getById(long id) {
        return wsetRepository.getById(id);
    }

    @Override
    public int getMaxOrder(long workoutExerId) {
        return (int) entityManager
                .createQuery("select coalesce(max(w.wsetOrder), 0) from Wset w join w.workoutExer e where e.id = :id")
                .setParameter("id", workoutExerId).getSingleResult();
    }
}
