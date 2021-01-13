package ru.fitness.dao;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgRepoAdapterImpl implements ProgRepoAdapter {
    private final ProgRepository progRepo;
    private final EntityManager entityManager;

    public ProgRepoAdapterImpl(ProgRepository progRepo, EntityManager entityManager) {
        this.progRepo = progRepo;
        this.entityManager = entityManager;
    }

    @Override
    public IProg getProg(long id) {
        return progRepo.getProgById(id);
    }

    @Override
    public List<IProg> getProgs() {
        return new ArrayList<>(progRepo.findAll());
    }
}
