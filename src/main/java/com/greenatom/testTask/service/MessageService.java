package com.greenatom.testTask.service;

import com.greenatom.testTask.model.Message;
import com.greenatom.testTask.model.TopicWithMessages;
import java.util.List;

public interface MessageService {
    TopicWithMessages createFirstMessage(Message message);
    TopicWithMessages createMessage(String topicId, Message message);
    TopicWithMessages updateMessage(String topicId, Message message);
    List<Message> getMessagesByTopicId(String topicId, int pageNo, int pageSize);
    void deleteMessage(String messageId);
}
