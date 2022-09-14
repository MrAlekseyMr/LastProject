package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "iduser", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank(message = "Заполните поле")
    @Size(message = "Укажите минимум 2 символа, Максимум 100",min = 2,max=100)
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @NotNull
    @NotBlank(message = "Заполните поле")
    @Size(message = "Укажите минимум 8 символов, Максимум 100",min = 8,max=100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "specialnostid", nullable = false)
    private Specialnosti specialnostid;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Specialnosti getSpecialnostid() {
        return specialnostid;
    }

    public void setSpecialnostid(Specialnosti specialnostid) {
        this.specialnostid = specialnostid;
    }

    public User() {
    }

    public User(String username, String password, Specialnosti specialnostid) {
        this.username = username;
        this.password = password;
        this.specialnostid = specialnostid;
    }
}