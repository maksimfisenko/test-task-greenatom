package com.greenatom.testTask.controller;

import com.greenatom.testTask.model.Message;
import com.greenatom.testTask.model.Topic;
import com.greenatom.testTask.model.TopicWithMessages;
import com.greenatom.testTask.service.MessageServiceImpl;
import com.greenatom.testTask.service.TopicServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    TopicServiceImpl topicService;

    @Autowired
    MessageServiceImpl messageService;

    // Update an existing topic
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

    // Delete an existing topic
    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable String topicId) {
        try {
            topicService.deleteTopic(topicId);
            return ResponseEntity.noContent().build();
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
