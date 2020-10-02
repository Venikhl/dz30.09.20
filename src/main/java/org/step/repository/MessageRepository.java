package org.step.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.step.entity.Message;
import org.step.entity.Message_;
import org.step.entity.dto.MessageDTO;
import org.step.entity.projection.MessageOpenProjection;
import org.step.entity.projection.MessageProjection;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.step.entity.Message.USER_ENTITY_GRAPH;

@Repository
public interface MessageRepository extends SpecificEntityRepository<Message, Long> {

    @Modifying(flushAutomatically = true)
    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "AUTO")
    }, forCounting = false) // forCounting - true применяется так же для постраничного получениях данных
    @Query("update Message m set m.message=?1 where m.id = ?2")
    void updateMessageById(String message, Long id);

    @EntityGraph(value = USER_ENTITY_GRAPH, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select m from Message m where m.id=?1")
    Optional<Message> findMessageByIdWithUserNamedEntityGraph(Long id);

    @EntityGraph(attributePaths = {Message_.USER}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select m from Message m where m.id=?1")
    Optional<Message> findMessageByIdWithUserInRepositoryEntityGraph(Long id);

    @Query("select m from Message m")
    List<MessageProjection> findAllMessageText();

    List<MessageOpenProjection> findAllByMessage(String message);

    List<MessageDTO> findAllMessageByIdIn(Collection<Long> ids);
}
