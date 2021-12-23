package com.sergtm.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
