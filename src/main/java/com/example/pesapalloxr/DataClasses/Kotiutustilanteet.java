package com.example.pesapalloxr.DataClasses;

public class Kotiutustilanteet {

    Integer id;
    Integer jakso;
    Integer vuoro;
    Integer joukkue;
    Integer batter;
    Integer hitnumber;
    Integer etenijaid;
    Boolean koppi;
    Integer karkilyonnit;
    Integer kunnari;
    Integer palot;
    Integer juoksut;
    Integer karkilyontikotia;
    Integer karkilyonnitkotia;
    Integer karkilyontiepaonnistuminenkotia;
    String lahtotilanne;
    String lopputilanne;

    public Kotiutustilanteet(Integer id, Integer jakso, Integer vuoro, Integer joukkue, Integer batter, Integer hitnumber, Integer etenijaid, Boolean koppi, Integer karkilyonnit, Integer kunnari, Integer palot, Integer juoksut, Integer karkilyontikotia, Integer karkilyonnitkotia, Integer karkilyontiepaonnistuminenkotia, String lahtotilanne, String lopputilanne) {
        this.id = id;
        this.jakso = jakso;
        this.vuoro = vuoro;
        this.joukkue = joukkue;
        this.batter = batter;
        this.hitnumber = hitnumber;
        this.etenijaid = etenijaid;
        this.koppi = koppi;
        this.karkilyonnit = karkilyonnit;
        this.kunnari = kunnari;
        this.palot = palot;
        this.juoksut = juoksut;
        this.karkilyontikotia = karkilyontikotia;
        this.karkilyonnitkotia = karkilyonnitkotia;
        this.karkilyontiepaonnistuminenkotia = karkilyontiepaonnistuminenkotia;
        this.lahtotilanne = lahtotilanne;
        this.lopputilanne = lopputilanne;
    }

    public Integer getJoukkue() {
        return joukkue;
    }

    public void setJoukkue(Integer joukkue) {
        this.joukkue = joukkue;
    }

    public Integer getKunnari() {
        return kunnari;
    }

    public void setKunnari(Integer kunnari) {
        this.kunnari = kunnari;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJakso() {
        return jakso;
    }

    public void setJakso(Integer jakso) {
        this.jakso = jakso;
    }

    public Integer getVuoro() {
        return vuoro;
    }

    public void setVuoro(Integer vuoro) {
        this.vuoro = vuoro;
    }

    public Integer getBatter() {
        return batter;
    }

    public void setBatter(Integer batter) {
        this.batter = batter;
    }

    public Integer getHitnumber() {
        return hitnumber;
    }

    public void setHitnumber(Integer hitnumber) {
        this.hitnumber = hitnumber;
    }

    public Integer getEtenijaid() {
        return etenijaid;
    }

    public void setEtenijaid(Integer etenijaid) {
        this.etenijaid = etenijaid;
    }

    public Integer getKarkilyonnit() {
        return karkilyonnit;
    }

    public void setKarkilyonnit(Integer karkilyonnit) {
        this.karkilyonnit = karkilyonnit;
    }

    public Integer getPalot() {
        return palot;
    }

    public void setPalot(Integer palot) {
        this.palot = palot;
    }

    public Integer getJuoksut() {
        return juoksut;
    }

    public void setJuoksut(Integer juoksut) {
        this.juoksut = juoksut;
    }

    public Integer getKarkilyontikotia() {
        return karkilyontikotia;
    }

    public void setKarkilyontikotia(Integer karkilyontikotia) {
        this.karkilyontikotia = karkilyontikotia;
    }

    public Integer getKarkilyonnitkotia() {
        return karkilyonnitkotia;
    }

    public void setKarkilyonnitkotia(Integer karkilyonnitkotia) {
        this.karkilyonnitkotia = karkilyonnitkotia;
    }

    public Integer getKarkilyontiepaonnistuminenkotia() {
        return karkilyontiepaonnistuminenkotia;
    }

    public void setKarkilyontiepaonnistuminenkotia(Integer karkilyontiepaonnistuminenkotia) {
        this.karkilyontiepaonnistuminenkotia = karkilyontiepaonnistuminenkotia;
    }

    public String getLahtotilanne() {
        return lahtotilanne;
    }

    public void setLahtotilanne(String lahtotilanne) {
        this.lahtotilanne = lahtotilanne;
    }

    public String getLopputilanne() {
        return lopputilanne;
    }

    public void setLopputilanne(String lopputilanne) {
        this.lopputilanne = lopputilanne;
    }

    public Boolean getKoppi() {
        return koppi;
    }

    public void setKoppi(Boolean koppi) {
        this.koppi = koppi;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
