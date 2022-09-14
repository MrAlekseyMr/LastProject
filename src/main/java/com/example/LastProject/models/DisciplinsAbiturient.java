package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "disciplins_abiturient")
public class DisciplinsAbiturient {
    @Id
    @Column(name = "iddisciplinsabiturient", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "disciplinid", nullable = false)
    private Basedisciplin disciplinid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "abiturientid", nullable = false)
    private Abiturient abiturientid;

    @NotNull
    @Column(name = "mark", nullable = false)
    private Integer mark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Basedisciplin getDisciplinid() {
        return disciplinid;
    }

    public void setDisciplinid(Basedisciplin disciplinid) {
        this.disciplinid = disciplinid;
    }

    public Abiturient getAbiturientid() {
        return abiturientid;
    }

    public void setAbiturientid(Abiturient abiturientid) {
        this.abiturientid = abiturientid;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

}