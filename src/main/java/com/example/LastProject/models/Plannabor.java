package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "plannabor")
public class Plannabor {
    @Id
    @Column(name = "idplannabor", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "specialnostid", nullable = false)
    private Specialnosti specialnostid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "formsobucid", nullable = false)
    private Formsobuch formsobucid;

    @NotNull
    @Min(value = 1, message = "Минимум набор 1 человек")
    @Max(value = 5000,message = "Очень много людей, максимум бюджета хватит на 5000 чел.")
    @Column(name = "kolvocheclovek", nullable = false)
    private Integer kolvocheclovek;

    public Formsobuch getFormsobucid() {
        return formsobucid;
    }

    public void setFormsobucid(Formsobuch formsobucid) {
        this.formsobucid = formsobucid;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

/*    public Integer getYearnabor() {
        return yearnabor;
    }

    public void setYearnabor(Integer yearnabor) {
        this.yearnabor = yearnabor;
    }*/

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