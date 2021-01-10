package ru.fitness.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WUserRepoAdapterImpl implements WUserRepoAdapter {
    private final WUserRepository wUserRepository;

    @Autowired
    public WUserRepoAdapterImpl(WUserRepository repository) {
        this.wUserRepository = repository;
    }

    @Override
    public List<IWuser> getAll() {
        return new ArrayList<>(wUserRepository.findAll());
    }

    @Override
    public IWuser getUser(int id) {
        return wUserRepository.findById(id);
    }
}
