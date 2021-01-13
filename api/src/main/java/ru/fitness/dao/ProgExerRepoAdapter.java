package ru.fitness.dao;

import java.util.List;

public interface ProgExerRepoAdapter {
    List<IProgExer> findByProgId(long progId);
}
