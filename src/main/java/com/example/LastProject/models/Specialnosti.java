package com.example.LastProject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "specialnosti")
public class Specialnosti {
    @Id
    @Column(name = "idspecialnost", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "kodpospo", nullable = false, length = 40)
    private String kodpospo;

    @Size(max = 100)
    @NotNull
    @Column(name = "namespecialnost", nullable = false, length = 100)
    private String namespecialnost;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fakultetid", nullable = false)
    private Fakulteti fakultetid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodpospo() {
        return kodpospo;
    }

    public void setKodpospo(String kodpospo) {
        this.kodpospo = kodpospo;
    }

    public String getNamespecialnost() {
        return namespecialnost;
    }

    public void setNamespecialnost(String namespecialnost) {
        this.namespecialnost = namespecialnost;
    }

    public Fakulteti getFakultetid() {
        return fakultetid;
    }

    public void setFakultetid(Fakulteti fakultetid) {
        this.fakultetid = fakultetid;
    }

}