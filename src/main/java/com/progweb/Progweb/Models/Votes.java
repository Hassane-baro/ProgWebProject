package com.progweb.Progweb.Models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "votes")
public class Votes  {

    @EmbeddedId
    private VoteKey idVote;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_User")
    Users user;

    @ManyToOne
    @MapsId("idSondage")
    @JoinColumn(name = "id_Sondage")
    Sondages sondage;

    @Column(name = "reponse")
    private boolean reponse;

    @Column(name = "nbVote")
    private int nbVote;

    public boolean isReponse() {
        return reponse;
    }

    public void setReponse(boolean reponse) {
        this.reponse = reponse;
    }

    public int getNbVote() {
        return nbVote;
    }

    public void setNbVote(int nbVote) {
        this.nbVote = nbVote;
    }
}
