package com.sergtm.health.tracker.persistence.entity;


import com.sergtm.entities.IEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Weather implements IEntity {
    @Id
    @SequenceGenerator(name = "WEATHER_SEQ", sequenceName = "WEATHER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WEATHER_SEQ")
    private Long id;

    @Column(name = "ICON_URL")
    private String iconUrl;

    private long temperature;

    private String description;

    @Column(name = "CONCRETE_DATE")
    private LocalDateTime date;
}
