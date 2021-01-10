package ru.fitness.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class WUserRepoAdapterImpl implements WUserRepoAdapter {
    private final WUserRepository wUserRepository;
    private final EntityManager entityManager;

    @Autowired
    public WUserRepoAdapterImpl(WUserRepository repository, EntityManager entityManager) {
        this.wUserRepository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public List<IWuser> getAll() {
        return new ArrayList<>(wUserRepository.findAll());
    }

    @Override
    public IWuser getUser(int id) {
        return wUserRepository.findById(id);
    }

    @Override
    public IWuser getUserRef(int id) {
        return entityManager.getReference(Wuser.class, id);
    }
}
