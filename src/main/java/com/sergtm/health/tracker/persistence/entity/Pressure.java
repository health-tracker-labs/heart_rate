package com.sergtm.health.tracker.persistence.entity;

import com.sergtm.entities.IEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "PRESSURE")
@Getter
@Setter
public class Pressure implements IEntity {
    @Id
    @SequenceGenerator(name = "PRESSURE_SEQ", sequenceName = "PRESSURE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESSURE_SEQ")
    private Long id;

    @Column(name = "CONCRETE_DATE")
    private LocalDate date;

    @Column(name = "PRESSURE",
            columnDefinition = "BINARY_DOUBLE"
    )
    private double pressure;
}
