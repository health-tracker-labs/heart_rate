package com.sergtm.entities;

import com.sergtm.health.tracker.persistence.entity.Person;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "heart_rate_with_atm_pressure")
@Getter
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
}
