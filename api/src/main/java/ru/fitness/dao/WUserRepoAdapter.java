package ru.fitness.dao;

import java.util.List;

public interface WUserRepoAdapter {

    List<IWuser> getAll();

    IWuser getUser(int id);
}
