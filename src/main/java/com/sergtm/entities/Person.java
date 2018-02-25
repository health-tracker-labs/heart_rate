package com.sergtm.entities;

import javax.persistence.*;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * Created by Sergey on 16.07.2017.
 */
@Entity
@Table(schema = "HEART_RATE", name = "PERSON")
public class Person implements IEntity{
    @Id
    @SequenceGenerator(name = "PERSON_SEQ", sequenceName = "PERSON_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PERSON_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "SECOND_NAME")
    private String secondName;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "CITY")
    private String city;
    @Column(name = "BIRTHDATE")
    private LocalDateTime birthdate;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;
    @Column(name = "EMAIL")
    private String email;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getName() {
        final String middleName = getMiddleName();
        return String.format("%s%s, %s", getSecondName(), 
                StringUtils.isEmpty(middleName) ? "" : " " + middleName, getFirstName());
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Person createPerson(String firstName, String secondName){
        Person person = new Person();
        person.setFirstName(firstName);
        person.setSecondName(secondName);
        return person;
    }
}
