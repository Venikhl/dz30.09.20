package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.step.entity.Course;
import org.step.repository.CourseRepository;
import org.step.service.CourseService;
import org.step.service.CrudService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("courseService")
public class CourseServiceImpl implements CrudService<Course, String>, CourseService {

    private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor();

    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionTemplate transactionTemplate;
    private final CourseRepository courseRepository;
//    private final CrudRepository<Course> courseCrudRepository;

    @Autowired
    public CourseServiceImpl(PlatformTransactionManager platformTransactionManager,
                             CourseRepository courseRepository) {
        this.platformTransactionManager = platformTransactionManager;
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
//        this.courseCrudRepository = courseCrudRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Course save(Course course) {
//        CompletableFuture.supplyAsync(
//                () -> transactionTemplate.execute(status -> courseRepository.save(course)),
//                SERVICE
//        );
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public void delete(String id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        courseRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllByTopicContainsIgnoreCase(String topic) {
        return courseRepository.findAllByTopicContainsIgnoreCase(topic);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findByIdWithNative(String id) {
        return courseRepository.findByIdWithNativeQuery(id)
                .orElseThrow(() -> new RuntimeException(String.format("Course with ID %s not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllByTopicSorted(String topic) {
        Sort sort = Sort.by("topic");

        return courseRepository.findAllByTopicSorted(topic, sort);
    }

    @Override
    @Transactional
    public void saveAll(Collection<Course> courses) {
        courseRepository.saveAll(courses);
    }
}
