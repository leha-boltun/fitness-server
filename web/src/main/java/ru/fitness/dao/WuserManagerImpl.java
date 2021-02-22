package ru.fitness.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WuserManagerImpl implements WuserManager {
    private final WuserManagerRepository wUserManagerRepository;

    @Autowired
    public WuserManagerImpl(WuserManagerRepository repository) {
        this.wUserManagerRepository = repository;
    }

    @Override
    public List<IWuser> getAll() {
        return new ArrayList<>(wUserManagerRepository.findAll());
    }
}
