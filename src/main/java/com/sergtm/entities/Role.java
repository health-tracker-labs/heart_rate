package com.sergtm.entities;

import com.sergtm.entities.IEntity;

import javax.persistence.*;

@Entity
@Table(schema = "HEART_RATE", name = "ROLE")
public class Role implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
