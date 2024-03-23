package com.greenatom.testTask.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "topic")
public class TopicWithMessages{

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String created;

    @Column(nullable = false)
    @OneToMany(mappedBy = "topic", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Message> messages;

    public TopicWithMessages(String name, String created) {
        this.name = name;
        this.created = created;
    }

    public TopicWithMessages(String name, String created, List<Message> messages) {
        this.name = name;
        this.created = created;
        this.messages = messages;
    }
}