package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "raioni")
public class Raioni {
    @Id
    @Column(name = "idraion", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "okrugid", nullable = false)
    private Okrug okrugid;

    @Size(max = 150)
    @NotNull
    @Size(message = "Укажите минимум 3 символов, Максимум 150",min = 3,max=150)
    @NotBlank
    @Column(name = "nameraion", nullable = false, length = 150)
    private String nameraion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Okrug getOkrugid() {
        return okrugid;
    }

    public void setOkrugid(Okrug okrugid) {
        this.okrugid = okrugid;
    }

    public String getNameraion() {
        return nameraion;
    }

    public void setNameraion(String nameraion) {
        this.nameraion = nameraion;
    }

}