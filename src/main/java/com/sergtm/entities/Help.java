package com.sergtm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(schema = "HEART_RATE", name = "HELP")
public class Help implements IEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="HELP_SEQUENCE")
    @SequenceGenerator(name="HELP_SEQUENCE", sequenceName="HELP_SEQ")
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @OneToOne
    @JoinColumn(name="TOPIC_ID")
    private Topic topic;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
