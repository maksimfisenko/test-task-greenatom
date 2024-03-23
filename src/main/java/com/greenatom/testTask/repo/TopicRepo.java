package com.greenatom.testTask.repo;

import com.greenatom.testTask.model.TopicWithMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<TopicWithMessages, String> {
}
