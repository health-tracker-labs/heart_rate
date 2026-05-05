package com.sergtm.health.tracker.persistence.entity;


import com.sergtm.entities.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Weather implements IEntity {
    @Id
    @SequenceGenerator(name = "WEATHER_SEQ", sequenceName = "WEATHER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "WEATHER_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ICON_URL")
    private String iconUrl;

    @Column(name = "TEMPERATURE")
    private long temperature;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONCRETE_DATE")
    private LocalDateTime date;
}
