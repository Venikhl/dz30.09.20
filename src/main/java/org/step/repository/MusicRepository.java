package org.step.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.step.entity.Music;
import org.step.entity.Speaker;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends SpecificEntityRepository<Music, String> {

    @Query(value = "select m from Music m")
    List<Speaker> findAllByDeclaredQuery();

    @Query(value = "select m from Music m where m.name=?1")
    Optional<Speaker> findSpeakerByName(String name);

    @Modifying
    @Query("delete from Speaker s where s.name=:name")
    void deleteSpeakerFromDb(@Param("id") String name);
}