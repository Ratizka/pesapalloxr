package com.example.pesapalloxr;

import javafx.application.Platform;
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
    private final Map<String, Double> miestenXLapiMap = new HashMap<>();
    private final Map<String, Double> naistenXrMap = new HashMap<>();
    private final Map<String, Double> SijaintiMapX = new HashMap<>();
    private final Map<String, Double> SijaintiMapY = new HashMap<>();
    public TextField xmetri;
    public TextField ymetri;
    public TextField lapilyontiolettama;
    private int otteluID;
    @FXML private ComboBox<String> saumakorkeuscombobox;
    @FXML private ComboBox<String> ulkopelitempocombobox;

    @FXML private ComboBox<String> ulkopelijoukkuecombobox;
    @FXML private ComboBox<String> sisajoukkuecombobox;
    @FXML private TableColumn<Lyontitiedot, String> taulukkoTilanne;
    @FXML private RadioMenuItem vierasjoukkueToggle;
    @FXML
    private RadioMenuItem menuItemMiehet;
    @FXML
    private RadioMenuItem menuItemNaiset;

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
        pelaajienSijaintiLisays();
        miestensijantiy();
        miestenXlapilyontiMap();
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

        saumakorkeuscombobox.getItems().addAll("", "maa","ilma");
        saumakorkeuscombobox.getSelectionModel().selectFirst();

        ulkopelitempocombobox.getItems().addAll("pysäytys", "ali", "peli", "yli");
        ulkopelitempocombobox.getSelectionModel().selectFirst();
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

    private void miestenXlapilyontiMap(){
        miestenXLapiMap.put("msualkuarvo", -20.23931974);
        miestenXLapiMap.put("kumura", 0.89692858);
        miestenXLapiMap.put("nappi", -15.67192285);
        miestenXLapiMap.put("pomppu", -0.85974399);
        miestenXLapiMap.put("pussari", -15.28975029);
        miestenXLapiMap.put("pystari/viisto", 0.42902384);
        miestenXLapiMap.put("vaakamaila", 1.09875118);
        miestenXLapiMap.put("varsi", -15.6036073);
        miestenXLapiMap.put("koppi", 0.000);

        miestenXLapiMap.put("karvauskahdella", -18.16454414);
        miestenXLapiMap.put("karvausyhdella", 0.13573397);
        miestenXLapiMap.put("oulu", -0.77576714);
        miestenXLapiMap.put("pertsa", -0.55334101);
        miestenXLapiMap.put("ristivitonen", -0.107884802);
        miestenXLapiMap.put("sailytys", 0.13665726);
        miestenXLapiMap.put("tahko", -1.27498680);
        miestenXLapiMap.put("tahko2", -0.48639065);
        miestenXLapiMap.put("kaannettypertsa", 0.000);
        miestenXLapiMap.put("muu", -10.0);

        miestenXLapiMap.put("vääräpois", 0.000);
        miestenXLapiMap.put("kulma", 0.000);
        miestenXLapiMap.put("lento", -2.12432265);
        miestenXLapiMap.put("vapaa", 0.98174228);
        miestenXLapiMap.put("etulyhyt", 0.000);
        miestenXLapiMap.put("etupitkä", 15.13486023);
        miestenXLapiMap.put("linjaetu", 16.87343891);
        miestenXLapiMap.put("linjataka", 17.10900609);
        miestenXLapiMap.put("takakenttä", 16.30968012);
        miestenXLapiMap.put("erinomainen", 0.02941683);
        miestenXLapiMap.put("huono", -0.04073751);
        miestenXLapiMap.put("hyvä", -0.22099589);
        miestenXLapiMap.put("keskiverto", -0.19710496);
        miestenXLapiMap.put("heikko", 0.000);

        miestenXLapiMap.put("2k", 2.04641622);
        miestenXLapiMap.put("3k", 2.30121352);
        miestenXLapiMap.put("2p", -26.23246982);
        miestenXLapiMap.put("2v", 0.47406555);
        miestenXLapiMap.put("3p", -20.02194498);
        miestenXLapiMap.put("3v", 13.7784512);
        miestenXLapiMap.put("l", 53.56879673);
        miestenXLapiMap.put("S", 13.71007295);
        miestenXLapiMap.put("1v", -92.02236471);

    }

    private void pelaajienSijaintiLisays(){
        SijaintiMapX.put("kaannettypertsal", 0.50);
        SijaintiMapX.put("oulul", 0.50);
        SijaintiMapX.put("pertsal", 0.50);
        SijaintiMapX.put("ristivitonenl", 0.50);
        SijaintiMapX.put("tahkol", 0.50);
        SijaintiMapX.put("tahko2l", 0.50);
        SijaintiMapX.put("sailytysl", 0.50);
        SijaintiMapX.put("karvauskahdellal", 0.50);
        SijaintiMapX.put("karvausyhdellal", 0.50);
        SijaintiMapX.put("muul", 0.50);

        SijaintiMapX.put("kaannettypertsa3v", 0.75);
        SijaintiMapX.put("oulu3v", 0.80);
        SijaintiMapX.put("pertsa3v", 0.765);
        SijaintiMapX.put("ristivitonen3v", 0.77);
        SijaintiMapX.put("tahko3v", 0.80);
        SijaintiMapX.put("tahko23v", 0.755);
        SijaintiMapX.put("sailytys3v", 0.80);
        SijaintiMapX.put("karvauskahdella3v", 0.80);
        SijaintiMapX.put("karvausyhdella3v", 0.80);
        SijaintiMapX.put("muu3v", 0.80);

        SijaintiMapX.put("kaannettypertsa1v", 0.41);
        SijaintiMapX.put("oulu1v", 0.52);
        SijaintiMapX.put("pertsa1v", 0.53);
        SijaintiMapX.put("ristivitonen1v", 0.425);
        SijaintiMapX.put("tahko1v", 0.60);
        SijaintiMapX.put("tahko21v", 0.55);
        SijaintiMapX.put("sailytys1v", 0.62);
        SijaintiMapX.put("karvauskahdella1v", 0.63);
        SijaintiMapX.put("karvausyhdella1v", 0.63);
        SijaintiMapX.put("muu1v", 0.20);


        SijaintiMapX.put("kaannettypertsaS", 0.25);
        SijaintiMapX.put("ouluS", 0.22);
        SijaintiMapX.put("pertsaS", 0.235);
        SijaintiMapX.put("ristivitonenS", 0.20);
        SijaintiMapX.put("tahkoS", 0.235);
        SijaintiMapX.put("tahko2S", 0.34);
        SijaintiMapX.put("sailytysS", 0.385);
        SijaintiMapX.put("karvauskahdellaS", 0.33);
        SijaintiMapX.put("karvausyhdellaS", 0.22);
        SijaintiMapX.put("muuS", 0.20);


        SijaintiMapX.put("kaannettypertsa3p", 0.68);
        SijaintiMapX.put("oulu3p", 0.68);
        SijaintiMapX.put("pertsa3p", 0.68);
        SijaintiMapX.put("ristivitonen3p", 0.68);
        SijaintiMapX.put("tahko3p", 0.535);
        SijaintiMapX.put("tahko23p", 0.68);
        SijaintiMapX.put("sailytys3p", 0.58);
        SijaintiMapX.put("karvauskahdella3p", 0.68);
        SijaintiMapX.put("karvausyhdella3p", 0.68);
        SijaintiMapX.put("muu3p", 0.80);

        SijaintiMapX.put("kaannettypertsa2p", 0.52);
        SijaintiMapX.put("oulu2p", 0.43);
        SijaintiMapX.put("pertsa2p", 0.43);
        SijaintiMapX.put("ristivitonen2p", 0.525);
        SijaintiMapX.put("tahko2p", 0.425);
        SijaintiMapX.put("tahko22p", 0.42);
        SijaintiMapX.put("sailytys2p", 0.43);
        SijaintiMapX.put("karvauskahdella2p", 0.43);
        SijaintiMapX.put("karvausyhdella2p", 0.43);
        SijaintiMapX.put("muu2p", 0.50);

        SijaintiMapX.put("kaannettypertsa2v", 0.30);
        SijaintiMapX.put("oulu2v", 0.30);
        SijaintiMapX.put("pertsa2v", 0.30);
        SijaintiMapX.put("ristivitonen2v", 0.30);
        SijaintiMapX.put("tahko2v", 0.30);
        SijaintiMapX.put("tahko22v", 0.20);
        SijaintiMapX.put("sailytys2v", 0.30);
        SijaintiMapX.put("karvauskahdella2v", 0.30);
        SijaintiMapX.put("karvausyhdella2v", 0.30);
        SijaintiMapX.put("muu2v", 0.20);

        SijaintiMapX.put("kaannettypertsa3k", 0.65);
        SijaintiMapX.put("oulu3k", 0.65);
        SijaintiMapX.put("pertsa3k", 0.65);
        SijaintiMapX.put("ristivitonen3k", 0.65);
        SijaintiMapX.put("tahko3k", 0.65);
        SijaintiMapX.put("tahko23k", 0.65);
        SijaintiMapX.put("sailytys3k", 0.65);
        SijaintiMapX.put("karvauskahdella3k", 0.65);
        SijaintiMapX.put("karvausyhdella3k", 0.65);
        SijaintiMapX.put("muu3k", 0.70);

        SijaintiMapX.put("kaannettypertsa2k", 0.35);
        SijaintiMapX.put("oulu2k", 0.35);
        SijaintiMapX.put("pertsa2k", 0.35);
        SijaintiMapX.put("ristivitonen2k", 0.35);
        SijaintiMapX.put("tahko2k", 0.35);
        SijaintiMapX.put("tahko22k", 0.35);
        SijaintiMapX.put("sailytys2k", 0.35);
        SijaintiMapX.put("karvauskahdella2k", 0.35);
        SijaintiMapX.put("karvausyhdella2k", 0.35);
        SijaintiMapX.put("muu2k", 0.30);
    }

    private void miestensijantiy(){
        SijaintiMapY.put("kaannettypertsal", 0.93);
        SijaintiMapY.put("oulul", 0.93);
        SijaintiMapY.put("pertsal", 0.93);
        SijaintiMapY.put("ristivitonenl", 0.93);
        SijaintiMapY.put("tahkol", 0.93);
        SijaintiMapY.put("tahko2l", 0.93);
        SijaintiMapY.put("sailytysl", 0.93);
        SijaintiMapY.put("karvauskahdellal", 0.93);
        SijaintiMapY.put("karvausyhdellal", 0.93);
        SijaintiMapY.put("muul", 0.93);

        SijaintiMapY.put("kaannettypertsa3v", 0.60);
        SijaintiMapY.put("oulu3v", 0.56);
        SijaintiMapY.put("pertsa3v", 0.61);
        SijaintiMapY.put("ristivitonen3v", 0.585);
        SijaintiMapY.put("tahko3v", 0.52);
        SijaintiMapY.put("tahko23v", 0.61);
        SijaintiMapY.put("sailytys3v", 0.54);
        SijaintiMapY.put("karvauskahdella3v", 0.56);
        SijaintiMapY.put("karvausyhdella3v", 0.56);
        SijaintiMapY.put("muu3v", 0.54);

        SijaintiMapY.put("kaannettypertsa1v", 0.55);
        SijaintiMapY.put("oulu1v", 0.68);
        SijaintiMapY.put("pertsa1v", 0.56);
        SijaintiMapY.put("ristivitonen1v", 0.68);
        SijaintiMapY.put("tahko1v", 0.66);
        SijaintiMapY.put("tahko21v", 0.52);
        SijaintiMapY.put("sailytys1v", 0.68);
        SijaintiMapY.put("karvauskahdella1v", 0.75);
        SijaintiMapY.put("karvausyhdella1v", 0.75);
        SijaintiMapY.put("muu1v", 0.20);


        SijaintiMapY.put("kaannettypertsaS", 0.60);
        SijaintiMapY.put("ouluS", 0.58);
        SijaintiMapY.put("pertsaS", 0.61);
        SijaintiMapY.put("ristivitonenS", 0.56);
        SijaintiMapY.put("tahkoS", 0.58);
        SijaintiMapY.put("tahko2S", 0.645);
        SijaintiMapY.put("sailytysS", 0.68);
        SijaintiMapY.put("karvauskahdellaS", 0.70);
        SijaintiMapY.put("karvausyhdellaS", 0.58);
        SijaintiMapY.put("muuS", 0.54);


        SijaintiMapY.put("kaannettypertsa3p", 0.47);
        SijaintiMapY.put("oulu3p", 0.47);
        SijaintiMapY.put("pertsa3p", 0.47);
        SijaintiMapY.put("ristivitonen3p", 0.47);
        SijaintiMapY.put("tahko3p", 0.40);
        SijaintiMapY.put("tahko23p", 0.45);
        SijaintiMapY.put("sailytys3p", 0.45);
        SijaintiMapY.put("karvauskahdella3p", 0.47);
        SijaintiMapY.put("karvausyhdella3p", 0.47);
        SijaintiMapY.put("muu3p", 0.30);

        SijaintiMapY.put("kaannettypertsa2p", 0.40);
        SijaintiMapY.put("oulu2p", 0.40);
        SijaintiMapY.put("pertsa2p", 0.40);
        SijaintiMapY.put("ristivitonen2p", 0.40);
        SijaintiMapY.put("tahko2p", 0.525);
        SijaintiMapY.put("tahko22p", 0.40);
        SijaintiMapY.put("sailytys2p", 0.45);
        SijaintiMapY.put("karvauskahdella2p", 0.40);
        SijaintiMapY.put("karvausyhdella2p", 0.40);
        SijaintiMapY.put("muu2p", 0.10);

        SijaintiMapY.put("kaannettypertsa2v", 0.48);
        SijaintiMapY.put("oulu2v", 0.48);
        SijaintiMapY.put("pertsa2v", 0.495);
        SijaintiMapY.put("ristivitonen2v", 0.48);
        SijaintiMapY.put("tahko2v", 0.45);
        SijaintiMapY.put("tahko22v", 0.53);
        SijaintiMapY.put("sailytys2v", 0.53);
        SijaintiMapY.put("karvauskahdella2v", 0.48);
        SijaintiMapY.put("karvausyhdella2v", 0.48);
        SijaintiMapY.put("muu2v", 0.20);

        SijaintiMapY.put("kaannettypertsa3k", 0.14);
        SijaintiMapY.put("oulu3k", 0.14);
        SijaintiMapY.put("pertsa3k", 0.14);
        SijaintiMapY.put("ristivitonen3k", 0.14);
        SijaintiMapY.put("tahko3k", 0.14);
        SijaintiMapY.put("tahko23k", 0.14);
        SijaintiMapY.put("sailytys3k", 0.14);
        SijaintiMapY.put("karvauskahdella3k", 0.14);
        SijaintiMapY.put("karvausyhdella3k", 0.14);
        SijaintiMapY.put("muu3k", 0.035);

        SijaintiMapY.put("kaannettypertsa2k", 0.14);
        SijaintiMapY.put("oulu2k", 0.14);
        SijaintiMapY.put("pertsa2k", 0.14);
        SijaintiMapY.put("ristivitonen2k", 0.14);
        SijaintiMapY.put("tahko2k", 0.14);
        SijaintiMapY.put("tahko22k", 0.14);
        SijaintiMapY.put("sailytys2k", 0.14);
        SijaintiMapY.put("karvauskahdella2k", 0.14);
        SijaintiMapY.put("karvausyhdella2k", 0.14);
        SijaintiMapY.put("muu2k", 0.035);
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
        graphicsContext.strokeLine(150, 37.5, 150, 550); // Kolmosjatke
        graphicsContext.strokeLine(600, 37.5, 600, 465); // Kakkosjatke
        graphicsContext.strokeLine(280, 690, 445, 690); // Kotipesä

        graphicsContext.strokeLine(150, 465, 337.5, 690); // Kolmosraja


        graphicsContext.strokeLine(600, 465, 412.5, 690); // Kakkosraja
        graphicsContext.strokeLine(150, 420, 600, 420); // 2-3 väli

        //graphicsContext.strokeLine(620, 430, 600, 430);
        //graphicsContext.strokeLine(130, 430, 150, 430);

        graphicsContext.strokeLine(197, 592.5, 600, 420); // 1-2 väli

        graphicsContext.strokeLine(150, 550, 300, 690); // Kotijuoksuviiva

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

        double xMetri = event.getX();
        double yMetri = event.getY();

        double y = mouseY / 750;

        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();
        graphicsContext.setFill(Color.RED);

        if (vierasjoukkue.isSelected() | vierasjoukkueToggle.isSelected()) {
            graphicsContext.setFill(Color.BLUE);
        }

        //System.out.print((event.getX()/150-1)*12 + "\n");
        //System.out.print((event.getY()/690-1)*-86.71265+ "\n" );

        graphicsContext.fillOval(mouseX, mouseY, 5, 5);

        koordinaattix.setText(String.format(Locale.US, "%.3f", 1 - mouseX / 750));
        koordinaattiy.setText(String.format(Locale.US, "%.3f",y));

        sijaintitext.setText(haesijanti(y));

        if (menuItemMiehet.isSelected()){
            lyonninSijaintiMetreissaMiestenRajat(xMetri, yMetri);
        }else{
            lyonninSijaintiMetreissaNaistenRajat(xMetri, yMetri);
        }

    }

    @FXML
    private void lyonninSijaintiMetreissaMiestenRajat(double x, double y){
        double xLaskettu = (x/150-1)*14;
        double yLaskettu = (y/690-1)*-101.52;

        xmetri.setText(String.format(Locale.US, "%.2f",xLaskettu));
        ymetri.setText(String.format(Locale.US, "%.2f",yLaskettu));

    }

    @FXML
    private void lyonninSijaintiMetreissaNaistenRajat(double x, double y){
        double xLaskettu = (x/150-1)*12;
        double yLaskettu = (y/690-1)*-86.71265;

        xmetri.setText(String.format(Locale.US, "%.2f",xLaskettu));
        ymetri.setText(String.format(Locale.US, "%.2f",yLaskettu));

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
        //Integer otteluID = Integer.valueOf(idottelu.getText());
        Double juoksutodennakoisyys = laskeJuoksuTodennakoisyysMiehet();
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

        laskeTodennakoisyysLapi();

    }

    @FXML
    private void laskeTodennakoisyydet(){
        laskeJuoksuTodennakoisyysMiehet();
        laskeTodennakoisyysLapi();
    }


    @FXML
    private double laskeJuoksuTodennakoisyysMiehet(){
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

        Double ulkopelaajaX = SijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = SijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x-ulkopelaajaX,2) + Math.pow(y-ulkopelaajaY,2));

        double juoksutn = 1 / (1 + Math.exp(-(-3.17379 + kuvio + tyyppi + merkki + etenijalaatu + sijanti + ulkopelaajaxr * etaisyys)));

        juoksuodottama.setText(String.format(Locale.US, "%.3f", juoksutn));

        laskeTodennakoisyysLapi();

        return juoksutn;
    }

    @FXML
    private void laskeTodennakoisyysLapi(){
        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        Double kuvio = miestenXLapiMap.get(kuvioxr);
        Double tyyppi = miestenXLapiMap.get(tyyppixr);
        Double merkki = miestenXLapiMap.get(merkkixr);
        Double etenijalaatu = miestenXLapiMap.get(etenijalaatuxr);
        Double ulkopelaajaxr = miestenXLapiMap.get(ulkopelaaja);

        double x = Double.parseDouble(koordinaattix.getText());
        double y = Double.parseDouble(koordinaattiy.getText());

        String sijainti = haesijanti(y);
        Double sijanti = miestenXLapiMap.get(sijainti);

        Double ulkopelaajaX = SijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = SijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x-ulkopelaajaX,2) + Math.pow(y-ulkopelaajaY,2));

        double juoksutn = 1 / (1 + Math.exp(-(miestenXLapiMap.get("msualkuarvo") + kuvio + tyyppi + merkki + etenijalaatu + sijanti + ulkopelaajaxr * etaisyys)));

        lapilyontiolettama.setText(String.format(Locale.US, "%.3f", juoksutn));

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
            String otteluid = ottelunid.getText();

            String ilma = "https://api.pesistulokset.fi/api/v1/public/match?id=" + otteluid + "&apikey=wRX0tTke3DZ8RLKAMntjZ81LwgNQuSN9";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ilma)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());

            client.close();

            if (jsonObject.has("error")) {
                return null;
            }

            otteluID = Integer.parseInt(ottelunid.getText());

            return jsonObject;
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }

        return null;
    }

    public class HaeOtteluThread extends Thread{
        @Override
        public void run() {
            JSONObject data = haeotteluntiedot();

            if (data == null) {
                return;
            }
            pelaajalisays(data);
        }
    }

    @FXML
    private void haepelaajat(){
        String otteluid = ottelunid.getText();

        if (otteluid.isEmpty()) {
            return;
        }

        Thread t = new HaeOtteluThread();
        t.start();
    }

    private void pelaajalisays(JSONObject jsonObject) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if ((jsonObject != null && jsonObject.has("error")) | jsonObject == null) {
                    return;
                }
                kotipelaajat(jsonObject);
                vieraspelaajat(jsonObject);
                kotipelaajatulkopelaajat(jsonObject);
                vieraspelaajatulkopelaajat(jsonObject);
                kotipelaajatetenijat(jsonObject);
                vieraspelaajaetenijat(jsonObject);
            }
        });
    }

    @FXML
    private void kotipelaajat(JSONObject jsonObject) {
        ObservableList<Lyojat> kotilyojatlista = FXCollections.observableArrayList();

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
    private void vieraspelaajat(JSONObject jsonObject) {
        ObservableList<Lyojat> vieraslyojatlista = FXCollections.observableArrayList();

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
    private void kotipelaajatulkopelaajat(JSONObject jsonObject) {
        ObservableList<Lyojat> kotilyojatlista = FXCollections.observableArrayList();

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
    private void kotipelaajatetenijat(JSONObject jsonObject) {
        ObservableList<Lyojat> kotilyojatlista = FXCollections.observableArrayList();

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
    private void vieraspelaajatulkopelaajat(JSONObject jsonObject) {
        ObservableList<Lyojat> vieraslyojatlista = FXCollections.observableArrayList();

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
    private void vieraspelaajaetenijat(JSONObject jsonObject) {
        ObservableList<Lyojat> vieraslyojatlista = FXCollections.observableArrayList();

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
        if (kotietenija.getValue() == null){
            return;
        }

        etenija.setText(kotietenija.getValue().getNimi());
    }

    @FXML
    private void vierasetenijalisays(){
        if (vierasetenija.getValue() == null){
            return;
        }

        etenija.setText(vierasetenija.getValue().getNimi());
    }


    @FXML
    private void poistarivi(){
        Lyontitiedot poistettava = taulukkoxr.getSelectionModel().getSelectedItem();

        taulukkoxr.getItems().remove(poistettava);

    }


}