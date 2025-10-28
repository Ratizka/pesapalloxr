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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.util.converter.IntegerStringConverter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static jfxtras.styles.jmetro.JMetroStyleClass.*;


public class PesapalloxrController {

    private static int OTTELUID = 1;
    private static double XKOORDINAATTI = 21;
    private static double YKOORDINAATTI = 43;

    private final Map<String, Double> miestenXrMap = new HashMap<>();
    private final Map<String, Double> miestenXLapiMap = new HashMap<>();
    //private final Map<String, Double> naistenXrMap = new HashMap<>();
    private final Map<String, Double> miestenSijaintiMapX = new HashMap<>();
    private final Map<String, Double> miestenSijaintiMapY = new HashMap<>();

    @FXML
    private Canvas lyontiCanvas;
    @FXML
    private ComboBox<String> kumuranTyyppi;
    @FXML
    private ComboBox<Integer> palojenMaara;
    @FXML
    private Tooltip tempoToolTip;
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
    private ComboBox<String> etenijalaatucombobox;
    @FXML
    private ComboBox<Pelaajat> etenijat;
    @FXML
    private ComboBox<String> ulkopelivirhe;
    @FXML
    private ComboBox<Integer> juoksut;
    @FXML
    private ComboBox<String> ulkopelipaikka;
    @FXML
    private ComboBox<Pelaajat> lyojat;
    @FXML
    private TextField ottelunid;
    @FXML
    private ComboBox<Pelaajat> ulkopelaajat;
    @FXML
    private TextField ulkopelijoukkue;
    @FXML
    private ComboBox<Integer> vuoropari;
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
    private TextField sisapelijoukkue;
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

        // Map
        malli();
        miestenSijaintiX();
        miestenSijantiY();

        // UI
        resoluutio();
        valikot();
        taulukontiedot();
        taulukkomuokkaus();
        sarakkeidenmuokkaus();
        taulukonOminaisuuksia();
        ulkopelikuvio();
    }

    @FXML
    private void resoluutio() {
        Rectangle2D koko = Screen.getPrimary().getVisualBounds();
        double width = koko.getWidth();
        double height = koko.getHeight();
        ui.setPrefWidth(width);
        ui.setPrefHeight(height);
    }

    @FXML
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

        kumuranTyyppi.getItems().addAll("", "matala", "korkea");
        kumuranTyyppi.getSelectionModel().selectFirst();

        palojenMaara.getItems().addAll(0, 1, 2);
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

        etenijalaatucombobox.getItems().addAll("huippu", "hyvä", "keskiverto", "heikko", "huono");
        etenijalaatucombobox.getSelectionModel().selectFirst();

        ulkopelipaikka.getItems().addAll("l", "s", "1v", "3v", "3p", "2p", "2v", "3k", "2k");
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

        lyojat.getItems().addAll(new Pelaajat(1, 1, "Koti", 1, "Koti"),
                new Pelaajat(1, 2, "Vieras", 2, "Vieras"));
        lyojat.getSelectionModel().selectFirst();

        etenijat.getItems().addAll(new Pelaajat(1, 1, "Koti", 1, "Koti"),
                new Pelaajat(1, 2, "Vieras", 2, "Vieras"));
        etenijat.getSelectionModel().selectFirst();

        ulkopelaajat.getItems().addAll(new Pelaajat(1, 1, "Koti", 1, "Koti"),
                new Pelaajat(1, 2, "Vieras", 2, "Vieras"));
        ulkopelaajat.getSelectionModel().selectFirst();
    }

    @FXML
    private void taulukkoValinta() {

        taulukkoxr.getSelectionModel().clearSelection();

    }

    @FXML
    private void taulukontiedot() {
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
    private void taulukonOminaisuuksia() {
        addIfNotPresent(taulukkoxr.getStyleClass(), ALTERNATING_ROW_COLORS);
        addIfNotPresent(taulukkoxr.getStyleClass(), TABLE_GRID_LINES);

        taulukkoxr.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taulukkoxr.getSelectionModel().setCellSelectionEnabled(true);

        ContextMenu taulukkoContextMenu = new ContextMenu();
        MenuItem taulukkopoista = new MenuItem("Poista");

        taulukkoContextMenu.getItems().addAll(taulukkopoista);

        taulukkoxr.setContextMenu(taulukkoContextMenu);


        taulukkoxr.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE) & (taulukkoxr.getSelectionModel() != null)) {
                poistarivi();
            }
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                taulukkoValinta();
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
    }

    @FXML
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

    @FXML
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

        taulukkosijainti.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setSijainti(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkoulkopelivirhe.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setUlkopelivirhe(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkolapilyonti.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setLapilyonti(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });


        taulukkomerkki.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setMerkki(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkosyotto.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setSyotto(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkojakso.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setJakso(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkojoukkue.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setJoukkue(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkolyoja.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setLyoja(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkotyyppi.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setTyyppi(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkokunnari.setOnEditCommit(new EventHandler<>() {
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

    private void malli() {
        JSONObject jsonObject = lataamallit();

        xrmap(jsonObject);
        miestenXlapilyontiMap(jsonObject);
    }

    private JSONObject lataamallit() {
        try {
            URL url = getClass().getResource("/mallit/mallit.json");

            Path path = Paths.get(Objects.requireNonNull(url).toURI());

            return new JSONObject(new String(Files.readAllBytes(path)));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void xrmap(JSONObject jsonObject) {

        JSONArray msumalli = jsonObject.getJSONArray("msumalli");

        int pituus = msumalli.length();

        for (int i = 0; i < pituus; i++) {
            miestenXrMap.put(
                    msumalli.getJSONObject(i).getString("avain"),
                    msumalli.getJSONObject(i).getDouble("arvo")
            );

        }

    }

    private void miestenXlapilyontiMap(JSONObject jsonObject) {
        JSONArray msulapilyontimalli = jsonObject.getJSONArray("msulapilyontimalli");

        int pituus = msulapilyontimalli.length();

        for (int i = 0; i < pituus; i++) {
            miestenXLapiMap.put(
                    msulapilyontimalli.getJSONObject(i).getString("avain"),
                    msulapilyontimalli.getJSONObject(i).getDouble("arvo")
            );

        }

    }

    private void miestenSijaintiX() {
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


        miestenSijaintiMapX.put("kaannettypertsas", 38.5);
        miestenSijaintiMapX.put("oulus", 41.0);
        miestenSijaintiMapX.put("pertsas", 38.5);
        miestenSijaintiMapX.put("ristivitonens", 42.0);
        miestenSijaintiMapX.put("tahkos", 38.5);
        miestenSijaintiMapX.put("tahko2s", 31.5);
        miestenSijaintiMapX.put("sailytyss", 30.5);
        miestenSijaintiMapX.put("karvauskahdellas", 30.5);
        miestenSijaintiMapX.put("karvausyhdellas", 41.0);
        miestenSijaintiMapX.put("muus", 0.0);


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

    private void miestenSijantiY() {
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


        miestenSijaintiMapY.put("kaannettypertsas", 33.5);
        miestenSijaintiMapY.put("oulus", 35.5);
        miestenSijaintiMapY.put("pertsas", 33.5);
        miestenSijaintiMapY.put("ristivitonens", 38.5);
        miestenSijaintiMapY.put("tahkos", 33.5);
        miestenSijaintiMapY.put("tahko2s", 28.5);
        miestenSijaintiMapY.put("sailytyss", 28.5);
        miestenSijaintiMapY.put("karvauskahdellas", 18.0);
        miestenSijaintiMapY.put("karvausyhdellas", 35.5);
        miestenSijaintiMapY.put("muus", 50.0);


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
    private void miestenkentta() {
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

    }

    @FXML
    private void kuvioAction() {
        ulkopelikuvio();
        laskeEtaisyys();
    }

    @FXML
    private void ulkopelikuvio() {
        miestenkentta();

        if (kuvio.getValue().equals("oulu")) {
            oulu();
        } else if (kuvio.getValue().equals("ristivitonen")) {
            ristiVitonen();
        } else if (kuvio.getValue().equals("tahko")) {
            tahkoKuvio();
        } else if (kuvio.getValue().equals("tahko2")) {
            kaannettyTahkoKuvio();
        } else if (kuvio.getValue().equals("pertsa")) {
            pertsa();
        } else if (kuvio.getValue().equals("kaannettypertsa")) {
            kaannettyPertsa();
        } else if (kuvio.getValue().equals("karvauskahdella")) {
            karvausKahdella();
        } else if (kuvio.getValue().equals("karvausyhdella")) {
            karvausYhdella();
        } else if (kuvio.getValue().equals("sailytys")) {
            sailytys();
        } else {
            muu();
        }
    }

    @FXML
    private void oulu() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);


        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 423, 10, 10); // 3v

        graphicsContext.fillOval(327, 521, 10, 10); // 1v

        graphicsContext.fillOval(535, 443, 10, 10); // Sieppari

        graphicsContext.fillOval(217, 350, 10, 10); // 3p

        graphicsContext.fillOval(400, 311, 10, 10); // 2p

        graphicsContext.fillOval(478, 358, 10, 10); // 2v
    }

    @FXML
    private void ristiVitonen() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(174, 457, 10, 10); // 3v

        graphicsContext.fillOval(388, 512, 10, 10); // 1v

        graphicsContext.fillOval(545, 423, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 359, 10, 10); // 3p

        graphicsContext.fillOval(326, 311, 10, 10); // 2p

        graphicsContext.fillOval(478, 331, 10, 10); // 2v
    }

    @FXML
    private void tahkoKuvio() {
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

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 402, 10, 10); // 3v

        graphicsContext.fillOval(268, 481, 10, 10); // 1v

        graphicsContext.fillOval(511, 457, 10, 10); // Sieppari

        graphicsContext.fillOval(312, 331, 10, 10); // 3p

        graphicsContext.fillOval(393, 403, 10, 10); // 2p

        graphicsContext.fillOval(478, 345, 10, 10); // 2v

    }

    @FXML
    private void kaannettyTahkoKuvio() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(173, 457, 10, 10); // 3v

        graphicsContext.fillOval(312, 410, 10, 10); // 1v

        graphicsContext.fillOval(445, 491, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 355, 10, 10); // 3p

        graphicsContext.fillOval(378, 321, 10, 10); // 2p

        graphicsContext.fillOval(545, 403, 10, 10); // 2v


        //graphicsContext.fillOval(435, 125, 10, 10); // 3p

    }

    @FXML
    private void kaannettyPertsa() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(173, 457, 10, 10); // 3v

        graphicsContext.fillOval(412, 423, 10, 10); // 1v

        graphicsContext.fillOval(512, 457, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 345, 10, 10); // 3p

        graphicsContext.fillOval(326, 311, 10, 10); // 2p

        graphicsContext.fillOval(478, 331, 10, 10); // 2v


    }

    @FXML
    private void pertsa() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(173, 457, 10, 10); // 3v

        graphicsContext.fillOval(326, 423, 10, 10); // 1v

        graphicsContext.fillOval(512, 457, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 359, 10, 10); // 3p

        graphicsContext.fillOval(392, 311, 10, 10); // 2p

        graphicsContext.fillOval(478, 359, 10, 10); // 2v

    }

    @FXML
    private void karvausKahdella() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 410, 10, 10); // 3v

        graphicsContext.fillOval(278, 563, 10, 10); // 1v

        graphicsContext.fillOval(435, 563, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 359, 10, 10); // 3p

        graphicsContext.fillOval(392, 294, 10, 10); // 2p

        graphicsContext.fillOval(493, 359, 10, 10); // 2v
    }

    @FXML
    private void karvausYhdella() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 410, 10, 10); // 3v

        graphicsContext.fillOval(278, 563, 10, 10); // 1v

        graphicsContext.fillOval(535, 443, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 359, 10, 10); // 3p

        graphicsContext.fillOval(392, 294, 10, 10); // 2p

        graphicsContext.fillOval(493, 359, 10, 10); // 2v
    }

    @FXML
    private void sailytys() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 410, 10, 10); // 3v

        graphicsContext.fillOval(293, 508, 10, 10); // 1v

        graphicsContext.fillOval(435, 491, 10, 10); // Sieppari

        graphicsContext.fillOval(307, 349, 10, 10); // 3p

        graphicsContext.fillOval(392, 362, 10, 10); // 2p

        graphicsContext.fillOval(545, 423, 10, 10); // 2v
    }

    @FXML
    private void muu() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(145, 33, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(545, 33, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 410, 10, 10); // 3v

        graphicsContext.fillOval(145, 311, 10, 10); // 1v

        graphicsContext.fillOval(545, 410, 10, 10); // Sieppari

        graphicsContext.fillOval(145, 175, 10, 10); // 3p

        graphicsContext.fillOval(345, 107, 10, 10); // 2p

        graphicsContext.fillOval(545, 277, 10, 10); // 2v
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

        GraphicsContext graphicsContext = lyontiCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.RED);

        if (vierasjoukkueToggle.isSelected()) {
            graphicsContext.setFill(Color.BLUE);
        }

        graphicsContext.fillOval(mouseX, mouseY, 5, 5);

        lyonninSijaintiMetreissaMiestenRajat(xMetri, yMetri);


    }

    @FXML
    private void laskeEtaisyys() {
        String kuvioxr = kuvio.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        sijaintitext.setText(haesijanti(YKOORDINAATTI));

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(XKOORDINAATTI - ulkopelaajaX, 2) + Math.pow(YKOORDINAATTI - ulkopelaajaY, 2));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));

    }

    @FXML
    private void lyonninSijaintiMetreissaMiestenRajat(double x, double y) {
        String kuvioxr = kuvio.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        double xLaskettu = (x - 150) / 9.5238;
        double yLaskettu = (690 - y) / 6.7967;

        XKOORDINAATTI = xLaskettu;
        YKOORDINAATTI = yLaskettu;

        sijaintitext.setText(haesijanti(yLaskettu));

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
        if (y >= 28.5 && y < 62) {
            return "linja";
        }
        if (y >= 62) {
            return "takakenttä";
        }
        return "linja";
    }

    @FXML
    private void lyontitiedot() {
        double x = XKOORDINAATTI;
        double y = YKOORDINAATTI;

        String sijainti = haesijanti(y);

        Double juoksutodennakoisyys = laskeJuoksuTodennakoisyysMiehet();

        laskeTodennakoisyysLapi();

        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String syottoxr = syotto.getValue();
        String lyojaxr = lyojat.getValue().getNimi();
        String sisapelijoukkuexr = sisapelijoukkue.getText();
        String jaksoxr = jakso.getValue();

        Integer vuoroparixr = vuoropari.getValue();
        String ulkopelipaikkaxr = ulkopelipaikka.getValue();
        String ulkopelivirhexr = ulkopelivirhe.getValue();
        String ulkopelisuorittjaxr = ulkopelaajat.getValue().getNimi();
        String vaaraallaxr = vaaraalla.getValue();
        String lyontixr = lyonticombobox.getValue();
        Integer juoksutxr = juoksut.getValue();
        String lapilyontixr = lapilyonti.getValue();
        Integer lyontinumeroxr = lyontinumero.getValue();

        String ulkopelijoukkuexr = ulkopelijoukkue.getText();
        String etenijaxr = etenijat.getValue().getNimi();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String kunnarixr = kunnari.getValue();
        String tilanne = tilannecombobox.getValue();
        String ulkopelisuoritusxr = ulkopelisuorituscombobox.getValue();


        Lyontitiedot tiedot = new Lyontitiedot(
                x, y,
                sijainti, kuvioxr, tyyppixr,
                merkkixr, syottoxr, lyojaxr,
                sisapelijoukkuexr, jaksoxr, vuoroparixr,
                OTTELUID, ulkopelipaikkaxr, ulkopelivirhexr,
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
        double x = XKOORDINAATTI;
        double y = YKOORDINAATTI;

        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();
        String sijainti = haesijanti(y);

        Double intercept = miestenXrMap.get("(Intercept)");
        Double kuvio = miestenXrMap.get(kuvioxr);
        Double tyyppi = miestenXrMap.get(tyyppixr);
        Double merkki = miestenXrMap.get(merkkixr);
        Double etenijalaatu = miestenXrMap.get(etenijalaatuxr);
        Double ulkopelaajaxr = miestenXrMap.get(ulkopelaaja);
        Double sijanti = miestenXrMap.get(sijainti);

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x - ulkopelaajaX, 2) + Math.pow(y - ulkopelaajaY, 2));

        double juoksutn = 1 / (1 + Math.exp(-(intercept + kuvio + tyyppi + merkki + etenijalaatu + sijanti + ulkopelaajaxr * etaisyys)));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));

        juoksuodottama.setText(String.format(Locale.US, "%.4f", juoksutn));

        return juoksutn;
    }

    @FXML
    private void laskeTodennakoisyysLapi() {
        double x = XKOORDINAATTI;
        double y = YKOORDINAATTI;

        Double intercept = miestenXLapiMap.get("(Intercept)");
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

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x - ulkopelaajaX, 2) + Math.pow(y - ulkopelaajaY, 2));

        double juoksutn = 1 / (1 + Math.exp(-(intercept + kuvio + tyyppi + merkki + etenijalaatu + ulkopelaajaxr * etaisyys)));

        lapilyontiolettama.setText(String.format(Locale.US, "%.4f", juoksutn));

    }

    @FXML
    private void tallennacsvtiedostoon() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Tallenna tiedostoon");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
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
                    "koordinaattix", "koordinaattiy", "kuvio",
                    "tyyppi", "merkki", "sijainti", "syotto",
                    "vaaraAlla", "lyonti", "juoksut", "lapilyonti", "lyontinumero", "juoksutodennakoisyys",
                    "kunnari", "tilanne"
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

                        datum.getJuoksutodennakoisyys(),
                        datum.getKunnari(),
                        datum.getTilanne()

                );
            }

            printer.flush();

            writer.close();
        } catch (IOException ioexception) {
            System.err.print(ioexception.getMessage());
        }
    }

    @FXML
    private void avaaOttelu() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Avaa tiedosto");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
            );

            File tiedosto = fileChooser.showOpenDialog(null);

            if (tiedosto == null) {
                return;
            }

            FileReader fileReader = new FileReader(tiedosto.getAbsolutePath());

            List<Lyontitiedot> data = new ArrayList<>();

            CSVFormat format = CSVFormat.Builder.create().setHeader(
                    "otteluID", "jakso", "vuoropari",
                    "sisajoukkue", "lyoja", "etenija", "etenijanlaatu",
                    "ulkopelijoukkue", "ulkopelisuoritus", "ulkopelisuorittaja", "ulkopelipaikka", "ulkopelivirhe",
                    "koordinaattix", "koordinaattiy", "kuvio",
                    "tyyppi", "merkki", "sijainti", "syotto",
                    "vaaraAlla", "lyonti", "juoksut", "lapilyonti", "lyontinumero", "juoksutodennakoisyys",
                    "kunnari", "tilanne"
            ).setSkipHeaderRecord(true).get();

            CSVParser parse = CSVParser.parse(fileReader, format);

            for (CSVRecord i: parse) {
                Integer otteluid = Integer.valueOf(
                        i.get("otteluID")
                );
                String jakso = i.get("jakso");
                Integer vuoropari = Integer.valueOf(
                        i.get("vuoropari")
                );
                String sisajoukkue = i.get("sisajoukkue");
                String lyoja = i.get("lyoja");
                String etenija = i.get("etenija");
                String etenijalaatu = i.get("etenijanlaatu");
                String ulkopelijoukkue = i.get("ulkopelijoukkue");
                String ulkopelisuoritus = i.get("ulkopelisuoritus");
                String ulkopelisuorittaja = i.get("ulkopelisuorittaja");
                String ulkopelipaikka = i.get("ulkopelipaikka");
                String ulkopelivirhe = i.get("ulkopelivirhe");

                Double koordinaattix = Double.valueOf(i.get("koordinaattix"));
                Double koordinaattiy = Double.valueOf(i.get("koordinaattiy"));

                String kuvio = i.get("kuvio");
                String tyyppi = i.get("tyyppi");
                String merkki = i.get("merkki");
                String sijainti = i.get("sijainti");
                String syotto = i.get("syotto");
                String vaaraAlla = i.get("vaaraAlla");
                String lyonti = i.get("lyonti");
                Integer juoksut = Integer.valueOf(i.get("juoksut"));

                String lapilyonti = i.get("lapilyonti");

                Integer lyontinumero = Integer.valueOf(i.get("lyontinumero"));
                Double juoksutodennakoisyys = Double.valueOf(i.get("juoksutodennakoisyys"));
                String kunnari = i.get("kunnari");
                String tilanne = i.get("tilanne");

                data.add(new Lyontitiedot(
                        koordinaattix, koordinaattiy,
                        sijainti, kuvio, tyyppi,
                        merkki, syotto, lyoja,
                        sisajoukkue, jakso, vuoropari,
                        otteluid, ulkopelipaikka, ulkopelivirhe,
                        ulkopelisuorittaja, ulkopelisuoritus,
                        vaaraAlla, lyonti, juoksut,
                        lapilyonti, lyontinumero, ulkopelijoukkue,
                        etenija, etenijalaatu, juoksutodennakoisyys,
                        kunnari, tilanne
                ));
            }

            taulukkoxr.getItems().addAll(data);


            parse.close();

            fileReader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private JSONObject haeotteluntiedot() {
        try {
            String otteluid = ottelunid.getText();

            String ilma = "https://api.pesistulokset.fi/api/v1/public/match?id=" + otteluid + "&apikey=wRX0tTke3DZ8RLKAMntjZ81LwgNQuSN9";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ilma)).GET().headers(
                            "Content-Type", "application/json",
                            "User-Agent", " Mozilla/5.0 (Windows NT 11.0; Win64; x64; rv:143.0) Gecko/20100101 Firefox/143.0")
                    .build();

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
                lyojat.setItems(kotilyojatlista);

                lyojat.getSelectionModel().selectFirst();

                ulkopelaajat.setItems(kotilyojatlista);

                ulkopelaajat.getSelectionModel().selectFirst();

                etenijat.setItems(kotilyojatlista);

                etenijat.getSelectionModel().selectFirst();
            }
        });
    }

    @FXML
    private void vieraspelaajat(JSONObject jsonObject) {
        ObservableList<Pelaajat> vieraslyojatlista = FXCollections.observableArrayList();

        JSONArray vieraspelaajalista = jsonObject.getJSONObject("away").getJSONArray("players");

        int vierasjoukkueID = jsonObject.getJSONObject("away").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("away").getString("name");

        vieraslyojatlista.add(
                new Pelaajat(
                        0, 0, " ",
                        vierasjoukkueID, vierasjoukkue
                ));

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
                lyojat.getItems().addAll(vieraslyojatlista);

                //lyojat.getSelectionModel().selectFirst();

                //etenijat.setItems(vieraslyojatlista);

                //vierasetenija.getSelectionModel().selectFirst();

                //vierasulkopelaajat.setItems(vieraslyojatlista);

                //vierasulkopelaajat.getSelectionModel().selectFirst();
            }
        });

    }

    @FXML
    private void sisapelijoukkue() {
        if (lyojat.getValue() == null) {
            return;
        }
        sisapelijoukkue.setText(lyojat.getValue().getJoukkue());
    }

    @FXML
    private void sisapelijoukkueEtenija() {
        if (etenijat.getValue() == null) {
            return;
        }
        sisapelijoukkue.setText(etenijat.getValue().getJoukkue());
    }

    @FXML
    private void ulkopelijoukkue() {
        if (ulkopelaajat.getValue() == null) {
            return;
        }
        ulkopelijoukkue.setText(ulkopelaajat.getValue().getJoukkue());
    }

    @FXML
    private void poistarivi() {
        Lyontitiedot poistettava = taulukkoxr.getSelectionModel().getSelectedItem();

        taulukkoxr.getItems().remove(poistettava);

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