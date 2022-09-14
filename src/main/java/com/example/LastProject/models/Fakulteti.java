package com.example.LastProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "fakulteti")
public class Fakulteti {
    @Id
    @Column(name = "idfakultet", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "namefakultet", nullable = false, length = 150)
    private String namefakultet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamefakultet() {
        return namefakultet;
    }

    public void setNamefakultet(String namefakultet) {
        this.namefakultet = namefakultet;
    }

}