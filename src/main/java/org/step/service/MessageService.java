package org.step.service;

import org.step.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> findAllByIdWithSorting();
}
