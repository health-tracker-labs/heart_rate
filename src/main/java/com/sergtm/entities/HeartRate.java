package com.sergtm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @GeneratedValue(strategy=GenerationType.AUTO, generator="HEART_RATE_SEQUENCE")
    @SequenceGenerator(name="HEART_RATE_SEQUENCE", sequenceName="HEART_RATE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    @JsonIgnore
    @XmlTransient
    private Long id;

    @Column(name = "UPPER_PRESSURE")
    private int upperPressure;

    @Column(name = "LOWER_PRESSURE")
    private int lowerPressure;

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

    public static HeartRate createHeartRate(int upperPressure, int lowerPressure, Date datetime, Person person){
        HeartRate hr = new HeartRate();
        hr.setUpperPressure(upperPressure);
        hr.setLowerPressure(lowerPressure);
        hr.setDate(datetime);
        hr.setPerson(person);
        return hr;
    }
}