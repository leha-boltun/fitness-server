package ru.fitness.dao;

public interface IProgExer {
    int getExerOrder();

    void setExerOrder(int exerOrder);

    IProg getProg();

    void setProg(IProg prog);

    IExer getExer();

    void setExer(IExer exer);
}
