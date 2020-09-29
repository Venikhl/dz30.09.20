package org.step.repository.impl;

import org.springframework.stereotype.Repository;
import org.step.entity.Course;
import org.step.repository.CrudRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements CrudRepository<Course> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteAll() {

    }

    @Override
    public Course save(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
