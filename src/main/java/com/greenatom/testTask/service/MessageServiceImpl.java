package com.greenatom.testTask.service;

import com.greenatom.testTask.model.Message;
import com.greenatom.testTask.model.TopicWithMessages;
import com.greenatom.testTask.repo.MessageRepo;
import com.greenatom.testTask.repo.TopicRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private MessageRepo messageRepo;

    public TopicWithMessages createFirstMessage(Message message) {

        boolean idExists = messageRepo.existsById(message.getId());
        if (idExists) {
            throw new EntityExistsException("Message with such id already exists.");
        }

        messageRepo.save(message);
        return topicRepo.findById(message.getTopic().getId()).orElseThrow(
                () -> new EntityNotFoundException("There is no topic with such id."));
    }

    public TopicWithMessages createMessage(String topicId, Message message) {

        TopicWithMessages topicInDb = topicRepo.findById(topicId).orElseThrow(
                () -> new EntityNotFoundException("There is no topic with such id."));

        boolean idExists = messageRepo.existsById(message.getId());
        if (idExists) {
            throw new EntityExistsException("Message with such id already exists.");
        }

        message.setTopic(topicInDb);
        messageRepo.save(message);

        return topicInDb;
    }

    public TopicWithMessages updateMessage(String topicId, Message message) {

        try {
            UUID.fromString(topicId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID is invalid.");
        }

        TopicWithMessages topicInDb = topicRepo.findById(topicId).orElseThrow(
                () -> new EntityNotFoundException("There is no topic with such id."));

        Message messageInDb = messageRepo.findById(message.getId()).orElseThrow(
                () -> new EntityNotFoundException("There is no message with such id."));

        messageInDb.setText(message.getText());
        messageInDb.setAuthor(message.getAuthor());
        messageInDb.setCreated(message.getCreated());

        messageRepo.save(message);
        return topicInDb;
    }

    public List<Message> getMessagesByTopicId(String topicId, int pageNo, int pageSize) {
        return messageRepo.findByTopicId(topicId, PageRequest.of(pageNo, pageSize)).getContent();
    }

    public void deleteMessage(String messageId) {
        if (!messageRepo.existsById(messageId)) {
            throw new EntityNotFoundException("There is no topic with such id.");
        }
        messageRepo.deleteById(messageId);
    }
}
