package com.sergtm.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.sergtm.OccasionLevel;

@Entity
public class Occasion implements IEntity {
	@Id
	@SequenceGenerator(name = "OCCASION_SEQ", sequenceName = "OCCASION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "OCCASION_SEQ")
	@Column(name = "ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "DISEASE_ID")
	private Disease disease;

	@OneToOne
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	@Column(name = "OCCASION_LEVEL")
	@Enumerated(EnumType.STRING)
	private OccasionLevel occasionLevel;

	@Column(name = "WITH_CONVULSION")
	@Type(type = "yes_no")
	private boolean convulsion;

	@Column(name = "OCCASION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date occasionDate;

	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Disease getDisease() {
		return disease;
	}
	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	public OccasionLevel getOccasionLevel() {
		return occasionLevel;
	}
	public void setOccasionLevel(OccasionLevel occasionLevel) {
		this.occasionLevel = occasionLevel;
	}

	public boolean isConvulsion() {
		return convulsion;
	}
	public void setConvulsion(boolean convulsion) {
		this.convulsion = convulsion;
	}

	public Date getOccasionDate() {
		return occasionDate;
	}
	public void setOccasionDate(Date occasionDate) {
		this.occasionDate = occasionDate;
	}
}
