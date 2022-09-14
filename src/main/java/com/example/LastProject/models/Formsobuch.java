package com.example.LastProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "formsobuch")
public class Formsobuch {
    @Id
    @Column(name = "idformobuch", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nameformobuch", nullable = false, length = 100)
    private String nameformobuch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameformobuch() {
        return nameformobuch;
    }

    public void setNameformobuch(String nameformobuch) {
        this.nameformobuch = nameformobuch;
    }

}