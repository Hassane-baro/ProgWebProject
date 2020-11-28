package com.progweb.Progweb.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String adresse;
    private String dateNaiss;
    private String numMobile;

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
