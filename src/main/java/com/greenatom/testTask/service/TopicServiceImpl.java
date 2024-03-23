package com.greenatom.testTask.service;

import com.greenatom.testTask.model.Topic;
import com.greenatom.testTask.model.TopicWithMessages;
import com.greenatom.testTask.repo.TopicRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepo topicRepo;

    public TopicWithMessages createTopic(TopicWithMessages topicWithMessages) {
        return topicRepo.save(topicWithMessages);
    }

    public TopicWithMessages updateTopic(Topic topic) {

        try {
            UUID.fromString(topic.getId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID is invalid.");
        }

        TopicWithMessages topicFromDb = topicRepo.findById(topic.getId()).orElseThrow(
                () -> new EntityNotFoundException("There is no topic with such id."));

        topicFromDb.setName(topic.getName());
        topicFromDb.setCreated(topic.getCreated());
        return topicRepo.save(topicFromDb);
    }

    public List<Topic> getAllTopics(int pageNo, int pageSize) {

        Page<TopicWithMessages> topicsWithMessages = topicRepo.findAll(PageRequest.of(pageNo, pageSize));
        List<Topic> topics = new ArrayList<>();

        for (TopicWithMessages topicWithMessages : topicsWithMessages) {
            topics.add(new Topic(topicWithMessages.getId(), topicWithMessages.getName(),
                    topicWithMessages.getCreated()));
        }

        return topics;
    }

    public TopicWithMessages getTopicById(String topicId) {
        return topicRepo.findById(topicId).orElseThrow(
                () -> new EntityNotFoundException("There is no topic with such id."));
    }

    public void deleteTopic(String topicId) {
        TopicWithMessages topicFromDb = topicRepo.findById(topicId).orElseThrow(
                () -> new EntityNotFoundException("There is no topic with such id."));
        topicRepo.delete(topicFromDb);
    }

}

