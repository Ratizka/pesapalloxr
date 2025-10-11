package com.example.pesapalloxr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.util.converter.IntegerStringConverter;
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

    private static int OTTELUID = 1;
    private final Map<String, Double> miestenXrMap = new HashMap<>();
    private final Map<String, Double> miestenXLapiMap = new HashMap<>();
    private final Map<String, Double> naistenXrMap = new HashMap<>();
    private final Map<String, Double> miestenSijaintiMapX = new HashMap<>();
    private final Map<String, Double> miestenSijaintiMapY = new HashMap<>();

    @FXML private ComboBox<String> kumuranTyyppi;
    @FXML private ComboBox<Integer> palojenMaara;
    @FXML private Tooltip tempoToolTip;
    @FXML
    private TextField lyonninEtaisyysulkopelaajasta;
    @FXML
    private TextField lapilyontiolettama;
    @FXML
    private ComboBox<String> saumakorkeuscombobox;
    @FXML
    private ComboBox<String> ulkopelitempocombobox;
    @FXML
    private TableColumn<Lyontitiedot, String> taulukkoTilanne;
    @FXML
    private RadioMenuItem vierasjoukkueToggle;
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
    private ComboBox<Pelaajat> kotietenija;
    @FXML
    private ComboBox<Pelaajat> vierasetenija;
    @FXML
    private ComboBox<String> ulkopelivirhe;
    @FXML
    private ComboBox<Integer> juoksut;
    @FXML
    private ComboBox<String> ulkopelipaikka;
    @FXML
    private ComboBox<Pelaajat> kotilyojat;
    @FXML
    private ComboBox<Pelaajat> vieraslyojat;
    @FXML
    private TextField ottelunid;
    @FXML
    private ComboBox<Pelaajat> kotiulkopelaajat;
    @FXML
    private ComboBox<Pelaajat> vierasulkopelaajat;
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
    private void sulje() {
        System.exit(0);
    }

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

    private void valikot() {

        tempoToolTip.setText("""
                Ulkopelisuorituksen tempo\s
                - Pysäytys: Pelaaja pysäyttää pallon\s
                - Ali: Normaalia suoritusta hitaampi \s
                - Peli: Normaali suoritus \s
                - Yli: Normaalia suoritusta kovempi\s
                ja virheen mahdollisuus on suuri
                """);
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

        kumuranTyyppi.getItems().addAll("","matala", "korkea");
        kumuranTyyppi.getSelectionModel().selectFirst();

        palojenMaara.getItems().addAll(0,1,2);
        palojenMaara.getSelectionModel().selectFirst();

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

        vuoropari.getItems().addAll(1, 2, 3, 4);
        vuoropari.getSelectionModel().selectFirst();

        vaaraalla.getItems().addAll("ei", "kyllä");
        vaaraalla.getSelectionModel().selectFirst();

        lapilyonti.getItems().addAll("ei", "kyllä");
        lapilyonti.getSelectionModel().selectFirst();

        lyontinumero.getItems().addAll(1, 2, 3);
        lyontinumero.getSelectionModel().selectFirst();

        kunnari.getItems().addAll("ei", "kyllä");
        kunnari.getSelectionModel().selectFirst();

        lyontisuuntacombobox.getItems().addAll(
                "3-raja", "3-luukku", "3-sauma", "3-pussi", "3-kolmosjatke", "3-koppari",
                "keskitakanen", "keskisauma", "keskipussi", "keskikentta",
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

        saumakorkeuscombobox.getItems().addAll("", "maa", "ilma");
        saumakorkeuscombobox.getSelectionModel().selectFirst();

        ulkopelitempocombobox.getItems().addAll("pysäytys", "ali", "peli", "yli");
        ulkopelitempocombobox.getSelectionModel().selectFirst();


    }

    @FXML
    private void taulukkoValinta(){

        taulukkoxr.getSelectionModel().clearSelection();

    }

    private void taulukontiedot() {

        taulukkoxr.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ContextMenu taulukkoContextMenu = new ContextMenu();
        MenuItem taulukkopoista = new MenuItem("Poista");

        taulukkoContextMenu.getItems().addAll(taulukkopoista);

        taulukkoxr.setContextMenu(taulukkoContextMenu);

        taulukkoxr.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE) & (taulukkoxr.getSelectionModel() != null)) {
                poistarivi();
            }
        });

        taulukkoxr.setOnContextMenuRequested(event -> {
            taulukkoContextMenu.show(taulukkoxr, event.getScreenX(), event.getScreenY());
        });

        taulukkopoista.setOnAction(event -> {
            if (taulukkoxr.getSelectionModel() != null) {
                poistarivi();
            }
        });

        taulukkoContextMenu.setOnHidden(windowEvent -> taulukkoValinta());


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

    private void taulukkomuokkaus() {
        taulukkokuvio.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkovuoropari.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        taulukkojuoksut.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        taulukkosijainti.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkomerkki.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkosyotto.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkojakso.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkosyotto.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkojoukkue.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkolyoja.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkotyyppi.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkoetenija.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkoEtenijaLaatu.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkoulkopelijoukkue.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkoulkopelaaja.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkoulkopelipaikka.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkoTilanne.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkovaaraalla.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkolyontinumero.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        taulukkolyonti.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkoulkopelivirhe.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkolapilyonti.setCellFactory(TextFieldTableCell.forTableColumn());
        taulukkokunnari.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void sarakkeidenmuokkaus() {

        taulukkojakso.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get(lyontitiedotStringCellEditEvent.getTablePosition().getRow())
                        .setJakso(lyontitiedotStringCellEditEvent.getNewValue());
            }
        });

        taulukkovuoropari.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, Integer> lyontitiedotIntegerCellEditEvent) {
                lyontitiedotIntegerCellEditEvent.getTableView().getItems().get(
                                lyontitiedotIntegerCellEditEvent.getTablePosition().getRow()).
                        setVuoropari(lyontitiedotIntegerCellEditEvent.getNewValue());
            }
        });

        taulukkolyontinumero.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, Integer> lyontitiedotIntegerCellEditEvent) {
                lyontitiedotIntegerCellEditEvent.getTableView().getItems().get(
                                lyontitiedotIntegerCellEditEvent.getTablePosition().getRow()).
                        setLyontinumero(lyontitiedotIntegerCellEditEvent.getNewValue());
            }
        });

        taulukkojuoksut.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, Integer> lyontitiedotIntegerCellEditEvent) {
                lyontitiedotIntegerCellEditEvent.getTableView().getItems().get(
                                lyontitiedotIntegerCellEditEvent.getTablePosition().getRow()).
                        setJuoksut(lyontitiedotIntegerCellEditEvent.getNewValue());
            }
        });

        taulukkokuvio.setOnEditCommit(new EventHandler<>() {
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

        taulukkolyonti.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setLyonti(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkoetenija.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setEtenija(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkoulkopelijoukkue.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setUlkopelijoukkue(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkoulkopelaaja.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setUlkopelisuorittaja(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkovaaraalla.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setVaaraAlla(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkoulkopelipaikka.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setUlkopelipaikka(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkoEtenijaLaatu.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setEtenijanlaatu(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkoEtenijaLaatu.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()
                                ).
                        setEtenijanlaatu(
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

        taulukkoulkopelivirhe.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setUlkopelivirhe(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkolapilyonti.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setLapilyonti(
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

        taulukkokunnari.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lyontitiedot, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setKunnari(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });



    }

    private void xrmap() {
        miestenXrMap.put("msualkuarvo", -3.49788505876296);
        miestenXrMap.put("kumura", 0.138493681879777);
        miestenXrMap.put("nappi", 0.449713987337941);
        miestenXrMap.put("pomppu", 0.631354536610227);
        miestenXrMap.put("pussari", 0.289068745982117);
        miestenXrMap.put("pystari/viisto", -0.136153089262216);
        miestenXrMap.put("vaakamaila", 0.28372832947388);
        miestenXrMap.put("varsi", 0.265230670979336);
        miestenXrMap.put("koppi", 0.000);

        miestenXrMap.put("karvauskahdella", 0.400668587033655);
        miestenXrMap.put("karvausyhdella", 0.424602918287331);
        miestenXrMap.put("oulu", 0.016751881020792);
        miestenXrMap.put("pertsa", -0.244889226583379);
        miestenXrMap.put("ristivitonen", 0.539966024602556);
        miestenXrMap.put("sailytys", -0.899362559863791);
        miestenXrMap.put("tahko", -0.291482943608297);
        miestenXrMap.put("tahko2", -0.0396682967096823);
        miestenXrMap.put("kaannettypertsa", 0.000);
        miestenXrMap.put("muu", 0.75);

        miestenXrMap.put("vääräpois", 0.000);
        miestenXrMap.put("kulma", 0.050);
        miestenXrMap.put("lento", 0.127368029599175);
        miestenXrMap.put("vapaa", 1.32807383482693);

        miestenXrMap.put("etulyhyt", 0.000);
        miestenXrMap.put("etupitkä", 0.247);
        miestenXrMap.put("linjaetu", 1.042);
        miestenXrMap.put("linjataka", 1.779);
        miestenXrMap.put("takakenttä", 1.506);

        miestenXrMap.put("erinomainen", 0.459031502203514);
        miestenXrMap.put("huono", 0.114914242183961);
        miestenXrMap.put("hyvä", 0.216741716506126);
        miestenXrMap.put("keskiverto", 0.154303080109923);
        miestenXrMap.put("heikko", 0.000);

        miestenXrMap.put("2k", 0.0805170359791177);
        miestenXrMap.put("3k", 0.0678144955021249);
        miestenXrMap.put("2p", 0.069244143232317);
        miestenXrMap.put("2v", 0.0962161401407556);
        miestenXrMap.put("3p", 0.030870720721764);
        miestenXrMap.put("3v", 0.0673980674267427);
        miestenXrMap.put("l", 0.250636391971532);
        miestenXrMap.put("S", 0.0690370099242293);
        miestenXrMap.put("1v", 0.06291954843901);

    }

    private void miestenXlapilyontiMap() {
        miestenXLapiMap.put("msualkuarvo", -3.97335441964245);
        miestenXLapiMap.put("kumura", 0.938604685999928);
        miestenXLapiMap.put("nappi", -18.583832743217);
        miestenXLapiMap.put("pomppu", -1.14447534207495);
        miestenXLapiMap.put("pussari", -15.8950682425984);
        miestenXLapiMap.put("pystari/viisto", 0.68815688208556);
        miestenXLapiMap.put("vaakamaila", 1.34892690992679);
        miestenXLapiMap.put("varsi", -17.3699309723167);
        miestenXLapiMap.put("koppi", 0.000);

        miestenXLapiMap.put("karvauskahdella", -20.1989628741689);
        miestenXLapiMap.put("karvausyhdella", -0.364803169876597);
        miestenXLapiMap.put("oulu", -0.81112800402384);
        miestenXLapiMap.put("pertsa", -0.556768220414041);
        miestenXLapiMap.put("ristivitonen", -0.140470524822915);
        miestenXLapiMap.put("sailytys", 0.254582358188998);
        miestenXLapiMap.put("tahko", -1.0141390179017);
        miestenXLapiMap.put("tahko2", -0.449794465468246);
        miestenXLapiMap.put("kaannettypertsa", 0.000);
        miestenXLapiMap.put("muu", -20.0);

        miestenXLapiMap.put("vääräpois", 0.000);
        miestenXLapiMap.put("kulma", 0.000);
        miestenXLapiMap.put("lento", 0.000);
        miestenXLapiMap.put("vapaa", 1.22755548203796);

        miestenXLapiMap.put("erinomainen", -0.0583264034750161);
        miestenXLapiMap.put("huono", -0.0912272804809523);
        miestenXLapiMap.put("hyvä", -0.287336974241431);
        miestenXLapiMap.put("keskiverto", -0.184790158290879);
        miestenXLapiMap.put("heikko", 0.000);

        miestenXLapiMap.put("2k", 0.00369451791316107);
        miestenXLapiMap.put("3k", -0.15066326156206);
        miestenXLapiMap.put("2p", 0.0594984577731075);
        miestenXLapiMap.put("2v", 0.0166446504403021);
        miestenXLapiMap.put("3p", -0.0551583477424309);
        miestenXLapiMap.put("3v", 0.171641079293421);
        miestenXLapiMap.put("l", -23.291000282437);
        miestenXLapiMap.put("S", 0.0793447256987359);
        miestenXLapiMap.put("1v", -1.07191200822227);

    }

    private void pelaajienSijaintiLisays() {
        miestenSijaintiMapX.put("kaannettypertsal", 21.00);
        miestenSijaintiMapX.put("oulul", 21.00);
        miestenSijaintiMapX.put("pertsal", 21.00);
        miestenSijaintiMapX.put("ristivitonenl", 21.00);
        miestenSijaintiMapX.put("tahkol", 21.00);
        miestenSijaintiMapX.put("tahko2l", 21.00);
        miestenSijaintiMapX.put("sailytysl", 21.00);
        miestenSijaintiMapX.put("karvauskahdellal", 21.00);
        miestenSijaintiMapX.put("karvausyhdellal", 21.00);
        miestenSijaintiMapX.put("muul", 21.00);

        miestenSijaintiMapX.put("kaannettypertsa3v", 3.0);
        miestenSijaintiMapX.put("oulu3v", 0.0);
        miestenSijaintiMapX.put("pertsa3v", 3.0);
        miestenSijaintiMapX.put("ristivitonen3v", 3.0);
        miestenSijaintiMapX.put("tahko3v", 0.0);
        miestenSijaintiMapX.put("tahko23v", 3.0);
        miestenSijaintiMapX.put("sailytys3v", 0.0);
        miestenSijaintiMapX.put("karvauskahdella3v", 0.0);
        miestenSijaintiMapX.put("karvausyhdella3v", 0.0);
        miestenSijaintiMapX.put("muu3v", 0.0);

        miestenSijaintiMapX.put("kaannettypertsa1v", 28.0);
        miestenSijaintiMapX.put("oulu1v", 19.1);
        miestenSijaintiMapX.put("pertsa1v", 19.0);
        miestenSijaintiMapX.put("ristivitonen1v", 25.5);
        miestenSijaintiMapX.put("tahko1v", 13.0);
        miestenSijaintiMapX.put("tahko21v", 17.5);
        miestenSijaintiMapX.put("sailytys1v", 15.5);
        miestenSijaintiMapX.put("karvauskahdella1v", 14.0);
        miestenSijaintiMapX.put("karvausyhdella1v", 14.0);
        miestenSijaintiMapX.put("muu1v", 42.0);


        miestenSijaintiMapX.put("kaannettypertsaS", 38.5);
        miestenSijaintiMapX.put("ouluS", 41.0);
        miestenSijaintiMapX.put("pertsaS", 38.5);
        miestenSijaintiMapX.put("ristivitonenS", 42.0);
        miestenSijaintiMapX.put("tahkoS", 38.5);
        miestenSijaintiMapX.put("tahko2S", 31.5);
        miestenSijaintiMapX.put("sailytysS", 30.5);
        miestenSijaintiMapX.put("karvauskahdellaS", 30.5);
        miestenSijaintiMapX.put("karvausyhdellaS", 41.0);
        miestenSijaintiMapX.put("muuS", 0.0);


        miestenSijaintiMapX.put("kaannettypertsa3p", 6.5);
        miestenSijaintiMapX.put("oulu3p", 6.5);
        miestenSijaintiMapX.put("pertsa3p", 6.5);
        miestenSijaintiMapX.put("ristivitonen3p", 6.5);
        miestenSijaintiMapX.put("tahko3p", 17.5);
        miestenSijaintiMapX.put("tahko23p", 6.5);
        miestenSijaintiMapX.put("sailytys3p", 17.0);
        miestenSijaintiMapX.put("karvauskahdella3p", 6.5);
        miestenSijaintiMapX.put("karvausyhdella3p", 6.5);
        miestenSijaintiMapX.put("muu3p", 0.0);

        miestenSijaintiMapX.put("kaannettypertsa2p", 19.0);
        miestenSijaintiMapX.put("oulu2p", 26.0);
        miestenSijaintiMapX.put("pertsa2p", 26.0);
        miestenSijaintiMapX.put("ristivitonen2p", 19.0);
        miestenSijaintiMapX.put("tahko2p", 26.0);
        miestenSijaintiMapX.put("tahko22p", 24.5);
        miestenSijaintiMapX.put("sailytys2p", 26.0);
        miestenSijaintiMapX.put("karvauskahdella2p", 26.0);
        miestenSijaintiMapX.put("karvausyhdella2p", 26.0);
        miestenSijaintiMapX.put("muu2p", 21.0);

        miestenSijaintiMapX.put("kaannettypertsa2v", 35.0);
        miestenSijaintiMapX.put("oulu2v", 35.0);
        miestenSijaintiMapX.put("pertsa2v", 35.0);
        miestenSijaintiMapX.put("ristivitonen2v", 35.0);
        miestenSijaintiMapX.put("tahko2v", 35.0);
        miestenSijaintiMapX.put("tahko22v", 42.0);
        miestenSijaintiMapX.put("sailytys2v", 42.0);
        miestenSijaintiMapX.put("karvauskahdella2v", 36.5);
        miestenSijaintiMapX.put("karvausyhdella2v", 35.0);
        miestenSijaintiMapX.put("muu2v", 42.0);

        miestenSijaintiMapX.put("kaannettypertsa3k", 14.0);
        miestenSijaintiMapX.put("oulu3k", 14.0);
        miestenSijaintiMapX.put("pertsa3k", 14.0);
        miestenSijaintiMapX.put("ristivitonen3k", 14.0);
        miestenSijaintiMapX.put("tahko3k", 14.0);
        miestenSijaintiMapX.put("tahko23k", 14.0);
        miestenSijaintiMapX.put("sailytys3k", 14.0);
        miestenSijaintiMapX.put("karvauskahdella3k", 14.0);
        miestenSijaintiMapX.put("karvausyhdella3k", 14.0);
        miestenSijaintiMapX.put("muu3k", 0.0);

        miestenSijaintiMapX.put("kaannettypertsa2k", 30.0);
        miestenSijaintiMapX.put("oulu2k", 30.0);
        miestenSijaintiMapX.put("pertsa2k", 30.0);
        miestenSijaintiMapX.put("ristivitonen2k", 30.0);
        miestenSijaintiMapX.put("tahko2k", 30.0);
        miestenSijaintiMapX.put("tahko22k", 30.0);
        miestenSijaintiMapX.put("sailytys2k", 30.0);
        miestenSijaintiMapX.put("karvauskahdella2k", 30.0);
        miestenSijaintiMapX.put("karvausyhdella2k", 30.0);
        miestenSijaintiMapX.put("muu2k", 0.0);
    }

    private void miestensijantiy() {
        miestenSijaintiMapY.put("kaannettypertsal", -1.5);
        miestenSijaintiMapY.put("oulul", -1.5);
        miestenSijaintiMapY.put("pertsal", -1.5);
        miestenSijaintiMapY.put("ristivitonenl", -1.5);
        miestenSijaintiMapY.put("tahkol", -1.5);
        miestenSijaintiMapY.put("tahko2l", -1.5);
        miestenSijaintiMapY.put("sailytysl", -1.5);
        miestenSijaintiMapY.put("karvauskahdellal", -1.5);
        miestenSijaintiMapY.put("karvausyhdellal", -1.5);
        miestenSijaintiMapY.put("muul", -1.5);

        miestenSijaintiMapY.put("kaannettypertsa3v", 33.5);
        miestenSijaintiMapY.put("oulu3v", 38.5);
        miestenSijaintiMapY.put("pertsa3v", 33.5);
        miestenSijaintiMapY.put("ristivitonen3v", 033.5);
        miestenSijaintiMapY.put("tahko3v", 41.5);
        miestenSijaintiMapY.put("tahko23v", 33.5);
        miestenSijaintiMapY.put("sailytys3v", 40.5);
        miestenSijaintiMapY.put("karvauskahdella3v", 40.5);
        miestenSijaintiMapY.put("karvausyhdella3v", 40.5);
        miestenSijaintiMapY.put("muu3v", 38.5);

        miestenSijaintiMapY.put("kaannettypertsa1v", 38.5);
        miestenSijaintiMapY.put("oulu1v", 25.5);
        miestenSijaintiMapY.put("pertsa1v", 38.5);
        miestenSijaintiMapY.put("ristivitonen1v", 25.5);
        miestenSijaintiMapY.put("tahko1v", 30.0);
        miestenSijaintiMapY.put("tahko21v", 40.5);
        miestenSijaintiMapY.put("sailytys1v", 26.0);
        miestenSijaintiMapY.put("karvauskahdella1v", 18.0);
        miestenSijaintiMapY.put("karvausyhdella1v", 18.0);
        miestenSijaintiMapY.put("muu1v", 55.0);


        miestenSijaintiMapY.put("kaannettypertsaS", 33.5);
        miestenSijaintiMapY.put("ouluS", 35.5);
        miestenSijaintiMapY.put("pertsaS", 33.5);
        miestenSijaintiMapY.put("ristivitonenS", 38.5);
        miestenSijaintiMapY.put("tahkoS", 33.5);
        miestenSijaintiMapY.put("tahko2S", 28.5);
        miestenSijaintiMapY.put("sailytysS", 28.5);
        miestenSijaintiMapY.put("karvauskahdellaS", 18.0);
        miestenSijaintiMapY.put("karvausyhdellaS", 35.5);
        miestenSijaintiMapY.put("muuS", 50.0);


        miestenSijaintiMapY.put("kaannettypertsa3p", 50.0);
        miestenSijaintiMapY.put("oulu3p", 48.0);
        miestenSijaintiMapY.put("pertsa3p", 48.0);
        miestenSijaintiMapY.put("ristivitonen3p", 48.0);
        miestenSijaintiMapY.put("tahko3p", 52.0);
        miestenSijaintiMapY.put("tahko23p", 48.5);
        miestenSijaintiMapY.put("sailytys3p", 49.5);
        miestenSijaintiMapY.put("karvauskahdella3p", 48.0);
        miestenSijaintiMapY.put("karvausyhdella3p", 48.0);
        miestenSijaintiMapY.put("muu3p", 75.0);

        miestenSijaintiMapY.put("kaannettypertsa2p", 55.0);
        miestenSijaintiMapY.put("oulu2p", 55.0);
        miestenSijaintiMapY.put("pertsa2p", 55.0);
        miestenSijaintiMapY.put("ristivitonen2p", 55.0);
        miestenSijaintiMapY.put("tahko2p", 41.5);
        miestenSijaintiMapY.put("tahko22p", 53.5);
        miestenSijaintiMapY.put("sailytys2p", 47.5);
        miestenSijaintiMapY.put("karvauskahdella2p", 57.5);
        miestenSijaintiMapY.put("karvausyhdella2p", 57.5);
        miestenSijaintiMapY.put("muu2p", 85.0);

        miestenSijaintiMapY.put("kaannettypertsa2v", 52.0);
        miestenSijaintiMapY.put("oulu2v", 48.0);
        miestenSijaintiMapY.put("pertsa2v", 48.0);
        miestenSijaintiMapY.put("ristivitonen2v", 52.0);
        miestenSijaintiMapY.put("tahko2v", 50.0);
        miestenSijaintiMapY.put("tahko22v", 41.5);
        miestenSijaintiMapY.put("sailytys2v", 38.5);
        miestenSijaintiMapY.put("karvauskahdella2v", 48.0);
        miestenSijaintiMapY.put("karvausyhdella2v", 48.0);
        miestenSijaintiMapY.put("muu2v", 60.0);

        miestenSijaintiMapY.put("kaannettypertsa3k", 82.0);
        miestenSijaintiMapY.put("oulu3k", 82.0);
        miestenSijaintiMapY.put("pertsa3k", 82.0);
        miestenSijaintiMapY.put("ristivitonen3k", 82.0);
        miestenSijaintiMapY.put("tahko3k", 82.0);
        miestenSijaintiMapY.put("tahko23k", 82.0);
        miestenSijaintiMapY.put("sailytys3k", 82.0);
        miestenSijaintiMapY.put("karvauskahdella3k", 82.0);
        miestenSijaintiMapY.put("karvausyhdella3k", 82.0);
        miestenSijaintiMapY.put("muu3k", 96.0);

        miestenSijaintiMapY.put("kaannettypertsa2k", 82.0);
        miestenSijaintiMapY.put("oulu2k", 82.0);
        miestenSijaintiMapY.put("pertsa2k", 82.0);
        miestenSijaintiMapY.put("ristivitonen2k", 82.0);
        miestenSijaintiMapY.put("tahko2k", 82.0);
        miestenSijaintiMapY.put("tahko22k", 82.0);
        miestenSijaintiMapY.put("sailytys2k", 82.0);
        miestenSijaintiMapY.put("karvauskahdella2k", 82.0);
        miestenSijaintiMapY.put("karvausyhdella2k", 82.0);
        miestenSijaintiMapY.put("muu2k", 96.0);
    }

    @FXML
    private void naistenkentta() {

        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, kentta.getWidth(), kentta.getHeight());
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(2.0);


        graphicsContext.strokeLine(150, 37.5, 550, 37.5); // Takaraja
        graphicsContext.strokeLine(150, 37.5, 150, 554); // Kolmosjatke
        graphicsContext.strokeLine(550, 37.5, 550, 472.5); // Kakkosjatke
        graphicsContext.strokeLine(275, 690, 400, 690); // Kotipesä

        graphicsContext.strokeLine(150, 472.5, 337.5, 690); // Kolmosraja

        graphicsContext.strokeLine(550, 472.5, 362.5, 690); // Kakkosraja

        graphicsContext.strokeLine(152, 415.480, 550, 415.480); // 2-3 väli

        graphicsContext.strokeLine(192, 592.5, 550, 415.480); // 1-2 väli

        graphicsContext.strokeLine(150, 554, 290, 690); // Kotijuoksuviiva


    }

    @FXML
    private void pesapallokentta() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, kentta.getWidth(), kentta.getHeight());
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(2.0);


        graphicsContext.strokeLine(150, 37.5, 550, 37.5); // Takaraja
        graphicsContext.strokeLine(150, 37.5, 150, 554); // Kolmosjatke
        graphicsContext.strokeLine(550, 37.5, 550, 472.5); // Kakkosjatke
        graphicsContext.strokeLine(275, 690, 400, 690); // Kotipesä

        graphicsContext.strokeLine(150, 472.5, 337.5, 690); // Kolmosraja

        graphicsContext.strokeLine(550, 472.5, 362.5, 690); // Kakkosraja

        graphicsContext.strokeLine(152, 428.3268, 550, 428.3268); // 2-3 väli

        graphicsContext.strokeLine(190, 592.5, 550, 428.3268); // 1-2 väli

        graphicsContext.strokeLine(150, 554, 290, 690); // Kotijuoksuviiva

        graphicsContext.fillOval(278, 125, 10, 10); // Kolmoskoppari


        graphicsContext.fillOval(435, 125, 10, 10); // Kakkoskoppari

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

        double y = (690 - yMetri) / 6.7967;

        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();
        graphicsContext.setFill(Color.RED);

        if (vierasjoukkueToggle.isSelected()) {
            graphicsContext.setFill(Color.BLUE);
        }

        graphicsContext.fillOval(mouseX, mouseY, 5, 5);

        koordinaattix.setText(String.format(Locale.US, "%.3f", 1 - mouseX / 750));
        koordinaattiy.setText(String.format(Locale.US, "%.3f", y));

        sijaintitext.setText(haesijanti(y));

        if (menuItemMiehet.isSelected()) {
            lyonninSijaintiMetreissaMiestenRajat(xMetri, yMetri);
        } else {
            lyonninSijaintiMetreissaNaistenRajat(xMetri, yMetri);
        }

    }

    @FXML
    private void laskeEtaisyys() {
        String kuvioxr = kuvio.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        double x = Double.parseDouble(koordinaattix.getText());
        double y = Double.parseDouble(koordinaattiy.getText());

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x - ulkopelaajaX, 2) + Math.pow(y - ulkopelaajaY, 2));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));
    }

    @FXML
    private void lyonninSijaintiMetreissaMiestenRajat(double x, double y) {
        String kuvioxr = kuvio.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        double xLaskettu = (x - 150) / 9.5238;
        double yLaskettu = (690 - y) / 6.7967;

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(xLaskettu - ulkopelaajaX, 2) + Math.pow(yLaskettu - ulkopelaajaY, 2));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));

        koordinaattix.setText(String.format(Locale.US, "%.2f", xLaskettu));
        koordinaattiy.setText(String.format(Locale.US, "%.2f", yLaskettu));

    }

    @FXML
    private void lyonninSijaintiMetreissaNaistenRajat(double x, double y) {

        String kuvioxr = kuvio.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        double xLaskettu = (x - 150) / 11.111;
        double yLaskettu = (690 - y) / 7.95731;

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(xLaskettu - ulkopelaajaX, 2) + Math.pow(yLaskettu - ulkopelaajaY, 2));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));

        koordinaattix.setText(String.format(Locale.US, "%.2f", xLaskettu));
        koordinaattiy.setText(String.format(Locale.US, "%.2f", yLaskettu));

    }

    @FXML
    private String haesijanti(Double y) {
        if (y <= 7.5) {
            return "etulyhyt";
        }
        if (y > 7.5 && y < 28.5) {
            return "etupitkä";
        }
        if (y <= 38.5 && y > 28.5) {
            return "linjaetu";
        }
        if (y >= 38.5 && y < 62) {
            return "linjataka";
        }
        if (y >= 62) {
            return "takakenttä";
        }
        return "linjataka";
    }

    @FXML
    private void lyontitiedot() {
        double x = Double.parseDouble(koordinaattix.getText());
        double y = Double.parseDouble(koordinaattiy.getText());

        String sijainti = haesijanti(y);

        Double juoksutodennakoisyys = laskeJuoksuTodennakoisyysMiehet();

        laskeTodennakoisyysLapi();

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
        String kunnarixr = kunnari.getValue();
        String tilanne = tilannecombobox.getValue();
        String ulkopelisuoritusxr = ulkopelisuorituscombobox.getValue();


        Lyontitiedot tiedot = new Lyontitiedot(
                x, y,
                sijainti, kuvioxr, tyyppixr,
                merrkixr, syottoxr, lyojaxr,
                joukkuexr, jaksoxr, vuoroparixr,
                OTTELUID, ulkopelixr, ulkopelivirhexr,
                ulkopelisuorittjaxr, ulkopelisuoritusxr,
                vaaraallaxr, lyontixr, juoksutxr,
                lapilyontixr, lyontinumeroxr, ulkopelijoukkuexr,
                etenijaxr, etenijalaatuxr, juoksutodennakoisyys,
                kunnarixr, tilanne
        );

        taulukkoxr.getItems().addAll(tiedot);

    }

    @FXML
    private void laskeTodennakoisyydet() {
        laskeJuoksuTodennakoisyysMiehet();
        laskeTodennakoisyysLapi();
    }

    @FXML
    private double laskeJuoksuTodennakoisyysMiehet() {
        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        Double intercept = miestenXrMap.get("msualkuarvo");

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

        double etaisyys = Math.sqrt(Math.pow(x - ulkopelaajaX, 2) + Math.pow(y - ulkopelaajaY, 2));

        double juoksutn = 1 / (1 + Math.exp(-(intercept + kuvio + tyyppi + merkki + etenijalaatu + sijanti + ulkopelaajaxr * etaisyys)));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));

        juoksuodottama.setText(String.format(Locale.US, "%.4f", juoksutn));

        laskeTodennakoisyysLapi();

        return juoksutn;
    }

    @FXML
    private void laskeTodennakoisyysLapi() {
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

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x - ulkopelaajaX, 2) + Math.pow(y - ulkopelaajaY, 2));

        double juoksutn = 1 / (1 + Math.exp(-(miestenXLapiMap.get("msualkuarvo") + kuvio + tyyppi + merkki + etenijalaatu + ulkopelaajaxr * etaisyys)));

        lapilyontiolettama.setText(String.format(Locale.US, "%.4f", juoksutn));

    }

    @FXML
    private void tallennacsvtiedostoon() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Tallenna tiedostoon");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", ".csv")
            );

            File tiedosto = fileChooser.showSaveDialog(null);

            if (tiedosto == null) {
                return;
            }

            FileWriter writer = new FileWriter(tiedosto.getAbsolutePath());

            List<Lyontitiedot> data = new ArrayList<>(taulukkoxr.getItems());

            CSVFormat format = CSVFormat.Builder.create().setHeader(
                    "otteluID", "jakso", "vuoropari",
                    "sisajoukkue", "lyoja", "etenija", "etenijanlaatu",
                    "ulkopelijoukkue", "ulkopelisuoritus", "ulkopelisuorittaja", "ulkopelipaikka", "ulkopelivirhe",
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

            OTTELUID = Integer.parseInt(ottelunid.getText());

            return jsonObject;
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }

        return null;
    }

    @FXML
    private void haepelaajat() {
        String otteluid = ottelunid.getText();

        if (otteluid.isEmpty()) {
            return;
        }

        Thread t = new HaeOtteluThread();
        t.start();
    }

    private void pelaajalisays(JSONObject jsonObject) {

        if ((jsonObject != null && jsonObject.has("error")) | jsonObject == null) {
            return;
        }
        kotipelaajat(jsonObject);
        vieraspelaajat(jsonObject);
    }

    @FXML
    private void kotipelaajat(JSONObject jsonObject) {
        ObservableList<Pelaajat> kotilyojatlista = FXCollections.observableArrayList();

        JSONArray kotipelaajalista = jsonObject.getJSONObject("home").getJSONArray("players");

        int kotijoukkueID = jsonObject.getJSONObject("home").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("home").getString("name");

        int kotipelaajapituus = kotipelaajalista.length();

        for (int i = 0; i < kotipelaajapituus; i++) {

            JSONObject pelaaja = kotipelaajalista.getJSONObject(i);

            kotilyojatlista.add(
                    new Pelaajat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            kotijoukkueID, vierasjoukkue
                    )
            );
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                kotilyojat.setItems(kotilyojatlista);

                kotilyojat.getSelectionModel().selectFirst();

                kotiulkopelaajat.setItems(kotilyojatlista);

                kotiulkopelaajat.getSelectionModel().selectFirst();

                kotietenija.setItems(kotilyojatlista);

                kotietenija.getSelectionModel().selectFirst();
            }
        });
    }

    @FXML
    private void vieraspelaajat(JSONObject jsonObject) {
        ObservableList<Pelaajat> vieraslyojatlista = FXCollections.observableArrayList();

        JSONArray vieraspelaajalista = jsonObject.getJSONObject("away").getJSONArray("players");

        int vierasjoukkueID = jsonObject.getJSONObject("away").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("away").getString("name");

        int vieraspelaajapituus = vieraspelaajalista.length();

        for (int i = 0; i < vieraspelaajapituus; i++) {

            JSONObject pelaaja = vieraspelaajalista.getJSONObject(i);

            vieraslyojatlista.add(
                    new Pelaajat(
                            pelaaja.getInt("number"), pelaaja.getInt("id"), pelaaja.getString("name"),
                            vierasjoukkueID, vierasjoukkue
                    )
            );
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vieraslyojat.setItems(vieraslyojatlista);

                vieraslyojat.getSelectionModel().selectFirst();

                vierasetenija.setItems(vieraslyojatlista);

                vierasetenija.getSelectionModel().selectFirst();

                vierasulkopelaajat.setItems(vieraslyojatlista);

                vierasulkopelaajat.getSelectionModel().selectFirst();
            }
        });

    }

    @FXML
    private void kotilyoja() {
        if (kotilyojat.getValue() == null) {
            return;
        }
        lyoja.setText(kotilyojat.getValue().getNimi());
        joukkue.setText(kotilyojat.getValue().getJoukkue());
        sisajoukkeuid.setText(String.valueOf(kotilyojat.getValue().getJoukkueID()));
    }

    @FXML
    private void vieraslyoja() {
        if (vieraslyojat.getValue() == null) {
            return;
        }
        lyoja.setText(vieraslyojat.getValue().getNimi());
        joukkue.setText(vieraslyojat.getValue().getJoukkue());
        sisajoukkeuid.setText(String.valueOf(vieraslyojat.getValue().getJoukkueID()));
    }

    @FXML
    private void kotiulkopelaaja() {
        if (kotiulkopelaajat.getValue() == null) {
            return;
        }
        ulkopelisuorittaja.setText(kotiulkopelaajat.getValue().getNimi());
        ulkopelijoukkue.setText(kotiulkopelaajat.getValue().getJoukkue());
        ulkojoukkueid.setText(String.valueOf(kotiulkopelaajat.getValue().getJoukkueID()));
    }

    @FXML
    private void vierasulkopelaaja() {
        if (vierasulkopelaajat.getValue() == null) {
            return;
        }
        ulkopelisuorittaja.setText(vierasulkopelaajat.getValue().getNimi());
        ulkopelijoukkue.setText(vierasulkopelaajat.getValue().getJoukkue());
        ulkojoukkueid.setText(String.valueOf(vierasulkopelaajat.getValue().getJoukkueID()));
    }

    @FXML
    private void kotietenijalisays() {
        if (kotietenija.getValue() == null) {
            return;
        }
        etenija.setText(kotietenija.getValue().getNimi());
    }

    @FXML
    private void vierasetenijalisays() {
        if (vierasetenija.getValue() == null) {
            return;
        }
        etenija.setText(vierasetenija.getValue().getNimi());
    }

    @FXML
    private void poistarivi() {
        Lyontitiedot poistettava = taulukkoxr.getSelectionModel().getSelectedItem();

        taulukkoxr.getItems().remove(poistettava);

        taulukkoxr.getSelectionModel().clearSelection();
    }

    public class HaeOtteluThread extends Thread {
        @Override
        public void run() {

            JSONObject data = haeotteluntiedot();

            if (data == null) {
                return;
            }

            pelaajalisays(data);

        }

    }


}