package com.sergtm.health.tracker.persistence.entity;

import com.sergtm.entities.IEntity;
import com.sergtm.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table( name = "USERS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements IEntity {
    @Id
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USERS_SEQ")
    private Long id;

    private String username;
    private String password;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Type(type= "org.hibernate.type.NumericBooleanType")
    private Boolean state;

    @JoinTable(
        name = "USER_ROLE",
        joinColumns = @JoinColumn(name = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    @OneToMany(fetch = FetchType.EAGER, cascade = {PERSIST})
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}