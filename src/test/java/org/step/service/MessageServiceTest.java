package org.step.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.step.configuration.DatabaseConfiguration;
import org.step.entity.Message;
import org.step.entity.projection.MessageOpenProjection;
import org.step.entity.projection.MessageProjection;
import org.step.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
public class MessageServiceTest {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    PlatformTransactionManager platformTransactionManager;
    TransactionTemplate transactionTemplate;

    @Before
    public void setup() {
        final Random random = new Random();

        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);

        transactionTemplate.execute(status -> {
            Stream
                    .generate(
                            () -> Message
                                    .builder()
                                    .message("message " + random.nextInt(10))
                                    .date(LocalDateTime.now().toString())
                                    .build()
                    )
                    .limit(100)
                    .forEach(messageRepository::save);

            return status;
        });
    }

    @After
    public void clean() {
        transactionTemplate.execute(status -> {
            messageRepository.deleteAll();

            return status;
        });
    }

    @Test
    public void findAllProjection() {
        List<MessageProjection> allMessageText = messageRepository.findAllMessageText();

        allMessageText.forEach(mp -> System.out.println(mp.getMessage()));
    }

    @Test
    public void findAllByMessage() {
        List<MessageOpenProjection> allByMessage = messageRepository.findAllByMessage("message 3");

        allByMessage.forEach(mp -> System.out.println(mp.getMessageWithIdDescription()));
    }

    @Test
    public void exampleMessageTest() {
        final String messageField = "age 3";
        final String date = LocalDateTime.now().toString();

        Message message = Message
                .builder()
                .message(messageField)
                .date(date)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAny() // Должно соответствовать всем проперти
                .withMatcher("message", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                // message - проперти в классе Message
                .withMatcher("date", ExampleMatcher.GenericPropertyMatchers.exact())
                // date - проперти в классе Message
                .withIgnoreNullValues() // Игнорируем null поля
                .withIgnorePaths("id"); // Игнорируем проперти id
//                .withIgnoreCase();

        // 1 аргумент - экземпляр сущности для поиска (message)
        // 2 аргумент - паттерн для сравнения этой сущности с записями в БД (matcher)
        Example<Message> messageExample = Example.of(message, matcher);

        List<Message> all = messageRepository.findAll(messageExample);

        all.forEach(m -> System.out.println(m.getMessage()));
    }

}
