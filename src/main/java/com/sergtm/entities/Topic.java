package com.sergtm.entities;

import javax.persistence.*;


@Entity
@Table(name = "TOPIC")
public class Topic implements IEntity {
    @Id
    @SequenceGenerator(name = "TOPIC_SEQ", sequenceName = "TOPIC_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TOPIC_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

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
}
