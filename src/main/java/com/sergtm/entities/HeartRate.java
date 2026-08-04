package com.sergtm.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sergtm.health.tracker.persistence.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Sergey on 16.07.2017.
 */
@Entity
@Getter
@Setter
@Table(name = "HEART_RATE")
@XmlRootElement
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartRate implements IEntity{
    @Id
    @SequenceGenerator(name = "HEART_RATE_SEQ", sequenceName = "HEART_RATE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "HEART_RATE_SEQ")
    @Column(name = "ID")
    @XmlTransient
    private Long id;

    @Column(name = "UPPER_PRESSURE")
    private int upperPressure;

    @Column(name = "LOWER_PRESSURE")
    private int lowerPressure;

    @Column(name = "BPM")
    private int beatsPerMinute;

    @Column(name = "CONCREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    //@OneToOne(targetEntity = Person.class)
    @ManyToOne
    @JoinColumn(name="PERSON_ID")
    private Person person;

    public static HeartRate createHeartRate(
            int upperPressure,
            int lowerPressure,
            int beatsPerMinute,
            Date datetime,
            Person person
    ) {
        HeartRate hr = new HeartRate();
        hr.setUpperPressure(upperPressure);
        hr.setLowerPressure(lowerPressure);
        hr.setBeatsPerMinute(beatsPerMinute);
        hr.setDate(datetime);
        hr.setPerson(person);
        return hr;
    }
}