package com.example.pesapalloxr;

public class Lyojat {

    Integer numero;
    Integer id;
    String nimi;
    Integer joukkueID;
    String joukkue;

    public Lyojat(Integer numero, Integer id, String nimi, Integer joukkueID, String joukkue) {
        this.numero = numero;
        this.id = id;
        this.nimi = nimi;
        this.joukkueID = joukkueID;
        this.joukkue = joukkue;
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
