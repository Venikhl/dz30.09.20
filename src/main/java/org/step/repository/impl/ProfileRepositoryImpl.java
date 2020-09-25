package org.step.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.step.entity.Profile;
import org.step.repository.ProfileRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/*
@Component:
1. @Repository
2. @Service
3. @Controller
4. @RestController
 */
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Profile save(Profile profile) {
        entityManager.persist(profile);
        return profile;
    }

    @Override
    public List<Profile> findAll() {
        return entityManager.createQuery("select p from Profile p", Profile.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Profile p where p.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createNativeQuery("DELETE FROM PROFILES")
                .executeUpdate();
    }
}
