package com.sergtm.health.tracker.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;

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
