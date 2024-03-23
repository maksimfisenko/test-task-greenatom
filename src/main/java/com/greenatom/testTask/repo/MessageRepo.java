package com.greenatom.testTask.repo;

import com.greenatom.testTask.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<Message, String> {
    Page<Message> findByTopicId(String topicId, PageRequest pageRequest);
}