package com.sergtm.entities;

import com.sergtm.health.tracker.persistence.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weight implements IEntity {
    @Id
    @SequenceGenerator(name = "WEIGHT_SEQ", sequenceName = "WEIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "WEIGHT_SEQ")
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    private BigDecimal weight;

    @Column(name = "CONCRETE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
