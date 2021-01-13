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
}
