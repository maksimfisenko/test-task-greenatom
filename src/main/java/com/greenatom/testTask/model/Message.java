package com.greenatom.testTask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String created;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonIgnore
    private TopicWithMessages topic;
}
