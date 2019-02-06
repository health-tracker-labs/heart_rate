package com.sergtm.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "STAFF_MEMBER")
public class StaffMember implements IEntity {
    @Id
    @SequenceGenerator(name = "STAFF_MEMBER_SEQ", sequenceName = "STAFF_MEMBER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "STAFF_MEMBER_SEQ")
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @JoinTable(
            name = "PATIENT_DOCTOR",
            joinColumns = @JoinColumn(name = "DOCTOR_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    )
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Person> patients;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Person> getPatients() {
        return patients;
    }

    public void setPatients(Set<Person> patients) {
        this.patients = patients;
    }
}
