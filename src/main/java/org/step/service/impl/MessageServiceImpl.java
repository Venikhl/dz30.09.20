package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.step.entity.Message;
import org.step.repository.MessageRepository;
import org.step.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAllByIdWithSorting() {
        final Sort sort = Sort.by("id");

        return messageRepository.findAllWithSortingById(sort);
    }
}
