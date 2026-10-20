package com.example.pesapalloxr.DataClasses;

public class Pelaajat {

    Integer id;
    Integer numero;
    String nimi;
    Integer SeuraID;
    Integer joukkueID;
    String joukkue;

    public Pelaajat(Integer id, Integer numero, String nimi,
                    Integer seuraID, Integer joukkueID, String joukkue) {
        this.id = id;
        this.numero = numero;
        this.nimi = nimi;
        SeuraID = seuraID;
        this.joukkueID = joukkueID;
        this.joukkue = joukkue;
    }

    public Integer getSeuraID() {
        return SeuraID;
    }

    public void setSeuraID(Integer seuraID) {
        SeuraID = seuraID;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return nimi;
    }

    public Integer getJoukkueID() {
        return joukkueID;
    }

    public void setJoukkueID(Integer joukkueID) {
        this.joukkueID = joukkueID;
    }

    public String getJoukkue() {
        return joukkue;
    }

    public void setJoukkue(String joukkue) {
        this.joukkue = joukkue;
    }

}
