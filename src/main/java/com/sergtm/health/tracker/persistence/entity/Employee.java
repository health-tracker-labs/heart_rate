package com.sergtm.health.tracker.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "EMPLOYEES")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @SequenceGenerator(name = "EMPLOYEES_SEQ", sequenceName = "EMPLOYEES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "EMPLOYEES_SEQ")
    @Column(name = "ID")
    private Long id;

    @JoinTable(
            name = "EMPLOYEE_PATIENT",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PATIENT_ID", referencedColumnName = "ID")
    )
    @ManyToMany(cascade = {PERSIST})
    private Set<Patient> patients;

    @OneToOne(cascade = {PERSIST})
    @JoinColumn(name = "PERSON_ID")
    private Person person;
}
