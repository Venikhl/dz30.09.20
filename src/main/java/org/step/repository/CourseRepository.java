package org.step.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.step.entity.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends SpecificEntityRepository<Course, String> {

    List<Course> findAllByTopicContainsIgnoreCase(String topic);

    List<Course> findAllNamedQuery();

    @Query(value = "select c from Course c")
    List<Course> findAllByDeclaredQuery();

    @Query(value = "select c from Course c where c.topic=?1")
    Optional<Course> findCourseBySpecialTopic(String specialTopic);

    @Query(value = "select c from Course c where c.topic=:topic")
    Optional<Course> findCourseBySpecialTopicSecondEdition(@Param("topic") String specialTopic);

    @Query(nativeQuery = true, value = "SELECT * FROM COURSES WHERE ID=?1")
    Optional<Course> findByIdWithNativeQuery(String id);

    @Query("select c from Course c where c.topic like %?1%")
    List<Course> findAllByTopicSorted(String topic, Sort sort);

    @Modifying(
//            flushAutomatically = true, // Синхронизация контекста хибернейта с бд
//            clearAutomatically = true  // Очищение контекста хибернейта от удаленных ентити
    )
    @Query("update Course c set c.courseDescription=?1")
    void updateCourseDescription(String courseDescription);

    @Modifying
    @Query("delete from Course c where c.id=:id")
    void deleteCourseFromDb(@Param("id") String id);
}