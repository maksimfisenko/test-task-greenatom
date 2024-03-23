package com.greenatom.testTask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTopic {
    private String topicName;
    private Message message;
}
