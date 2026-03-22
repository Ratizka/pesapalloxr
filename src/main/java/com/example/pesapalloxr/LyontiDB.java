package com.example.pesapalloxr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LyontiDB {

    private LyontiDB() {

    }

    protected static void Lisaa(List<Lyontitiedot> data, String tietokannanmuutos) {
        try (Connection yhdistys = DB.tiedot(tietokannanmuutos)) {
            PreparedStatement insert = yhdistys.prepareStatement(
                    """
                            INSERT INTO pesistiedot (otteluID, jakso, vuoropari, tilanne, palot, lyontinumero, sisajoukkue, sisajoukkueID, lyoja, lyojaID, etenija, etenijanlaatu, ulkopelijoukkue, ulkopelijoukkueID,
                            ulkopelisuorittaja, ulkopelaajaID, ulkopelipaikka, ulkopelisuoritus, ulkopelivirhe, ulkopelitempo, kuvio, vaaraAlla, merkki, karkaus, syotto, lyonnintyyppi, saumakorkeus,
                            kumurankorkeus, suunta, koordinaattix, koordinaattiy, sijainti, lopputulos, juoksut, lapilyonti, kunnari, juoksutodennakoisyys, lapilyontitn)
                            VALUES (?, ? ,? ,? ,? ,? ,? , ?, ?, ?, ?, ?,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , ?, ?, ?,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)
                            """);

            for (Lyontitiedot datum : data) {
                insert.setInt(1, datum.getOttelunID());
                insert.setString(2, datum.getJakso());
                insert.setInt(3, datum.getVuoropari());
                insert.setString(4, datum.getTilanne());
                insert.setInt(5, datum.getPalot());
                insert.setInt(6, datum.getLyontinumero());

                insert.setString(7, datum.getSisajoukkue());
                insert.setInt(8, datum.getSisajoukkueid());
                insert.setString(9, datum.getLyoja());
                insert.setInt(10, datum.getLyojaID());
                insert.setString(11, datum.getEtenija());
                insert.setString(12, datum.getEtenijanlaatu());

                insert.setString(13, datum.getUlkopelijoukkue());
                insert.setInt(14, datum.getUlkopelijoukkueID());
                insert.setString(15, datum.getUlkopelisuorittaja());
                insert.setInt(16, datum.getUlkopelaajaID());
                insert.setString(17,datum.getUlkopelipaikka());
                insert.setString(18,datum.getUlkopelisuoritus());
                insert.setString(19,datum.getUlkopelivirhe());
                insert.setString(20,datum.getUlkopelitempo());

                insert.setString(21, datum.getKuvio());
                insert.setString(22, datum.getVaaraAlla());
                insert.setString(23, datum.getMerkki());
                insert.setString(24, datum.getKarkaus());
                insert.setString(25, datum.getSyotto());
                insert.setString(26, datum.getLyonnintyyppi());
                insert.setString(27, datum.getSaumakorkeus());
                insert.setString(28, datum.getKumurakorkeus());
                insert.setString(29, datum.getSuunta());

                insert.setDouble(30, datum.getKoordinaattix());
                insert.setDouble(31, datum.getKoordinaattiy());
                insert.setString(32, datum.getSijainti());

                insert.setString(33, datum.getLopputulos());
                insert.setInt(34, datum.getJuoksut());
                insert.setString(35, datum.getLapilyonti());
                insert.setString(36, datum.getKunnari());

                insert.setDouble(37, datum.getJuoksutodennakoisyys());
                insert.setDouble(38, datum.getLapilyontitn());
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
