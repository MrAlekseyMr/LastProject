package com.example.LastProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "grazhdanstav")
public class Grazhdanstav {
    @Id
    @Column(name = "idgrazhdanstov", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(message = "Укажите минимум 2 символов, Максимум 100",min = 2,max=100)
    @Column(name = "namegrazhdanstvo", nullable = false, length = 100)
    private String namegrazhdanstvo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamegrazhdanstvo() {
        return namegrazhdanstvo;
    }

    public void setNamegrazhdanstvo(String namegrazhdanstvo) {
        this.namegrazhdanstvo = namegrazhdanstvo;
    }

}