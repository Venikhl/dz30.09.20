package org.step.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.step.configuration.DatabaseConfiguration;
import org.step.entity.Course;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
public class CourseServiceTest {

    @Autowired
    @Qualifier("courseService")
    private CrudService<Course, String> service;

    @Autowired
    private CourseService courseService;

    @Before
    public void setup() {
        Course course = Course.builder()
                .id()
                .topic("topic")
                .courseDescription("description")
                .build();

        service.save(course);
    }

    @Test
    public void shouldSaveCourseToDatabaseTest() {
        Course course = Course.builder()
                .id()
                .topic("topic")
                .courseDescription("description")
                .build();

        Course savedCourse = service.save(course);

        Assert.assertNotNull(savedCourse.getId());
    }

    @Test
    public void shouldFindAllCoursesByTopicContainsIgnoreCase() {
        List<Course> courses = courseService.findAllByTopicContainsIgnoreCase("op");

        Assert.assertFalse(courses.isEmpty());
    }

    @Test
    public void shouldReturnCourseByIdWithNativeQuery() {
        Course course = Course.builder()
                .id()
                .topic("topic")
                .courseDescription("description")
                .build();

        service.save(course);

        Course byIdWithNative = courseService.findByIdWithNative(course.getId());

        Assert.assertNotNull(byIdWithNative);
    }

    @Test
    public void shouldReturnSortedTopic() {
        List<Course> courses = Stream.generate(() -> Course.builder()
                .id()
                .topic("topic")
                .courseDescription("description")
                .build())
                .limit(100)
                .collect(Collectors.toList());

        courseService.saveAll(courses);

        List<Course> topic = courseService.findAllByTopicSorted("topic");
    }
}
