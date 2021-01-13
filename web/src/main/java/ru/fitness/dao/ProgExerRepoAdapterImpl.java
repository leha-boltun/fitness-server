package ru.fitness.dao;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgExerRepoAdapterImpl implements ProgExerRepoAdapter {
    private final ProgExerRepository progExerRepo;

    public ProgExerRepoAdapterImpl(ProgExerRepository progExerRepo) {
        this.progExerRepo = progExerRepo;
    }

    @Override
    public List<IProgExer> findByProgId(long progId) {
        return new ArrayList<>(progExerRepo.findByProgId(progId));
    }
}
