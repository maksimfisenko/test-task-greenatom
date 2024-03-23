package com.greenatom.testTask.controller;

import com.greenatom.testTask.model.Message;
import com.greenatom.testTask.model.NewTopic;
import com.greenatom.testTask.model.Topic;
import com.greenatom.testTask.model.TopicWithMessages;
import com.greenatom.testTask.service.MessageServiceImpl;
import com.greenatom.testTask.service.TopicServiceImpl;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    TopicServiceImpl topicService;

    @Autowired
    MessageServiceImpl messageService;

    // Create a new topic
    @PostMapping("/topic")
    public ResponseEntity<TopicWithMessages> createTopic(@RequestBody NewTopic newTopic) {

        Message message = newTopic.getMessage();
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        TopicWithMessages savedTopic = topicService.createTopic(
                new TopicWithMessages(newTopic.getTopicName(), message.getCreated(), messages));

        message.setTopic(savedTopic);

        try {
            return ResponseEntity.ok(messageService.createFirstMessage(message));
        } catch (EntityExistsException | EntityNotFoundException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    // Update existing topic
    @PutMapping("/topic")
    public ResponseEntity<TopicWithMessages> updateTopic(@RequestBody Topic topic) {
        try {
            return ResponseEntity.ok(topicService.updateTopic(topic));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all topics (with pagination)
    @GetMapping("/topic")
    public ResponseEntity<List<Topic>> getAllTopics(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return ResponseEntity.ok(topicService.getAllTopics(pageNo, pageSize));
    }

    // Get all messages of a topic (with pagination)
    @GetMapping("/topic/messages/{topicId}")
    public ResponseEntity<List<Message>> getTopicMessages(
            @PathVariable String topicId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        try {
            return ResponseEntity.ok(messageService.getMessagesByTopicId(topicId, pageNo, pageSize));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get a topic by id
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<TopicWithMessages> getTopicById(@PathVariable String topicId) {
        try {
            return ResponseEntity.ok(topicService.getTopicById(topicId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Create a message
    @PostMapping("/topic/{topicId}/message")
    public ResponseEntity<TopicWithMessages> createMessage(@PathVariable String topicId,
                                                           @RequestBody Message message) {
        try {
            return ResponseEntity.ok(messageService.createMessage(topicId, message));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update an existing message
    @PutMapping("/topic/{topicId}/message")
    public ResponseEntity<TopicWithMessages> updateMessage(@PathVariable String topicId,
                                                           @RequestBody Message message) {
        try {
            return ResponseEntity.ok(messageService.updateMessage(topicId, message));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    // Delete an existing message
    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}