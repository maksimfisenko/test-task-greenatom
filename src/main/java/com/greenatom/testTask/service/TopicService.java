package com.greenatom.testTask.service;

import com.greenatom.testTask.model.Topic;
import com.greenatom.testTask.model.TopicWithMessages;
import java.util.List;

public interface TopicService {
    TopicWithMessages createTopic(TopicWithMessages topicWithMessages);
    TopicWithMessages updateTopic(Topic topic);
    List<Topic> getAllTopics(int pageNo, int pageSize);
    TopicWithMessages getTopicById(String topicId);
    void deleteTopic(String topicId);
}
