package org.step.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.step.entity.Course;
import org.step.entity.Music;
import org.step.entity.Speaker;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeakerRepository extends SpecificEntityRepository<Speaker, String> {

    @Query(value = "select s from Speaker s")
    List<Speaker> findAllByDeclaredQuery();

    @Query(value = "select s from Speaker s where s.name=?1")
    Optional<Speaker> findSpeakerByName(String name);

    @Query("update Speaker s set s.name=?1")
    void updateSpeakerName(String name);


    @Query("update Speaker s set s.music=?1")
    void updateSpeakerMusic(Music music);

    @Modifying
    @Query("delete from Speaker s where s.id=:id")
    void deleteSpeakerFromDb(@Param("id") Long id);
}