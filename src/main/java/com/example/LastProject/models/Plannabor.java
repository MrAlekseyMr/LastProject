package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "plannabor")
public class Plannabor {
    @Id
    @Column(name = "idplannabor", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "yearnabor", nullable = false)
    private Integer yearnabor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "specialnostid", nullable = false)
    private Specialnosti specialnostid;

    @NotNull
    @Column(name = "kolvocheclovek", nullable = false)
    private Integer kolvocheclovek;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYearnabor() {
        return yearnabor;
    }

    public void setYearnabor(Integer yearnabor) {
        this.yearnabor = yearnabor;
    }

    public Specialnosti getSpecialnostid() {
        return specialnostid;
    }

    public void setSpecialnostid(Specialnosti specialnostid) {
        this.specialnostid = specialnostid;
    }

    public Integer getKolvocheclovek() {
        return kolvocheclovek;
    }

    public void setKolvocheclovek(Integer kolvocheclovek) {
        this.kolvocheclovek = kolvocheclovek;
    }

}