package com.sergtm.entities;

import com.sergtm.OccasionLevel;
import com.sergtm.health.tracker.persistence.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.Type;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.YesNoConverter;

import java.util.Date;

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
	@Convert(converter = YesNoConverter.class)
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
