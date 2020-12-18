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

    @Column(name = "user_fk")
    private Integer user_fk;

    public Token() {

    }

    public Token(String token, Integer user_fk) {
        this.token = token;
        this.user_fk = user_fk;
    }

    public Integer getIdToken() {
        return idToken;
    }

    public void setIdToken(Integer idToken) {
        this.idToken = idToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(Integer user_fk) {
        this.user_fk = user_fk;
    }


}
