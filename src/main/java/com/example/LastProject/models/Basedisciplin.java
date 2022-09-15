package com.example.LastProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "basedisciplins")
public class Basedisciplin {
    @Id
    @Column(name = "iddisciplins", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "namedisciplins", nullable = false, length = 100)
    @NotBlank
    @Size(message = "Укажите минимум 3 символов, Максимум 100",min = 3,max=100)
    private String namedisciplins;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamedisciplins() {
        return namedisciplins;
    }

    public void setNamedisciplins(String namedisciplins) {
        this.namedisciplins = namedisciplins;
    }

}