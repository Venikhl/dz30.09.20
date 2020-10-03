package org.step.repository.impl;

import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import org.step.entity.Course;
import org.step.entity.Speaker;
import org.step.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SpeakerRepositoryImpl implements CrudRepository<Speaker>  {

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public Speaker save(Speaker speaker) {
            entityManager.persist(speaker);
            return speaker;
        }

        @Override
        public List<Speaker> findAll() {
            return entityManager.createQuery("select s from Speaker s", Speaker.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .getResultList();
        }

        @Override
        public void delete(Long id) {
            entityManager.createQuery("delete from Speaker s where s.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
        }

        @Override
        public void deleteAll() {
            entityManager.createNativeQuery("DELETE FROM SPEAKERS")
                    .executeUpdate();
        }
}
