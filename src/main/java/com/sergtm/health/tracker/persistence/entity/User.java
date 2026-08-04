package com.sergtm.health.tracker.persistence.entity;

import com.sergtm.entities.IEntity;
import com.sergtm.entities.Role;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.NumericBooleanConverter;

import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;

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

    @Convert(converter = NumericBooleanConverter.class)
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