package com.sergtm.health.tracker.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.Objects;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "PATIENTS")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @SequenceGenerator(name = "PATIENTS_SEQ", sequenceName = "PATIENTS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PATIENTS_SEQ")
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = {PERSIST})
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) o;
        return id != null && id.equals(patient.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hashCode(id) : 0;
    }
}
