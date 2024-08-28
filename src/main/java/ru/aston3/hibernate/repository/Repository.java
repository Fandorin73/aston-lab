package ru.aston3.hibernate.repository;

import java.util.List;

public interface Repository<T, K> {

    T save(T t);

    List<T> findAll();

    boolean deleteById(K id);
}
