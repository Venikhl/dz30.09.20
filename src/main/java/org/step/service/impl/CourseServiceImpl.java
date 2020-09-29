package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.step.entity.Course;
import org.step.repository.CrudRepository;
import org.step.service.CrudService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CourseServiceImpl implements CrudService<Course> {

    private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor();

    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionTemplate transactionTemplate;
    private final CrudRepository<Course> courseCrudRepository;

    @Autowired
    public CourseServiceImpl(PlatformTransactionManager platformTransactionManager, CrudRepository<Course> courseCrudRepository) {
        this.platformTransactionManager = platformTransactionManager;
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.courseCrudRepository = courseCrudRepository;
    }

    @Override
    public Course save(Course course) {
        CompletableFuture.supplyAsync(
                () -> transactionTemplate.execute(status -> courseCrudRepository.save(course)),
                SERVICE);

        return course;
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
