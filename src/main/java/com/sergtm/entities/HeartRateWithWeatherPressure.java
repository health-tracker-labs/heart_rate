package com.sergtm.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "heart_rate_with_atm_pressure")
public class HeartRateWithWeatherPressure {
    @Id
    private String id;

    private Long upperPressure;

    private Long lowerPressure;

    private Long beatsPerMinute;

    @Column(name = "concrete_date")
    private LocalDate date;

    @Column(columnDefinition = "BINARY_DOUBLE")
    private Double weatherPressure;

    @ManyToOne
    @JoinColumn(name="PERSON_ID")
    private Person person;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUpperPressure() {
        return upperPressure;
    }

    public void setUpperPressure(Long upperPressure) {
        this.upperPressure = upperPressure;
    }

    public Long getLowerPressure() {
        return lowerPressure;
    }

    public void setLowerPressure(Long lowerPressure) {
        this.lowerPressure = lowerPressure;
    }

    public Long getBeatsPerMinute() {
        return beatsPerMinute;
    }

    public void setBeatsPerMinute(Long beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getWeatherPressure() {
        return weatherPressure;
    }

    public void setWeatherPressure(Double weatherPressure) {
        this.weatherPressure = weatherPressure;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
