package com.progweb.Progweb.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoteKey implements Serializable {
    @Column(name = "id_User")
    private Integer idUser;

    @Column(name = "id_Sondage")
    private Integer idSondage;
}
