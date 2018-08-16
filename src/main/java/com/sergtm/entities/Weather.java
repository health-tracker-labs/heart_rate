package com.sergtm.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "WEATHER")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = Math.round(temperature);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
