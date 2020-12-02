package com.progweb.Progweb.Models;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "idToken")
    private Integer idToken;

    @Column(name="token", length=100, nullable=false, unique=false)
    private String token;
}
