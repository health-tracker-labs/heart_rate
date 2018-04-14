package com.sergtm.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "HEART_RATE", name = "USER")
public class User implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    /*@Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;*/

    @JoinTable(
        name = "USER_ROLE",
        joinColumns = @JoinColumn(name = "ROLE_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @OneToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> role) {
        this.roles = roles;
    }
}