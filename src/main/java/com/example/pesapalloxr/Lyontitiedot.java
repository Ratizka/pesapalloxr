package com.example.pesapalloxr;


public class Lyontitiedot {

    public Double getKoordinaattix() {
        return koordinaattix;
    }

    public void setKoordinaattix(Double koordinaattix) {
        this.koordinaattix = koordinaattix;
    }

    public Double getKoordinaattiy() {
        return koordinaattiy;
    }

    public void setKoordinaattiy(Double koordinaattiy) {
        this.koordinaattiy = koordinaattiy;
    }

    public String getKuvio() {
        return kuvio;
    }

    public void setKuvio(String kuvio) {
        this.kuvio = kuvio;
    }

    public String getTyyppi() {
        return tyyppi;
    }

    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }

    public String getMerkki() {
        return merkki;
    }

    public void setMerkki(String merkki) {
        this.merkki = merkki;
    }

    public String getSyotto() {
        return syotto;
    }

    public void setSyotto(String syotto) {
        this.syotto = syotto;
    }

    public String getLyoja() {
        return lyoja;
    }

    public void setLyoja(String lyoja) {
        this.lyoja = lyoja;
    }

    public String getJoukkue() {
        return joukkue;
    }

    public void setJoukkue(String joukkue) {
        this.joukkue = joukkue;
    }

    public String getJakso() {
        return jakso;
    }

    public void setJakso(String jakso) {
        this.jakso = jakso;
    }

    public String getSijainti() {
        return sijainti;
    }

    public void setSijainti(String sijainti) {
        this.sijainti = sijainti;
    }

    Double koordinaattix;
    Double koordinaattiy;
    String sijainti;
    String kuvio;
    String tyyppi;
    String merkki;
    String syotto;
    String lyoja;
    String joukkue;
    String jakso;


    public Lyontitiedot(Double koordinaattix, Double koordinaattiy, String sijainti, String kuvio, String tyyppi, String merkki, String syotto, String lyoja, String joukkue, String jakso) {
        this.koordinaattix = koordinaattix;
        this.koordinaattiy = koordinaattiy;
        this.sijainti = sijainti;
        this.kuvio = kuvio;
        this.tyyppi = tyyppi;
        this.merkki = merkki;
        this.syotto = syotto;
        this.lyoja = lyoja;
        this.joukkue = joukkue;
        this.jakso = jakso;
    }
}
