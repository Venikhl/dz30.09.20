package org.step.service;

import java.util.List;

public interface CrudService<T> {

    T save(T t);

    List<T> findAll();

    void delete(Long id);

    void deleteAll();
}
