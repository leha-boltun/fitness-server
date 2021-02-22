package ru.fitness.dao;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgManagerImpl implements ProgManager {
    private final ProgRepository progRepo;

    public ProgManagerImpl(ProgRepository progRepo) {
        this.progRepo = progRepo;
    }

    @Override
    public List<IProg> getProgs() {
        return new ArrayList<>(progRepo.findAll());
    }

    @Override
    public List<IProg> getActualProgsByWuserId(int wuserId) {
        return new ArrayList<>(progRepo.getActualProgsByWuserId(wuserId));
    }
}
