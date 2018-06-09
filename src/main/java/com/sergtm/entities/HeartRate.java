package com.sergtm.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Created by Sergey on 16.07.2017.
 */
@Entity
@Table(schema = "HEART_RATE", name = "HEART_RATE")
@XmlRootElement
public class HeartRate implements IEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @Temporal(TemporalType.DATE)
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