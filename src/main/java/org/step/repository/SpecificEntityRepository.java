package org.step.repository;


import org.hibernate.FlushMode;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;

/*
NoRepositoryBean - говорит Spring Data не создавать из этого интерфейса репозиторий
 */
@NoRepositoryBean
public interface SpecificEntityRepository<T, ID> extends JpaRepository<T, ID> {

    @Query("select e from #{#entityName} e")
    List<T> findAllWithSortingById(Sort sort);

    @Query("select e from #{#entityName} e where e.id in ?1")
    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")
    }, forCounting = false)
    List<T> findAllInCollectionEntities(Collection<ID> ids);
}
