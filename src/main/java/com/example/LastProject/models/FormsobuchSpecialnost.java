package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "formsobuch_specialnost")
public class FormsobuchSpecialnost {
    @Id
    @Column(name = "idformsobuchspecialnost", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "specialnostid", nullable = false)
    private Specialnosti specialnostid;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "abiturientid", nullable = false)
    private Abiturient abiturientid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "formaobuchid", nullable = false)
    private Formsobuch formaobuchid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Specialnosti getSpecialnostid() {
        return specialnostid;
    }

    public void setSpecialnostid(Specialnosti specialnostid) {
        this.specialnostid = specialnostid;
    }

    public Abiturient getAbiturientid() {
        return abiturientid;
    }

    public void setAbiturientid(Abiturient abiturientid) {
        this.abiturientid = abiturientid;
    }

    public Formsobuch getFormaobuchid() {
        return formaobuchid;
    }

    public void setFormaobuchid(Formsobuch formaobuchid) {
        this.formaobuchid = formaobuchid;
    }

}