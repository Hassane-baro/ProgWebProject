package com.progweb.Progweb.Models;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Votes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVote")
    private  Integer idVote;

    @Column(name = "user_fk")
    private Integer user_fk;

    @Column(name = "sondage_fk")
    private Integer sondage_fk;

    @Column
    private boolean reponse;


    public Votes(){

    }

    public Votes(Integer user_fk, Integer sondage_fk, boolean reponse) {
        this.user_fk = user_fk;
        this.sondage_fk = sondage_fk;
        this.reponse = reponse;

    }

    public Integer getIdVote() {
        return idVote;
    }

    public void setIdVote(Integer idVote) {
        this.idVote = idVote;
    }

    public Integer getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(Integer user_fk) {
        this.user_fk = user_fk;
    }

    public Integer getSondage_fk() {
        return sondage_fk;
    }

    public void setSondage_fk(Integer sondage_fk) {
        this.sondage_fk = sondage_fk;
    }

    public boolean isReponse() {
        return reponse;
    }

    public void setReponse(boolean reponse) {
        this.reponse = reponse;
    }



}
