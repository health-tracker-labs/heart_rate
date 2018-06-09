package com.sergtm.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PRESSURE")
public class Pressure implements IEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PRESSURE_SEQ", sequenceName = "PRESSURE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PRESSURE_SEQ")
    private Long id;

    @Column(name = "CONCRETE_DATE")
    private LocalDate date;

    @Column(name = "PRESSURE")
    private double pressure;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
