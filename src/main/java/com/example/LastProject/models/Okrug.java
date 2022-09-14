package com.example.LastProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "okrugs")
public class Okrug {
    @Id
    @Column(name = "idokrug", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @NotBlank
    @Column(name = "nameokrug", nullable = false, length = 50)
    @Size(message = "Укажите минимум 3 символов, Максимум 50",min = 3,max=50)
    private String nameokrug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameokrug() {
        return nameokrug;
    }

    public void setNameokrug(String nameokrug) {
        this.nameokrug = nameokrug;
    }

}