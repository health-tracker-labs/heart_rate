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

import java.math.BigDecimal;
import java.util.Date;

@Entity
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

	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
