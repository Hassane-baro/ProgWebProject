package com.progweb.Progweb.Models;

import javax.persistence.*;

@Entity
@Table(name = "sondages")
public class Sondages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSondage")
    private  Integer idSondage;

    @Column(name="libeller", length=100, nullable=false, unique=false)
    private String libeller;

    @Column(name="dateRDV", length=100, nullable=false, unique=false)
    private String dateRDV;

    @Column(name="lieuRDV", length=100, nullable=false, unique=false)
    private String lieuRDV;

    private Users users;


    public Sondages(){

    }

    public Sondages(String libeller, String dateRDV, String lieuRDV, Integer idUser) {
        this.libeller = libeller;
        this.dateRDV = dateRDV;
        this.lieuRDV = lieuRDV;
        this.users.setId(idUser);

    }

    public Integer getIdSondage() {
        return idSondage;
    }

    public void setIdSondage(Integer idSondage) {
        this.idSondage = idSondage;
    }

    public String getLibeller() {
        return libeller;
    }

    public void setLibeller(String libeller) {
        this.libeller = libeller;
    }

    public String getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(String dateRDV) {
        this.dateRDV = dateRDV;
    }

    public String getLieuRDV() {
        return lieuRDV;
    }

    public void setLieuRDV(String lieuRDV) {
        this.lieuRDV = lieuRDV;
    }

}
