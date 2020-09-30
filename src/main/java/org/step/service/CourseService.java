package org.step.service;

import org.step.entity.Course;

import java.util.Collection;
import java.util.List;

public interface CourseService {

    List<Course> findAllByTopicContainsIgnoreCase(String topic);

    Course findByIdWithNative(String id);

    List<Course> findAllByTopicSorted(String topic);

    void saveAll(Collection<Course> courses);

    List<Course> findAllByIdWithSorting();
}
