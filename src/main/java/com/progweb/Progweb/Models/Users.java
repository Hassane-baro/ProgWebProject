package com.progweb.Progweb.Models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="nom", length=100, nullable=false, unique=false)
    private String nom;

    @Column(name="prenom", length=100, nullable=false, unique=false)
    private String prenom;

    @Column(name="email", length=100, nullable=false, unique=false)
    private String email;

    @Column(name="password", length=100, nullable=false, unique=false)
    private String password;

    @Column(name="adresse", length=100, nullable=false, unique=false)
    private String adresse;

    @Column(name="dateNaiss", length=100, nullable=false, unique=false)
    private String dateNaiss;

    @Column(name="numMobile", length=100, nullable=false, unique=false)
    private String numMobile;

    @OneToMany(targetEntity = Sondages.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk", referencedColumnName = "id")
    private List<Sondages> sondages;

    public Users(){

    }
    public Users(String nom, String prenom, String email, String password, String adresse, String dateNaiss, String numMobile) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.dateNaiss = dateNaiss;
        this.numMobile = numMobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getAdresse() {
        return adresse;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }
    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setNumMobile(String numMobile) {
        this.numMobile = numMobile;
    }
    public String getNumMobile() {
        return numMobile;
    }



}
