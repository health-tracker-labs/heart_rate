package com.sergtm.health.tracker.persistence.entity;

import com.sergtm.entities.IEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
