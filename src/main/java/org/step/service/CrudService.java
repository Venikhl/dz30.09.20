package org.step.service;

import java.util.List;

public interface CrudService<T, ID> {

    T save(T t);

    List<T> findAll();

    void delete(ID id);

    void deleteAll();
}
