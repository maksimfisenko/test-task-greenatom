package com.greenatom.testTask.runners;

import com.github.javafaker.Faker;
import com.greenatom.testTask.model.Message;
import com.greenatom.testTask.model.TopicWithMessages;
import com.greenatom.testTask.service.MessageServiceImpl;
import com.greenatom.testTask.service.TopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private TopicServiceImpl topicService;
    @Autowired
    private MessageServiceImpl messageService;

    @Override
    public void run(ApplicationArguments args) {

        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {

            TopicWithMessages topic = new TopicWithMessages(
                    faker.harryPotter().location(), Instant.now().toString());
            topicService.createTopic(topic);

            for (int j = 0; j < 10; j++) {

                Message message = new Message(
                        faker.internet().uuid(), faker.harryPotter().spell(),
                        faker.harryPotter().character(), Instant.now().toString(), topic);
                messageService.createFirstMessage(message);
            }
        }
    }
}
