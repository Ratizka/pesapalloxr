package com.example.pesapalloxr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class PesapalloxrController {

    private final Map<String, Double> miestenXrMap = new HashMap<>();
    private final Map<String, Double> miestenSijaintiMapX = new HashMap<>();
    private final Map<String, Double> miestenSijaintiMapY = new HashMap<>();
    private final Map<String, Double> miestenEtaisyysVaikutusMap = new HashMap<>();
    public ComboBox<String> ulkopelijoukkuecombobox;
    public ComboBox<String> sisajoukkuecombobox;
    public TableColumn<Lyontitiedot, String> taulukkoTilanne;
    public RadioMenuItem vierasjoukkueToggle;

    @FXML
    private ComboBox<String> ulkopelisuorituscombobox;
    @FXML
    private ComboBox<String> lyontisuuntacombobox;
    @FXML
    private TextField juoksuodottama;
    @FXML
    private ComboBox<String> tilannecombobox;
    @FXML
    private ComboBox<String> karkauscombobox;
    @FXML
    private TextField sisajoukkeuid;
    @FXML
    private TextField ulkojoukkueid;
    @FXML
    private ComboBox<String> etenijalaatucombobox;
    @FXML
    private ComboBox<Lyojat> kotietenija;
    @FXML
    private ComboBox<Lyojat> vierasetenija;
    @FXML
    private ComboBox<String> ulkopelivirhe;
    @FXML
    private ComboBox<Integer> juoksut;
    @FXML
    private ComboBox<String> ulkopelipaikka;
    @FXML
    private ComboBox<Lyojat> kotilyojat;
    @FXML
    private ComboBox<Lyojat> vieraslyojat;
    @FXML
    private TextField ottelunid;
    @FXML
    private ComboBox<Lyojat> kotiulkopelaajat;
    @FXML
    private ComboBox<Lyojat> vierasulkopelaajat;
    @FXML
    private TextField ulkopelisuorittaja;
    @FXML
    private TextField ulkopelijoukkue;
    @FXML
    private ComboBox<Integer> vuoropari;
    @FXML
    private TextField etenija;
    @FXML
    private ComboBox<String> vaaraalla;
    @FXML
    private ComboBox<String> lapilyonti;
    @FXML
    private ComboBox<String> kunnari;
    @FXML
    private ComboBox<Integer> lyontinumero;
    @FXML
    private TextField idottelu;
    @FXML
    private TableColumn<Lyontitiedot, Integer> taulukkoOttelunID;
    @FXML
    private TableColumn<Lyontitiedot, Integer> taulukkovuoropari;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkoetenija;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkoulkopelijoukkue;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkoulkopelaaja;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkoulkopelipaikka;
    @FXML
    private TableColumn<Lyontitiedot, Integer> taulukkolyontinumero;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkovaaraalla;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkolyonti;
    @FXML
    private TableColumn<Lyontitiedot, Integer> taulukkojuoksut;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkolapilyonti;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkokunnari;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkoulkopelivirhe;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkoEtenijaLaatu;
    @FXML
    private TableColumn<Lyontitiedot, Double> taulukkoJuoksunTodennakoisyys;
    @FXML
    private Canvas kentta;
    @FXML
    private TextField koordinaattix;
    @FXML
    private TextField koordinaattiy;
    @FXML
    private ComboBox<String> kuvio;
    @FXML
    private ComboBox<String> tyyppi;
    @FXML
    private ComboBox<String> merkki;
    @FXML
    private ComboBox<String> syotto;
    @FXML
    private TextField joukkue;
    @FXML
    private TextField lyoja;
    @FXML
    private ComboBox<String> jakso;
    @FXML
    private TableView<Lyontitiedot> taulukkoxr;
    @FXML
    private TableColumn<Lyontitiedot, Double> taulukkokoordinaattix;
    @FXML
    private TableColumn<Lyontitiedot, Double> taulukkokoordinaattiy;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkokuvio;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkosijainti;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkomerkki;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkosyotto;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkojakso;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkojoukkue;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkolyoja;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkotyyppi;
    @FXML
    private BorderPane ui;
    @FXML
    private TextField sijaintitext;
    @FXML
    private ComboBox<String> lyonticombobox;
    @FXML
    private RadioButton vierasjoukkue;


    @FXML
    public void initialize() {
        resoluutio();
        pesapallokentta();
        valikot();
        taulukontiedot();
        taulukkomuokkaus();
        sarakkeidenmuokkaus();
        xrmap();
        miestensijanti();
        miestensijantiy();
    }

    private void resoluutio() {
        Rectangle2D koko = Screen.getPrimary().getVisualBounds();
        double width = koko.getWidth();
        double height = koko.getHeight();
        ui.setPrefWidth(width);
        ui.setPrefHeight(height);
    }

    private void taulukontiedot() {
        taulukkoxr.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taulukkokoordinaattix.setCellValueFactory(new PropertyValueFactory<>("koordinaattix"));
        taulukkokoordinaattiy.setCellValueFactory(new PropertyValueFactory<>("koordinaattiy"));
        taulukkokuvio.setCellValueFactory(new PropertyValueFactory<>("kuvio"));
        taulukkosijainti.setCellValueFactory(new PropertyValueFactory<>("sijainti"));
        taulukkomerkki.setCellValueFactory(new PropertyValueFactory<>("merkki"));
        taulukkosyotto.setCellValueFactory(new PropertyValueFactory<>("syotto"));
        taulukkojakso.setCellValueFactory(new PropertyValueFactory<>("jakso"));
        taulukkojoukkue.setCellValueFactory(new PropertyValueFactory<>("joukkue"));
        taulukkolyoja.setCellValueFactory(new PropertyValueFactory<>("lyoja"));
        taulukkotyyppi.setCellValueFactory(new PropertyValueFactory<>("tyyppi"));
        taulukkoOttelunID.setCellValueFactory(new PropertyValueFactory<>("ottelunID"));
        taulukkovuoropari.setCellValueFactory(new PropertyValueFactory<>("vuoropari"));
        taulukkoetenija.setCellValueFactory(new PropertyValueFactory<>("etenija"));
        taulukkoulkopelijoukkue.setCellValueFactory(new PropertyValueFactory<>("ulkopelijoukkue"));
        taulukkoulkopelaaja.setCellValueFactory(new PropertyValueFactory<>("ulkopelisuorittaja"));
        taulukkoulkopelipaikka.setCellValueFactory(new PropertyValueFactory<>("ulkopelipaikka"));
        taulukkolyontinumero.setCellValueFactory(new PropertyValueFactory<>("lyontinumero"));
        taulukkovaaraalla.setCellValueFactory(new PropertyValueFactory<>("vaaraAlla"));
        taulukkolyonti.setCellValueFactory(new PropertyValueFactory<>("lyonti"));
        taulukkojuoksut.setCellValueFactory(new PropertyValueFactory<>("juoksut"));
        taulukkolapilyonti.setCellValueFactory(new PropertyValueFactory<>("lapilyonti"));
        taulukkokunnari.setCellValueFactory(new PropertyValueFactory<>("kunnari"));
        taulukkoulkopelivirhe.setCellValueFactory(new PropertyValueFactory<>("ulkopelivirhe"));
        taulukkoEtenijaLaatu.setCellValueFactory(new PropertyValueFactory<>("etenijanlaatu"));
        taulukkoJuoksunTodennakoisyys.setCellValueFactory(new PropertyValueFactory<>("juoksutodennakoisyys"));
        taulukkoTilanne.setCellValueFactory(new PropertyValueFactory<>("tilanne"));
    }

    @FXML
    private void sulje(){
        System.exit(0);
    }

    private void valikot() {
        kuvio.getItems().addAll("oulu", "pertsa", "ristivitonen", "kaannettypertsa", "tahko", "tahko2", "sailytys",
                "karvauskahdella", "karvausyhdella", "muu"
        );
        kuvio.getSelectionModel().selectFirst();


        tyyppi.getItems().addAll("kumura", "vaakamaila", "nappi", "pomppu", "pussari", "pystari/viisto",
                "varsi", "koppi");
        tyyppi.getSelectionModel().selectFirst();


        merkki.getItems().addAll("vapaa", "vääräpois", "kulma", "lento"
        );
        merkki.getSelectionModel().selectFirst();

        //

        syotto.getItems().addAll("perus", "puolikorkea", "tolppa", "matala", "lautasväärä", "taktinen väärä/linkku");
        syotto.getSelectionModel().selectFirst();

        jakso.getItems().addAll("1", "2", "supervuoro", "kotari");
        jakso.getSelectionModel().selectFirst();

        lyonticombobox.getItems().addAll("kärkilyönti", "palo", "haava");
        lyonticombobox.getSelectionModel().selectFirst();

        juoksut.getItems().addAll(0, 1, 2, 3, 4);
        juoksut.getSelectionModel().selectFirst();

        ulkopelivirhe.getItems().addAll("ei", "kiinniotto", "häpläys", "harhaheitto", "heittoa ei saada kiinni");
        ulkopelivirhe.getSelectionModel().selectFirst();

        etenijalaatucombobox.getItems().addAll("erinomainen", "hyvä", "keskiverto", "heikko", "huono");
        etenijalaatucombobox.getSelectionModel().selectFirst();

        ulkopelipaikka.getItems().addAll("l", "S", "1v", "3v", "3p", "2p", "2v", "3k", "2k");
        ulkopelipaikka.getSelectionModel().selectFirst();

        vuoropari.getItems().addAll(1,2,3,4);
        vuoropari.getSelectionModel().selectFirst();

        vaaraalla.getItems().addAll("ei", "kyllä");
        vaaraalla.getSelectionModel().selectFirst();

        lapilyonti.getItems().addAll("ei", "kyllä");
        lapilyonti.getSelectionModel().selectFirst();

        lyontinumero.getItems().addAll(1,2,3);
        lyontinumero.getSelectionModel().selectFirst();

        kunnari.getItems().addAll("ei","kyllä");
        kunnari.getSelectionModel().selectFirst();

        lyontisuuntacombobox.getItems().addAll(
                "3-raja", "3-luukku", "3-sauma", "3-pussi", "3-kolmosjatke","3-koppari",
                "keskitakanen", "keskisauma", "keskipussi","keskikentta",
                "2-sauma", "2-pussi", "2-luukku", "2-raja",
                "2-koppari", "2-jatke"
        );
        lyontisuuntacombobox.getSelectionModel().selectFirst();

        ulkopelisuorituscombobox.getItems().addAll("ei", "kyllä");
        ulkopelisuorituscombobox.getSelectionModel().selectFirst();

        tilannecombobox.getItems().addAll("0-3", "1-3", "2-3", "ajo");
        tilannecombobox.getSelectionModel().selectFirst();

        karkauscombobox.getItems().addAll("ei", "kyllä");
        karkauscombobox.getSelectionModel().selectFirst();
    }

    private void xrmap(){
        miestenXrMap.put("msualkuarvo", -3.17379);
        miestenXrMap.put("kumura", 0.195);
        miestenXrMap.put("nappi", 0.325);
        miestenXrMap.put("pomppu", 0.699);
        miestenXrMap.put("pussari", 0.271);
        miestenXrMap.put("pystari/viisto", -0.097);
        miestenXrMap.put("vaakamaila", 0.289);
        miestenXrMap.put("varsi", 0.176);
        miestenXrMap.put("koppi", 0.000);

        miestenXrMap.put("karvauskahdella", 0.369);
        miestenXrMap.put("karvausyhdella", 0.391);
        miestenXrMap.put("oulu", 0.035);
        miestenXrMap.put("pertsa", -0.209);
        miestenXrMap.put("ristivitonen", 0.564);
        miestenXrMap.put("sailytys", -0.980);
        miestenXrMap.put("tahko", -0.319);
        miestenXrMap.put("tahko2", -0.059);
        miestenXrMap.put("kaannettypertsa", 0.000);
        miestenXrMap.put("muu", 0.75);

        miestenXrMap.put("vääräpois", 0.000);
        miestenXrMap.put("kulma", 0.050);
        miestenXrMap.put("lento", 0.100);
        miestenXrMap.put("vapaa", 1.319);
        miestenXrMap.put("etulyhyt", 0.000);
        miestenXrMap.put("etupitkä", 0.247);
        miestenXrMap.put("linjaetu", 1.042);
        miestenXrMap.put("linjataka", 1.779);
        miestenXrMap.put("takakenttä", 1.506);
        miestenXrMap.put("erinomainen", 0.448);
        miestenXrMap.put("huono", 0.102);
        miestenXrMap.put("hyvä", 0.195);
        miestenXrMap.put("keskiverto", 0.141);
        miestenXrMap.put("heikko", 0.000);

        miestenXrMap.put("2k", 7.11811);
        miestenXrMap.put("3k", 7.6466);
        miestenXrMap.put("2p", 4.28322);
        miestenXrMap.put("2v", 5.76319);
        miestenXrMap.put("3p", 2.68138);
        miestenXrMap.put("3v", 8.93);
        miestenXrMap.put("l", 23.19);
        miestenXrMap.put("S", 8.53);
        miestenXrMap.put("1v", 6.57882);

    }

    private void miestensijanti(){
        miestenSijaintiMapX.put("kaannettypertsal", 0.50);
        miestenSijaintiMapX.put("oulul", 0.50);
        miestenSijaintiMapX.put("pertsal", 0.50);
        miestenSijaintiMapX.put("ristivitonenl", 0.50);
        miestenSijaintiMapX.put("tahkol", 0.50);
        miestenSijaintiMapX.put("tahko2l", 0.50);
        miestenSijaintiMapX.put("sailytysl", 0.50);
        miestenSijaintiMapX.put("karvauskahdellal", 0.50);
        miestenSijaintiMapX.put("karvausyhdellal", 0.50);
        miestenSijaintiMapX.put("muul", 0.50);

        miestenSijaintiMapX.put("kaannettypertsa3v", 0.75);
        miestenSijaintiMapX.put("oulu3v", 0.80);
        miestenSijaintiMapX.put("pertsa3v", 0.765);
        miestenSijaintiMapX.put("ristivitonen3v", 0.77);
        miestenSijaintiMapX.put("tahko3v", 0.80);
        miestenSijaintiMapX.put("tahko23v", 0.755);
        miestenSijaintiMapX.put("sailytys3v", 0.80);
        miestenSijaintiMapX.put("karvauskahdella3v", 0.80);
        miestenSijaintiMapX.put("karvausyhdella3v", 0.80);
        miestenSijaintiMapX.put("muu3v", 0.80);

        miestenSijaintiMapX.put("kaannettypertsa1v", 0.41);
        miestenSijaintiMapX.put("oulu1v", 0.52);
        miestenSijaintiMapX.put("pertsa1v", 0.53);
        miestenSijaintiMapX.put("ristivitonen1v", 0.425);
        miestenSijaintiMapX.put("tahko1v", 0.60);
        miestenSijaintiMapX.put("tahko21v", 0.55);
        miestenSijaintiMapX.put("sailytys1v", 0.62);
        miestenSijaintiMapX.put("karvauskahdella1v", 0.63);
        miestenSijaintiMapX.put("karvausyhdella1v", 0.63);
        miestenSijaintiMapX.put("muu1v", 0.20);


        miestenSijaintiMapX.put("kaannettypertsaS", 0.25);
        miestenSijaintiMapX.put("ouluS", 0.22);
        miestenSijaintiMapX.put("pertsaS", 0.235);
        miestenSijaintiMapX.put("ristivitonenS", 0.20);
        miestenSijaintiMapX.put("tahkoS", 0.235);
        miestenSijaintiMapX.put("tahko2S", 0.34);
        miestenSijaintiMapX.put("sailytysS", 0.385);
        miestenSijaintiMapX.put("karvauskahdellaS", 0.33);
        miestenSijaintiMapX.put("karvausyhdellaS", 0.22);
        miestenSijaintiMapX.put("muuS", 0.20);


        miestenSijaintiMapX.put("kaannettypertsa3p", 0.68);
        miestenSijaintiMapX.put("oulu3p", 0.68);
        miestenSijaintiMapX.put("pertsa3p", 0.68);
        miestenSijaintiMapX.put("ristivitonen3p", 0.68);
        miestenSijaintiMapX.put("tahko3p", 0.535);
        miestenSijaintiMapX.put("tahko23p", 0.68);
        miestenSijaintiMapX.put("sailytys3p", 0.58);
        miestenSijaintiMapX.put("karvauskahdella3p", 0.68);
        miestenSijaintiMapX.put("karvausyhdella3p", 0.68);
        miestenSijaintiMapX.put("muu3p", 0.80);

        miestenSijaintiMapX.put("kaannettypertsa2p", 0.52);
        miestenSijaintiMapX.put("oulu2p", 0.43);
        miestenSijaintiMapX.put("pertsa2p", 0.43);
        miestenSijaintiMapX.put("ristivitonen2p", 0.525);
        miestenSijaintiMapX.put("tahko2p", 0.425);
        miestenSijaintiMapX.put("tahko22p", 0.42);
        miestenSijaintiMapX.put("sailytys2p", 0.43);
        miestenSijaintiMapX.put("karvauskahdella2p", 0.43);
        miestenSijaintiMapX.put("karvausyhdella2p", 0.43);
        miestenSijaintiMapX.put("muu2p", 0.50);

        miestenSijaintiMapX.put("kaannettypertsa2v", 0.30);
        miestenSijaintiMapX.put("oulu2v", 0.30);
        miestenSijaintiMapX.put("pertsa2v", 0.30);
        miestenSijaintiMapX.put("ristivitonen2v", 0.30);
        miestenSijaintiMapX.put("tahko2v", 0.30);
        miestenSijaintiMapX.put("tahko22v", 0.20);
        miestenSijaintiMapX.put("sailytys2v", 0.30);
        miestenSijaintiMapX.put("karvauskahdella2v", 0.30);
        miestenSijaintiMapX.put("karvausyhdella2v", 0.30);
        miestenSijaintiMapX.put("muu2v", 0.20);

        miestenSijaintiMapX.put("kaannettypertsa3k", 0.65);
        miestenSijaintiMapX.put("oulu3k", 0.65);
        miestenSijaintiMapX.put("pertsa3k", 0.65);
        miestenSijaintiMapX.put("ristivitonen3k", 0.65);
        miestenSijaintiMapX.put("tahko3k", 0.65);
        miestenSijaintiMapX.put("tahko23k", 0.65);
        miestenSijaintiMapX.put("sailytys3k", 0.65);
        miestenSijaintiMapX.put("karvauskahdella3k", 0.65);
        miestenSijaintiMapX.put("karvausyhdella3k", 0.65);
        miestenSijaintiMapX.put("muu3k", 0.70);

        miestenSijaintiMapX.put("kaannettypertsa2k", 0.35);
        miestenSijaintiMapX.put("oulu2k", 0.35);
        miestenSijaintiMapX.put("pertsa2k", 0.35);
        miestenSijaintiMapX.put("ristivitonen2k", 0.35);
        miestenSijaintiMapX.put("tahko2k", 0.35);
        miestenSijaintiMapX.put("tahko22k", 0.35);
        miestenSijaintiMapX.put("sailytys2k", 0.35);
        miestenSijaintiMapX.put("karvauskahdella2k", 0.35);
        miestenSijaintiMapX.put("karvausyhdella2k", 0.35);
        miestenSijaintiMapX.put("muu2k", 0.30);
    }

    private void miestensijantiy(){
        miestenSijaintiMapY.put("kaannettypertsal", 0.93);
        miestenSijaintiMapY.put("oulul", 0.93);
        miestenSijaintiMapY.put("pertsal", 0.93);
        miestenSijaintiMapY.put("ristivitonenl", 0.93);
        miestenSijaintiMapY.put("tahkol", 0.93);
        miestenSijaintiMapY.put("tahko2l", 0.93);
        miestenSijaintiMapY.put("sailytysl", 0.93);
        miestenSijaintiMapY.put("karvauskahdellal", 0.93);
        miestenSijaintiMapY.put("karvausyhdellal", 0.93);
        miestenSijaintiMapY.put("muul", 0.93);

        miestenSijaintiMapY.put("kaannettypertsa3v", 0.60);
        miestenSijaintiMapY.put("oulu3v", 0.56);
        miestenSijaintiMapY.put("pertsa3v", 0.61);
        miestenSijaintiMapY.put("ristivitonen3v", 0.585);
        miestenSijaintiMapY.put("tahko3v", 0.52);
        miestenSijaintiMapY.put("tahko23v", 0.61);
        miestenSijaintiMapY.put("sailytys3v", 0.54);
        miestenSijaintiMapY.put("karvauskahdella3v", 0.56);
        miestenSijaintiMapY.put("karvausyhdella3v", 0.56);
        miestenSijaintiMapY.put("muu3v", 0.54);

        miestenSijaintiMapY.put("kaannettypertsa1v", 0.55);
        miestenSijaintiMapY.put("oulu1v", 0.68);
        miestenSijaintiMapY.put("pertsa1v", 0.56);
        miestenSijaintiMapY.put("ristivitonen1v", 0.68);
        miestenSijaintiMapY.put("tahko1v", 0.66);
        miestenSijaintiMapY.put("tahko21v", 0.52);
        miestenSijaintiMapY.put("sailytys1v", 0.68);
        miestenSijaintiMapY.put("karvauskahdella1v", 0.75);
        miestenSijaintiMapY.put("karvausyhdella1v", 0.75);
        miestenSijaintiMapY.put("muu1v", 0.20);


        miestenSijaintiMapY.put("kaannettypertsaS", 0.60);
        miestenSijaintiMapY.put("ouluS", 0.58);
        miestenSijaintiMapY.put("pertsaS", 0.61);
        miestenSijaintiMapY.put("ristivitonenS", 0.56);
        miestenSijaintiMapY.put("tahkoS", 0.58);
        miestenSijaintiMapY.put("tahko2S", 0.645);
        miestenSijaintiMapY.put("sailytysS", 0.68);
        miestenSijaintiMapY.put("karvauskahdellaS", 0.70);
        miestenSijaintiMapY.put("karvausyhdellaS", 0.58);
        miestenSijaintiMapY.put("muuS", 0.54);


        miestenSijaintiMapY.put("kaannettypertsa3p", 0.47);
        miestenSijaintiMapY.put("oulu3p", 0.47);
        miestenSijaintiMapY.put("pertsa3p", 0.47);
        miestenSijaintiMapY.put("ristivitonen3p", 0.47);
        miestenSijaintiMapY.put("tahko3p", 0.40);
        miestenSijaintiMapY.put("tahko23p", 0.45);
        miestenSijaintiMapY.put("sailytys3p", 0.45);
        miestenSijaintiMapY.put("karvauskahdella3p", 0.47);
        miestenSijaintiMapY.put("karvausyhdella3p", 0.47);
        miestenSijaintiMapY.put("muu3p", 0.30);

        miestenSijaintiMapY.put("kaannettypertsa2p", 0.40);
        miestenSijaintiMapY.put("oulu2p", 0.40);
        miestenSijaintiMapY.put("pertsa2p", 0.40);
        miestenSijaintiMapY.put("ristivitonen2p", 0.40);
        miestenSijaintiMapY.put("tahko2p", 0.525);
        miestenSijaintiMapY.put("tahko22p", 0.40);
        miestenSijaintiMapY.put("sailytys2p", 0.45);
        miestenSijaintiMapY.put("karvauskahdella2p", 0.40);
        miestenSijaintiMapY.put("karvausyhdella2p", 0.40);
        miestenSijaintiMapY.put("muu2p", 0.10);

        miestenSijaintiMapY.put("kaannettypertsa2v", 0.48);
        miestenSijaintiMapY.put("oulu2v", 0.48);
        miestenSijaintiMapY.put("pertsa2v", 0.495);
        miestenSijaintiMapY.put("ristivitonen2v", 0.48);
        miestenSijaintiMapY.put("tahko2v", 0.45);
        miestenSijaintiMapY.put("tahko22v", 0.53);
        miestenSijaintiMapY.put("sailytys2v", 0.53);
        miestenSijaintiMapY.put("karvauskahdella2v", 0.48);
        miestenSijaintiMapY.put("karvausyhdella2v", 0.48);
        miestenSijaintiMapY.put("muu2v", 0.20);

        miestenSijaintiMapY.put("kaannettypertsa3k", 0.14);
        miestenSijaintiMapY.put("oulu3k", 0.14);
        miestenSijaintiMapY.put("pertsa3k", 0.14);
        miestenSijaintiMapY.put("ristivitonen3k", 0.14);
        miestenSijaintiMapY.put("tahko3k", 0.14);
        miestenSijaintiMapY.put("tahko23k", 0.14);
        miestenSijaintiMapY.put("sailytys3k", 0.14);
        miestenSijaintiMapY.put("karvauskahdella3k", 0.14);
        miestenSijaintiMapY.put("karvausyhdella3k", 0.14);
        miestenSijaintiMapY.put("muu3k", 0.035);

        miestenSijaintiMapY.put("kaannettypertsa2k", 0.14);
        miestenSijaintiMapY.put("oulu2k", 0.14);
        miestenSijaintiMapY.put("pertsa2k", 0.14);
        miestenSijaintiMapY.put("ristivitonen2k", 0.14);
        miestenSijaintiMapY.put("tahko2k", 0.14);
        miestenSijaintiMapY.put("tahko22k", 0.14);
        miestenSijaintiMapY.put("sailytys2k", 0.14);
        miestenSijaintiMapY.put("karvauskahdella2k", 0.14);
        miestenSijaintiMapY.put("karvausyhdella2k", 0.14);
        miestenSijaintiMapY.put("muu2k", 0.035);
    }

    private void taulukkomuokkaus() {
        taulukkokuvio.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkosijainti.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkomerkki.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkosyotto.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkojakso.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkosyotto.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkojoukkue.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkolyoja.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkotyyppi.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    private void sarakkeidenmuokkaus() {
        taulukkokuvio.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setKuvio(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });
        taulukkosijainti.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setSijainti(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });


        taulukkomerkki.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setMerkki(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkosyotto.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setSyotto(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkojakso.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setJakso(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkojoukkue.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setJoukkue(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkolyoja.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setLyoja(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkotyyppi.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setTyyppi(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });
    }

    @FXML
    private void pesapallokentta() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(2.0);


        graphicsContext.strokeLine(150, 37.5, 600, 37.5); // Takaraja
        graphicsContext.strokeLine(150, 37.5, 150, 532.5); // Kolmosjatke
        graphicsContext.strokeLine(600, 37.5, 600, 465); // Kakkosjatke
        graphicsContext.strokeLine(300, 690, 435, 690); // Kotipesä

        graphicsContext.strokeLine(150, 465, 337.5, 690); // Kolmosraja


        graphicsContext.strokeLine(600, 465, 412.5, 690); // Kakkosraja
        graphicsContext.strokeLine(150, 420, 600, 420); // 2-3 väli

        //graphicsContext.strokeLine(620, 430, 600, 430);
        //graphicsContext.strokeLine(130, 430, 150, 430);

        graphicsContext.strokeLine(212, 592.5, 600, 420); // 1-2 väli

        graphicsContext.strokeLine(150, 532.5, 310, 690); // Kotijuoksuviiva

        graphicsContext.fillOval(265, 105, 10, 10); // Kolmoskoppari


        graphicsContext.fillOval(487, 105, 10, 10); // Kakkoskoppari

    }

    @FXML
    private void lyonti(MouseEvent event) {

        if (!event.getButton().equals(MouseButton.PRIMARY)) {
            return;
        }

        double mouseX = event.getX() - 2.5;
        double mouseY = event.getY() - 2.5;

        double y = mouseY / 750;

        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();
        graphicsContext.setFill(Color.RED);

        if (vierasjoukkue.isSelected() | vierasjoukkueToggle.isSelected()) {
            graphicsContext.setFill(Color.BLUE);
        }

        graphicsContext.fillOval(mouseX, mouseY, 5, 5);

        koordinaattix.setText(String.format(Locale.US, "%.3f", 1 - mouseX / 750));
        koordinaattiy.setText(String.format(Locale.US, "%.3f",mouseY / 750));

        sijaintitext.setText(haesijanti(y));

    }

    @FXML
    private String haesijanti(Double y){
        if (y >= 0.87) {
            return "etulyhyt";
        }
        if (y < 0.87 && y > 0.70) {
            return "etupitkä";
        }
        if (y <= 0.7 && y >= 0.56) {
            return "linjaetu";
        }
        if (y < 0.56 && y >= 0.35) {
            return "linjataka";
        }
        if (y < 0.35) {
            return "takakenttä";
        }
        return null;
    }

    @FXML
    private void lyontitiedot() {
        double x = Double.parseDouble(koordinaattix.getText());
        double y = Double.parseDouble(koordinaattiy.getText());

        String sijainti = haesijanti(y);

        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merrkixr = merkki.getValue();
        String syottoxr = syotto.getValue();
        String lyojaxr = lyoja.getText();
        String joukkuexr = joukkue.getText();
        String jaksoxr = jakso.getValue();
        Integer vuoroparixr = vuoropari.getValue();
        String ulkopelixr = ulkopelipaikka.getValue();
        String ulkopelivirhexr = ulkopelivirhe.getValue();
        String ulkopelisuorittjaxr = ulkopelisuorittaja.getText();
        String vaaraallaxr = vaaraalla.getValue();
        String lyontixr = lyonticombobox.getValue();
        Integer juoksutxr = juoksut.getValue();
        String lapilyontixr = lapilyonti.getValue();
        Integer lyontinumeroxr = lyontinumero.getValue();
        String ulkopelijoukkuexr = ulkopelijoukkue.getText();
        String etenijaxr = etenija.getText();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        Integer otteluID = Integer.valueOf(idottelu.getText());
        Double juoksutodennakoisyys = laskeTodennakoisyys();
        String kunnarixr = kunnari.getValue();
        String tilanne = tilannecombobox.getValue();
        String ulkopelisuoritusxr = ulkopelisuorituscombobox.getValue();

        Lyontitiedot tiedot = new Lyontitiedot(
                x, y,
                sijainti, kuvioxr, tyyppixr,
                merrkixr, syottoxr, lyojaxr,
                joukkuexr, jaksoxr, vuoroparixr,
                otteluID, ulkopelixr, ulkopelivirhexr,
                ulkopelisuorittjaxr, ulkopelisuoritusxr, vaaraallaxr, lyontixr,
                juoksutxr, lapilyontixr, lyontinumeroxr, ulkopelijoukkuexr,
                etenijaxr, etenijalaatuxr, juoksutodennakoisyys, kunnarixr, tilanne
        );

        taulukkoxr.getItems().addAll(tiedot);

    }


    @FXML
    public double laskeTodennakoisyys(){
        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        Double kuvio = miestenXrMap.get(kuvioxr);
        Double tyyppi = miestenXrMap.get(tyyppixr);
        Double merkki = miestenXrMap.get(merkkixr);
        Double etenijalaatu = miestenXrMap.get(etenijalaatuxr);
        Double ulkopelaajaxr = miestenXrMap.get(ulkopelaaja);

        double x = Double.parseDouble(koordinaattix.getText());
        double y = Double.parseDouble(koordinaattiy.getText());

        String sijainti = haesijanti(y);
        Double sijanti = miestenXrMap.get(sijainti);

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x-ulkopelaajaX,2) + Math.pow(y-ulkopelaajaY,2));

        double juoksutn = 1 / (1 + Math.exp(-(-3.17379 + kuvio + tyyppi + merkki + etenijalaatu + sijanti + ulkopelaajaxr * etaisyys)));

        juoksuodottama.setText(String.format(Locale.US, "%.3f", juoksutn));

        return juoksutn;
    }

    @FXML
    private void tallennacsvtiedostoon() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Tallenna tiedostoon");
            File tiedosto = fileChooser.showSaveDialog(null);

            if (tiedosto == null) {
                return;
            }

            FileWriter writer = new FileWriter(tiedosto.getAbsolutePath() + ".csv");

            List<Lyontitiedot> data = new ArrayList<>(taulukkoxr.getItems());

            CSVFormat format = CSVFormat.Builder.create().setHeader(
                    "otteluID", "jakso", "vuoropari",
                    "sisajoukkue",  "lyoja", "etenija", "etenijanlaatu",
                    "ulkopelijoukkue", "ulkopelisuoritus", "ulkopelisuorittaja","ulkopelipaikka", "ulkopelivirhe",
                    "koordinaatti.x", "koordinaatti.y", "kuvioxr",
                    "tyyppixr", "merkkixr", "sijaintixr", "syotto",
                    "vaaraAlla", "lyonti",
                    "juoksut", "lapilyonti", "lyontinumero", "juoksutodennakoisyys"
            ).get();

            CSVPrinter printer = format.print(writer);


            for (Lyontitiedot datum : data) {
                printer.printRecord(
                        datum.getOttelunID(),
                        datum.getJakso(),
                        datum.getVuoropari(),

                        datum.getJoukkue(),
                        datum.getLyoja(),
                        datum.getEtenija(),
                        datum.getEtenijanlaatu(),

                        datum.getUlkopelijoukkue(),
                        datum.getUlkopelisuoritus(),
                        datum.getUlkopelisuorittaja(),
                        datum.getUlkopelipaikka(),
                        datum.getUlkopelivirhe(),

                        datum.getKoordinaattix(),
                        datum.getKoordinaattiy(),

                        datum.getKuvio(),
                        datum.getTyyppi(),
                        datum.getMerkki(),
                        datum.getSijainti(),
                        datum.getSyotto(),

                        datum.getVaaraAlla(),
                        datum.getLyonti(),
                        datum.getJuoksut(),
                        datum.getLapilyonti(),
                        datum.getLyontinumero(),


                        datum.getJuoksutodennakoisyys()
                );
            }

            printer.flush();

            writer.close();
        } catch (IOException ioexception) {
            System.err.print(ioexception.getMessage());
        }
    }

    @FXML
    private JSONObject haeotteluntiedot() {
        try {
            //  + URLEncoder.encode(123, StandardCharsets.UTF_8);
            //"https://api.pesistulokset.fi/api/v1/public/match?id=127440&apikey=wRX0tTke3DZ8RLKAMntjZ81LwgNQuSN9"

            String otteluid = ottelunid.getText();

            idottelu.setText(otteluid);

            String ilma = "https://api.pesistulokset.fi/api/v1/public/match?id=" + otteluid + "&apikey=wRX0tTke3DZ8RLKAMntjZ81LwgNQuSN9";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ilma)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());

            client.close();

            return jsonObject;
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }

        return null;
    }

    @FXML
    private void kotipelaajat() {
        ObservableList<Lyojat> kotilyojatlista = FXCollections.observableArrayList();

        JSONObject jsonObject = haeotteluntiedot();

        if (jsonObject == null) {
            return;
        }

        JSONArray kotipelaajalista = jsonObject.getJSONObject("home").getJSONArray("players");

        int kotijoukkueID = jsonObject.getJSONObject("home").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("home").getString("name");

        int kotipelaajapituus = kotipelaajalista.length();

        for (int i = 0; i < kotipelaajapituus; i++) {

            JSONObject pelaaja = kotipelaajalista.getJSONObject(i);

            kotilyojatlista.add(
                    new Lyojat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            kotijoukkueID, vierasjoukkue
                    )
            );
        }
        kotilyojat.setItems(kotilyojatlista);

        kotilyojat.getSelectionModel().selectFirst();
    }

    @FXML
    private void vieraspelaajat() {
        ObservableList<Lyojat> vieraslyojatlista = FXCollections.observableArrayList();

        JSONObject jsonObject = haeotteluntiedot();

        if (jsonObject == null) {
            return;
        }

        JSONArray vieraspelaajalista = jsonObject.getJSONObject("away").getJSONArray("players");

        int vierasjoukkueID = jsonObject.getJSONObject("away").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("away").getString("name");

        int vieraspelaajapituus = vieraspelaajalista.length();

        for (int i = 0; i < vieraspelaajapituus; i++) {

            JSONObject pelaaja = vieraspelaajalista.getJSONObject(i);

            vieraslyojatlista.add(
                    new Lyojat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            vierasjoukkueID, vierasjoukkue
                    )
            );
        }
        vieraslyojat.setItems(vieraslyojatlista);

        vieraslyojat.getSelectionModel().selectFirst();

    }

    @FXML
    private void kotilyoja(){
        if (kotilyojat.getValue() == null){
            return;
        }
        lyoja.setText(kotilyojat.getValue().getNimi());
        joukkue.setText(kotilyojat.getValue().getJoukkue());
        sisajoukkeuid.setText(String.valueOf(kotilyojat.getValue().getJoukkueID()));
    }

    @FXML
    private void vieraslyoja(){
        if (vieraslyojat.getValue() == null){
            return;
        }

        lyoja.setText(vieraslyojat.getValue().getNimi());
        joukkue.setText(vieraslyojat.getValue().getJoukkue());
        sisajoukkeuid.setText(String.valueOf(vieraslyojat.getValue().getJoukkueID()));
    }

    @FXML
    private void kotiulkopelaaja(){
        if (kotiulkopelaajat.getValue() == null){
            return;
        }
        ulkopelisuorittaja.setText(kotiulkopelaajat.getValue().getNimi());
        ulkopelijoukkue.setText(kotiulkopelaajat.getValue().getJoukkue());
        ulkojoukkueid.setText(String.valueOf(kotiulkopelaajat.getValue().getJoukkueID()));
    }

    @FXML
    private void vierasulkopelaaja(){
        if (vierasulkopelaajat.getValue() == null){
            return;
        }

        ulkopelisuorittaja.setText(vierasulkopelaajat.getValue().getNimi());
        ulkopelijoukkue.setText(vierasulkopelaajat.getValue().getJoukkue());
        ulkojoukkueid.setText(String.valueOf(vierasulkopelaajat.getValue().getJoukkueID()));

    }

    @FXML
    private void kotietenijalisays(){
        if (vierasulkopelaajat.getValue() == null){
            return;
        }

        etenija.setText(kotietenija.getValue().getNimi());
    }

    @FXML
    private void haepelaajat(){
        idottelu.setText(ottelunid.getText());
        kotipelaajat();
        vieraspelaajat();
        haepelaajatulkopelaajat();
        kotipelaajatetenijat();
        vieraspelaajaetenijat();
    }

    @FXML
    private void kotipelaajatulkopelaajat() {
        ObservableList<Lyojat> kotilyojatlista = FXCollections.observableArrayList();

        JSONObject jsonObject = haeotteluntiedot();

        if (jsonObject == null) {
            return;
        }

        JSONArray kotipelaajalista = jsonObject.getJSONObject("home").getJSONArray("players");


        String vierasjoukkue = jsonObject.getJSONObject("home").getString("name");

        int kotijoukkueID = jsonObject.getJSONObject("home").getInt("sport_club_id");

        int kotipelaajapituus = kotipelaajalista.length();

        for (int i = 0; i < kotipelaajapituus; i++) {

            JSONObject pelaaja = kotipelaajalista.getJSONObject(i);

            kotilyojatlista.add(
                    new Lyojat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            kotijoukkueID, vierasjoukkue
                    )
            );
        }
        kotiulkopelaajat.setItems(kotilyojatlista);

        kotiulkopelaajat.getSelectionModel().selectFirst();
    }

    @FXML
    private void kotipelaajatetenijat() {
        ObservableList<Lyojat> kotilyojatlista = FXCollections.observableArrayList();

        JSONObject jsonObject = haeotteluntiedot();

        if (jsonObject == null) {
            return;
        }

        JSONArray kotipelaajalista = jsonObject.getJSONObject("home").getJSONArray("players");

        String vierasjoukkue = jsonObject.getJSONObject("home").getString("name");

        int kotijoukkueID = jsonObject.getJSONObject("home").getInt("sport_club_id");

        int kotipelaajapituus = kotipelaajalista.length();

        for (int i = 0; i < kotipelaajapituus; i++) {

            JSONObject pelaaja = kotipelaajalista.getJSONObject(i);

            kotilyojatlista.add(
                    new Lyojat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            kotijoukkueID, vierasjoukkue
                    )
            );
        }
        kotietenija.setItems(kotilyojatlista);

        kotietenija.getSelectionModel().selectFirst();
    }

    @FXML
    private void vieraspelaajatulkopelaajat() {
        ObservableList<Lyojat> vieraslyojatlista = FXCollections.observableArrayList();

        JSONObject jsonObject = haeotteluntiedot();

        if (jsonObject == null) {
            return;
        }

        JSONArray vieraspelaajalista = jsonObject.getJSONObject("away").getJSONArray("players");

        int kotijoukkueID = jsonObject.getJSONObject("away").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("away").getString("name");

        int vieraspelaajapituus = vieraspelaajalista.length();

        for (int i = 0; i < vieraspelaajapituus; i++) {

            JSONObject pelaaja = vieraspelaajalista.getJSONObject(i);

            vieraslyojatlista.add(
                    new Lyojat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            kotijoukkueID, vierasjoukkue
                    )
            );
        }

        vierasulkopelaajat.setItems(vieraslyojatlista);

        vierasulkopelaajat.getSelectionModel().selectFirst();

    }

    @FXML
    private void vieraspelaajaetenijat() {
        ObservableList<Lyojat> vieraslyojatlista = FXCollections.observableArrayList();

        JSONObject jsonObject = haeotteluntiedot();

        if (jsonObject == null) {
            return;
        }

        JSONArray vieraspelaajalista = jsonObject.getJSONObject("away").getJSONArray("players");

        int kotijoukkueID = jsonObject.getJSONObject("away").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("away").getString("name");

        int vieraspelaajapituus = vieraspelaajalista.length();

        for (int i = 0; i < vieraspelaajapituus; i++) {

            JSONObject pelaaja = vieraspelaajalista.getJSONObject(i);

            vieraslyojatlista.add(
                    new Lyojat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            kotijoukkueID, vierasjoukkue
                    )
            );
        }

        vierasetenija.setItems(vieraslyojatlista);

        vierasetenija.getSelectionModel().selectFirst();

    }
    @FXML
    private void haepelaajatulkopelaajat(){
        kotipelaajatulkopelaajat();
        vieraspelaajatulkopelaajat();
    }

    @FXML
    private void poistarivi(){

        Lyontitiedot poistettava = taulukkoxr.getSelectionModel().getSelectedItem();

        taulukkoxr.getItems().remove(poistettava);

    }


}