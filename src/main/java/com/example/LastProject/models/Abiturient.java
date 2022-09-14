package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "abiturients")
public class Abiturient {
    @Id
    @Column(name = "idabiturient", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "familia", nullable = false, length = 100)
    private String familia;

    @Size(max = 100)
    @NotNull
    @Column(name = "ima", nullable = false, length = 100)
    private String ima;

    @Size(max = 100)
    @Column(name = "otchestvo", length = 100)
    private String otchestvo;

    @NotNull
    @Column(name = "snils", nullable = false)
    private Integer snils;

    @NotNull
    @Column(name = "seriapasporta", nullable = false)
    private Integer seriapasporta;

    @NotNull
    @Column(name = "nomerpasporta", nullable = false)
    private Integer nomerpasporta;

    @NotNull
    @Column(name = "dateofbirth", nullable = false)
    private LocalDate dateofbirth;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "raionid", nullable = false)
    private Raioni raionid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "grazhdanstvoid", nullable = false)
    private Grazhdanstav grazhdanstvoid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getIma() {
        return ima;
    }

    public void setIma(String ima) {
        this.ima = ima;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public Integer getSnils() {
        return snils;
    }

    public void setSnils(Integer snils) {
        this.snils = snils;
    }

    public Integer getSeriapasporta() {
        return seriapasporta;
    }

    public void setSeriapasporta(Integer seriapasporta) {
        this.seriapasporta = seriapasporta;
    }

    public Integer getNomerpasporta() {
        return nomerpasporta;
    }

    public void setNomerpasporta(Integer nomerpasporta) {
        this.nomerpasporta = nomerpasporta;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public Raioni getRaionid() {
        return raionid;
    }

    public void setRaionid(Raioni raionid) {
        this.raionid = raionid;
    }

    public Grazhdanstav getGrazhdanstvoid() {
        return grazhdanstvoid;
    }

    public void setGrazhdanstvoid(Grazhdanstav grazhdanstvoid) {
        this.grazhdanstvoid = grazhdanstvoid;
    }

}