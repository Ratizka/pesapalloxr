package com.example.pesapalloxr;


public class Lyontitiedot {

    Double koordinaattix;
    Double koordinaattiy;
    String sijainti;
    String kuvio;
    String lyonnintyyppi;
    String merkki;
    String syotto;
    String lyoja;
    Integer lyojaID;
    String sisajoukkue;
    Integer sisajoukkueid;
    String jakso;
    Integer vuoropari;
    Integer ottelunID;
    String ulkopelipaikka;
    String ulkopelivirhe;
    String ulkopelisuorittaja;
    Integer ulkopelaajaID;
    String ulkopelisuoritus;
    String vaaraAlla;
    String lopputulos;
    Integer juoksut;
    String lapilyonti;
    Integer lyontinumero;
    String ulkopelijoukkue;
    Integer ulkopelijoukkueID;
    String etenija;
    Integer etenijaID;
    String etenijanlaatu;
    Double juoksutodennakoisyys;
    String kunnari;
    String tilanne;
    Integer palot;
    String suunta;
    String kumurakorkeus;
    String saumakorkeus;
    String karkaus;
    String ulkopelitempo;
    Double lapilyontitn;

    public Lyontitiedot(Double koordinaattix, Double koordinaattiy, String sijainti, String kuvio, String lyonnintyyppi,
                        String merkki, String syotto, String lyoja, Integer lyojaID, String sisajoukkue, Integer sisajoukkueid,
                        String jakso, Integer vuoropari, Integer ottelunID, String ulkopelipaikka, String ulkopelivirhe,
                        String ulkopelisuorittaja, Integer ulkopelaajaID, String ulkopelisuoritus, String vaaraAlla,
                        String lopputulos, Integer juoksut, String lapilyonti, Integer lyontinumero, String ulkopelijoukkue,
                        Integer ulkopelijoukkueID, String etenija, String etenijanlaatu, Double juoksutodennakoisyys,
                        String kunnari, String tilanne, Integer palot, String suunta, String kumurakorkeus,
                        String saumakorkeus, String karkaus, String ulkopelitempo, Double lapilyontitn) {

        this.koordinaattix = koordinaattix;
        this.koordinaattiy = koordinaattiy;
        this.sijainti = sijainti;
        this.kuvio = kuvio;
        this.lyonnintyyppi = lyonnintyyppi;
        this.merkki = merkki;
        this.syotto = syotto;
        this.lyoja = lyoja;
        this.lyojaID = lyojaID;
        this.sisajoukkue = sisajoukkue;
        this.sisajoukkueid = sisajoukkueid;
        this.jakso = jakso;
        this.vuoropari = vuoropari;
        this.ottelunID = ottelunID;
        this.ulkopelipaikka = ulkopelipaikka;
        this.ulkopelivirhe = ulkopelivirhe;
        this.ulkopelisuorittaja = ulkopelisuorittaja;
        this.ulkopelaajaID = ulkopelaajaID;
        this.ulkopelisuoritus = ulkopelisuoritus;
        this.vaaraAlla = vaaraAlla;
        this.lopputulos = lopputulos;
        this.juoksut = juoksut;
        this.lapilyonti = lapilyonti;
        this.lyontinumero = lyontinumero;
        this.ulkopelijoukkue = ulkopelijoukkue;
        this.ulkopelijoukkueID = ulkopelijoukkueID;
        this.etenija = etenija;
        this.etenijanlaatu = etenijanlaatu;
        this.juoksutodennakoisyys = juoksutodennakoisyys;
        this.kunnari = kunnari;
        this.tilanne = tilanne;
        this.palot = palot;
        this.suunta = suunta;
        this.kumurakorkeus = kumurakorkeus;
        this.saumakorkeus = saumakorkeus;
        this.karkaus = karkaus;
        this.ulkopelitempo = ulkopelitempo;
        this.lapilyontitn = lapilyontitn;
    }

    public Integer getLyojaID() {
        return lyojaID;
    }

    public void setLyojaID(Integer lyojaID) {
        this.lyojaID = lyojaID;
    }

    public Integer getUlkopelaajaID() {
        return ulkopelaajaID;
    }

    public void setUlkopelaajaID(Integer ulkopelaajaID) {
        this.ulkopelaajaID = ulkopelaajaID;
    }

    public Integer getUlkopelijoukkueID() {
        return ulkopelijoukkueID;
    }

    public void setUlkopelijoukkueID(Integer ulkopelijoukkueID) {
        this.ulkopelijoukkueID = ulkopelijoukkueID;
    }

    public Integer getPalot() {
        return palot;
    }

    public void setPalot(Integer palot) {
        this.palot = palot;
    }

    public String getSuunta() {
        return suunta;
    }

    public void setSuunta(String suunta) {
        this.suunta = suunta;
    }

    public String getKumurakorkeus() {
        return kumurakorkeus;
    }

    public void setKumurakorkeus(String kumurakorkeus) {
        this.kumurakorkeus = kumurakorkeus;
    }

    public String getSaumakorkeus() {
        return saumakorkeus;
    }

    public void setSaumakorkeus(String saumakorkeus) {
        this.saumakorkeus = saumakorkeus;
    }

    public String getKarkaus() {
        return karkaus;
    }

    public void setKarkaus(String karkaus) {
        this.karkaus = karkaus;
    }

    public String getUlkopelitempo() {
        return ulkopelitempo;
    }

    public void setUlkopelitempo(String ulkopelitempo) {
        this.ulkopelitempo = ulkopelitempo;
    }

    public Double getLapilyontitn() {
        return lapilyontitn;
    }

    public void setLapilyontitn(Double lapilyontitn) {
        this.lapilyontitn = lapilyontitn;
    }

    /*
    public Lyontitiedot(Double koordinaattix, Double koordinaattiy, String sijainti, String kuvio, String tyyppi, String merkki, String syotto, String lyoja, String joukkue, Integer sisajoukkueid, String jakso, Integer vuoropari, Integer ottelunID, String ulkopelipaikka, String ulkopelivirhe, String ulkopelisuorittaja, String ulkopelisuoritus, String vaaraAlla, String lyonnityyppi, Integer juoksut, String lapilyonti, Integer lyontinumero, String ulkopelijoukkue, String etenija, String etenijanlaatu, Double juoksutodennakoisyys, String kunnari, String tilanne) {
        this.koordinaattix = koordinaattix;
        this.koordinaattiy = koordinaattiy;
        this.sijainti = sijainti;
        this.kuvio = kuvio;
        this.tyyppi = tyyppi;
        this.merkki = merkki;
        this.syotto = syotto;
        this.lyoja = lyoja;
        this.joukkue = joukkue;
        this.sisajoukkueid = sisajoukkueid;
        this.jakso = jakso;
        this.vuoropari = vuoropari;
        this.ottelunID = ottelunID;
        this.ulkopelipaikka = ulkopelipaikka;
        this.ulkopelivirhe = ulkopelivirhe;
        this.ulkopelisuorittaja = ulkopelisuorittaja;
        this.ulkopelisuoritus = ulkopelisuoritus;
        this.vaaraAlla = vaaraAlla;
        this.lyonnityyppi = lyonnityyppi;
        this.juoksut = juoksut;
        this.lapilyonti = lapilyonti;
        this.lyontinumero = lyontinumero;
        this.ulkopelijoukkue = ulkopelijoukkue;
        this.etenija = etenija;
        this.etenijanlaatu = etenijanlaatu;
        this.juoksutodennakoisyys = juoksutodennakoisyys;
        this.kunnari = kunnari;
        this.tilanne = tilanne;
    }

     */

    public Integer getSisajoukkueid() {
        return sisajoukkueid;
    }

    public void setSisajoukkueid(Integer sisajoukkueid) {
        this.sisajoukkueid = sisajoukkueid;
    }

    public String getUlkopelisuoritus() {
        return ulkopelisuoritus;
    }

    public void setUlkopelisuoritus(String ulkopelisuoritus) {
        this.ulkopelisuoritus = ulkopelisuoritus;
    }

    public String getTilanne() {
        return tilanne;
    }

    public void setTilanne(String tilanne) {
        this.tilanne = tilanne;
    }

    public String getKunnari() {
        return kunnari;
    }

    public void setKunnari(String kunnari) {
        this.kunnari = kunnari;
    }

    public Double getJuoksutodennakoisyys() {
        return juoksutodennakoisyys;
    }

    public void setJuoksutodennakoisyys(Double juoksutodennakoisyys) {
        this.juoksutodennakoisyys = juoksutodennakoisyys;
    }

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

    public String getLyonnintyyppi() {
        return lyonnintyyppi;
    }

    public void setLyonnintyyppi(String lyonnintyyppi) {
        this.lyonnintyyppi = lyonnintyyppi;
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

    public String getSisajoukkue() {
        return sisajoukkue;
    }

    public void setSisajoukkue(String sisajoukkue) {
        this.sisajoukkue = sisajoukkue;
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

    public String getEtenijanlaatu() {
        return etenijanlaatu;
    }

    public void setEtenijanlaatu(String etenijanlaatu) {
        this.etenijanlaatu = etenijanlaatu;
    }

    public Integer getVuoropari() {
        return vuoropari;
    }

    public void setVuoropari(Integer vuoropari) {
        this.vuoropari = vuoropari;
    }

    public Integer getOttelunID() {
        return ottelunID;
    }

    public void setOttelunID(Integer ottelunID) {
        this.ottelunID = ottelunID;
    }

    public String getUlkopelipaikka() {
        return ulkopelipaikka;
    }

    public void setUlkopelipaikka(String ulkopelipaikka) {
        this.ulkopelipaikka = ulkopelipaikka;
    }

    public String getUlkopelivirhe() {
        return ulkopelivirhe;
    }

    public void setUlkopelivirhe(String ulkopelivirhe) {
        this.ulkopelivirhe = ulkopelivirhe;
    }

    public String getUlkopelisuorittaja() {
        return ulkopelisuorittaja;
    }

    public void setUlkopelisuorittaja(String ulkopelisuorittaja) {
        this.ulkopelisuorittaja = ulkopelisuorittaja;
    }

    public String getVaaraAlla() {
        return vaaraAlla;
    }

    public void setVaaraAlla(String vaaraAlla) {
        this.vaaraAlla = vaaraAlla;
    }

    public String getLopputulos() {
        return lopputulos;
    }

    public void setLopputulos(String lopputulos) {
        this.lopputulos = lopputulos;
    }

    public Integer getJuoksut() {
        return juoksut;
    }

    public void setJuoksut(Integer juoksut) {
        this.juoksut = juoksut;
    }

    public String getLapilyonti() {
        return lapilyonti;
    }

    public void setLapilyonti(String lapilyonti) {
        this.lapilyonti = lapilyonti;
    }

    public Integer getLyontinumero() {
        return lyontinumero;
    }

    public void setLyontinumero(Integer lyontinumero) {
        this.lyontinumero = lyontinumero;
    }

    public String getUlkopelijoukkue() {
        return ulkopelijoukkue;
    }

    public void setUlkopelijoukkue(String ulkopelijoukkue) {
        this.ulkopelijoukkue = ulkopelijoukkue;
    }

    public String getEtenija() {
        return etenija;
    }

    public void setEtenija(String etenija) {
        this.etenija = etenija;
    }

}
