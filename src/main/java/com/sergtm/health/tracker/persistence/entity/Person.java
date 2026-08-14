package com.sergtm.health.tracker.persistence.entity;

import com.sergtm.entities.IEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Sergey on 16.07.2017.
 */
@Entity
@Table(name = "PERSONS")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person implements IEntity {
    @Id
    @SequenceGenerator(name = "PERSONS_SEQ", sequenceName = "PERSONS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PERSONS_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "SECOND_NAME")
    private String secondName;

    private String country;
    private String city;
    private LocalDateTime birthdate;
    private String phone;

    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    private String email;
}
