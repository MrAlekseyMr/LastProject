package com.example.LastProject.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "abiturients")
public class Abiturient {
    @Id
    @Column(name = "idabiturient", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Error")
    @Size(message = "Укажите минимум 3 символов, Максимум 100",min = 3,max=100)
    @Column(name = "familia", nullable = false, length = 100)
    private String familia;

    @NotBlank(message = "Error")
    @Size(message = "Укажите минимум 3 символов, Максимум 100",min = 3,max=100)
    @Column(name = "ima", nullable = false, length = 100)
    private String ima;

    @Size(max = 100)
    @Column(name = "otchestvo", length = 100)
    private String otchestvo;

    @NotBlank(message = "Error")
    @Size(min=14,max = 14,message = "Введите корректный СНИЛС")
    @Column(name = "snils", nullable = false)
    private String snils;

    @NotNull(message = "Error")
    @Size(min=4,max = 4,message = "Введите корректную серию паспорта")
    @Column(name = "seriapasporta", nullable = false)
    private String seriapasporta;

    @NotNull(message = "Заполните")
    @Size(min=6,max = 6,message = "Введите корректный номер паспорта")
    @Column(name = "nomerpasporta", nullable = false)
    private String nomerpasporta;

    @NotNull(message = "Заполните")
    @Column(name = "dateofbirth", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Укажите дату рождения корректную")
    private LocalDate dateofbirth;

    @NotNull(message = "Заполните")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "raionid", nullable = false)
    private Raioni raionid;

    @NotNull(message = "Заполните")
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

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getSeriapasporta() {
        return seriapasporta;
    }

    public void setSeriapasporta(String seriapasporta) {
        this.seriapasporta = seriapasporta;
    }

    public String getNomerpasporta() {
        return nomerpasporta;
    }

    public void setNomerpasporta(String nomerpasporta) {
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