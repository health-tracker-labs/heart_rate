package com.sergtm.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by Sergey on 16.07.2017.
 */
@Entity
@Table(name = "HEART_RATE")
@XmlRootElement
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

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUpperPressure() {
        return upperPressure;
    }

    public void setUpperPressure(int upperPressure) {
        this.upperPressure = upperPressure;
    }

    public int getLowerPressure() {
        return lowerPressure;
    }

    public void setLowerPressure(int lowerPressure) {
        this.lowerPressure = lowerPressure;
    }

    public int getBeatsPerMinute() {
        return beatsPerMinute;
    }

    public void setBeatsPerMinute(int beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public static HeartRate createHeartRate(int upperPressure, int lowerPressure, int beatsPerMinute, Date datetime, Person person){
        HeartRate hr = new HeartRate();
        hr.setUpperPressure(upperPressure);
        hr.setLowerPressure(lowerPressure);
        hr.setBeatsPerMinute(beatsPerMinute);
        hr.setDate(datetime);
        hr.setPerson(person);
        return hr;
    }
}