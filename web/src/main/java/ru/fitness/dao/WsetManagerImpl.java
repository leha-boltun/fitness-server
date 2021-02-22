package ru.fitness.dao;

import org.springframework.stereotype.Service;

@Service
public class WsetManagerImpl implements WsetManager {
    private final WsetManagerRepository wsetManagerRepository;

    public WsetManagerImpl(WsetManagerRepository wsetManagerRepository) {
        this.wsetManagerRepository = wsetManagerRepository;
    }

    @Override
    public int getMaxOrder(long workoutExerId) {
        return wsetManagerRepository.getMaxOrder(workoutExerId);
    }
}
