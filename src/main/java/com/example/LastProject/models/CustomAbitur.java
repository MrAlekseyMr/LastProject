package com.example.LastProject.models;



import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CustomAbitur {
    private Integer id;
    private String familia;
    private String ima;
    private String snils;
    private Double srball;
    private String specialnost;

    public String getSpecialnost() {
        return specialnost;
    }

    public void setSpecialnost(String specialnost) {
        this.specialnost = specialnost;
    }

    private List<DisciplinsAbiturient> list_discp;

    public Double getSrball() {
        return srball;
    }

    public void setSrball(Double srball) {
        this.srball = srball;
    }

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

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public List<DisciplinsAbiturient> getList_discp() {
        return list_discp;
    }

    public void setList_discp(List<DisciplinsAbiturient> list_discp) {
        this.list_discp = list_discp;
    }
}
