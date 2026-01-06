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
import javafx.scene.shape.ArcType;
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
import java.lang.reflect.InvocationTargetException;
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

    private final Map<String, Double> naistenXrMap = new HashMap<>();
    private final Map<String, Double> naistenXrLapiMap = new HashMap<>();

    private final Map<String, Double> miestenSijaintiMapX = new HashMap<>();
    private final Map<String, Double> miestenSijaintiMapY = new HashMap<>();

    private final Map<String, Double> naistenSijaintiMapX = new HashMap<>();
    private final Map<String, Double> naistenSijaintiMapY = new HashMap<>();

    public RadioMenuItem menuItemMiehet;
    public RadioMenuItem menuItemNaiset;
    public ComboBox<String> kuvionvaihdot;

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
    private ComboBox<String> lopputuloscombobox;

    @FXML
    private void sulje() {
        System.exit(0);
    }

    @FXML
    public void initialize() {

        // Map
        malli();

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

        lopputuloscombobox.getItems().addAll("kärkilyönti", "palo", "haava");
        lopputuloscombobox.getSelectionModel().selectFirst();

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
                "3-raja", "3-luukku", "3-sauma", "3-pussi", "3-jatke", "3-koppari",
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

        kuvionvaihdot.getItems().addAll("ei", "kyllä");
        kuvionvaihdot.getSelectionModel().selectFirst();
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
        taulukkojoukkue.setCellValueFactory(new PropertyValueFactory<>("sisajoukkue"));
        taulukkolyoja.setCellValueFactory(new PropertyValueFactory<>("lyoja"));
        taulukkotyyppi.setCellValueFactory(new PropertyValueFactory<>("lyonnintyyppi"));
        taulukkoOttelunID.setCellValueFactory(new PropertyValueFactory<>("ottelunID"));
        taulukkovuoropari.setCellValueFactory(new PropertyValueFactory<>("vuoropari"));
        taulukkoetenija.setCellValueFactory(new PropertyValueFactory<>("etenija"));
        taulukkoulkopelijoukkue.setCellValueFactory(new PropertyValueFactory<>("ulkopelijoukkue"));
        taulukkoulkopelaaja.setCellValueFactory(new PropertyValueFactory<>("ulkopelisuorittaja"));
        taulukkoulkopelipaikka.setCellValueFactory(new PropertyValueFactory<>("ulkopelipaikka"));
        taulukkolyontinumero.setCellValueFactory(new PropertyValueFactory<>("lyontinumero"));
        taulukkovaaraalla.setCellValueFactory(new PropertyValueFactory<>("vaaraAlla"));
        taulukkolyonti.setCellValueFactory(new PropertyValueFactory<>("lopputulos"));
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
                        setLopputulos(
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

        taulukkoTilanne.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setTilanne(
                                lyontitiedotStringCellEditEvent.getNewValue()
                        );
            }
        });

        taulukkojoukkue.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lyontitiedot, String> lyontitiedotStringCellEditEvent) {
                lyontitiedotStringCellEditEvent.getTableView().getItems().get
                                (lyontitiedotStringCellEditEvent.getTablePosition().getRow()).
                        setSisajoukkue(
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
                        setLyonnintyyppi(
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
        msuSijaintiMap(jsonObject);

        xrMallinsuMap(jsonObject);
        nsuSijaintiMap(jsonObject);
        naistenXRlapilyontiMap(jsonObject);
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

    private void xrMallinsuMap(JSONObject jsonObject) {

        JSONArray nsumalli = jsonObject.getJSONArray("nsumalli");

        int pituus = nsumalli.length();

        for (int i = 0; i < pituus; i++) {
            naistenXrMap.put(
                    nsumalli.getJSONObject(i).getString("avain"),
                    nsumalli.getJSONObject(i).getDouble("arvo")
            );

        }

    }

    private void naistenXRlapilyontiMap(JSONObject jsonObject) {
        JSONArray nsulapilyontimalli = jsonObject.getJSONArray("nsulapilyontimalli");

        int pituus = nsulapilyontimalli.length();

        for (int i = 0; i < pituus; i++) {
            naistenXrLapiMap.put(
                    nsulapilyontimalli.getJSONObject(i).getString("avain"),
                    nsulapilyontimalli.getJSONObject(i).getDouble("arvo")
            );

        }

    }

    private void msuSijaintiMap(JSONObject jsonObject) {

        JSONArray upMiehet = jsonObject.getJSONArray("msusijainnitup");

        int pituus = upMiehet.length();

        for (int i = 0; i < pituus; i++) {
            String nimi = upMiehet.getJSONObject(i).getString("ulkopelikuvio")
                    + upMiehet.getJSONObject(i).getString("pelipaikka");

            miestenSijaintiMapX.put(
                    nimi,
                    upMiehet.getJSONObject(i).getDouble("x")
            );

            miestenSijaintiMapY.put(
                    nimi,
                    upMiehet.getJSONObject(i).getDouble("y")
            );

        }

    }

    private void nsuSijaintiMap(JSONObject jsonObject) {

        JSONArray upNaiset = jsonObject.getJSONArray("nsusijainnitup");

        int pituus = upNaiset.length();

        for (int i = 0; i < pituus; i++) {
            String nimi = upNaiset.getJSONObject(i).getString("ulkopelikuvio")
                    + upNaiset.getJSONObject(i).getString("pelipaikka");

            naistenSijaintiMapX.put(
                    nimi,
                    upNaiset.getJSONObject(i).getDouble("x")
            );

            naistenSijaintiMapY.put(
                    nimi,
                    upNaiset.getJSONObject(i).getDouble("y")
            );

        }

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

        graphicsContext.strokeArc(120,395, 60,40, 270,180 , ArcType.ROUND); // Kolmospesä

        graphicsContext.strokeArc(520,395, 60,40, 90,180 , ArcType.ROUND); // Kakkospesä


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

        graphicsContext.strokeArc(120,408, 60,40, 270,180 , ArcType.ROUND); // Kolmospesä

        graphicsContext.strokeArc(520,408, 60,40, 90,180 , ArcType.ROUND); // Kakkospesä

    }

    @FXML
    private void kuvioAction() {
        ulkopelikuvio();
        laskeEtaisyys();
    }

    @FXML
    private void ulkopelikuvio() {
        if (menuItemMiehet.isSelected()){
            miestenKuviot();
        } else {
            naistenKuviot();
        }
    }

    @FXML
    private void miestenKuviot() {
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
    private void naistenKuviot() {
        naistenkentta();

        if (kuvio.getValue().equals("oulu")) {
            ouluNaiset();
        } else if (kuvio.getValue().equals("ristivitonen")) {
            ristiVitonenNaiset();
        } else if (kuvio.getValue().equals("tahko")) {
            tahkoKuvioNaiset();
        } else if (kuvio.getValue().equals("tahko2")) {
            kaannettyTahkoKuvioNaiset();
        } else if (kuvio.getValue().equals("pertsa")) {
            pertsaNaiset();
        } else if (kuvio.getValue().equals("kaannettypertsa")) {
            kaannettyPertsaNaiset();
        } else if (kuvio.getValue().equals("karvauskahdella")) {
            karvausKahdellaNaiset();
        } else if (kuvio.getValue().equals("karvausyhdella")) {
            karvausYhdellaNaiset();
        } else if (kuvio.getValue().equals("sailytys")) {
            sailytysNaiset();
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

        graphicsContext.fillOval(327, 510, 10, 10); // 1v

        graphicsContext.fillOval(533, 443, 10, 10); // Sieppari

        graphicsContext.fillOval(210, 360, 10, 10); // 3p

        graphicsContext.fillOval(393, 310, 10, 10); // 2p

        graphicsContext.fillOval(485, 360, 10, 10); // 2v
    }

    @FXML
    private void ouluNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 410, 10, 10); // 3v

        graphicsContext.fillOval(327, 500, 10, 10); // 1v

        graphicsContext.fillOval(530, 440, 10, 10); // Sieppari

        graphicsContext.fillOval(208, 358, 10, 10); // 3p

        graphicsContext.fillOval(395, 311, 10, 10); // 2p

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
    private void ristiVitonenNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(170, 445, 10, 10); // 3v

        graphicsContext.fillOval(388, 512, 10, 10); // 1v

        graphicsContext.fillOval(545, 410, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 359, 10, 10); // 3p

        graphicsContext.fillOval(326, 311, 10, 10); // 2p

        graphicsContext.fillOval(478, 331, 10, 10); // 2v
    }

    @FXML
    private void tahkoKuvio() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 402, 10, 10); // 3v

        graphicsContext.fillOval(268, 481, 10, 10); // 1v

        graphicsContext.fillOval(511, 445, 10, 10); // Sieppari

        graphicsContext.fillOval(312, 331, 10, 10); // 3p

        graphicsContext.fillOval(393, 403, 10, 10); // 2p

        graphicsContext.fillOval(478, 345, 10, 10); // 2v

    }

    @FXML
    private void tahkoKuvioNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 400, 10, 10); // 3v

        graphicsContext.fillOval(268, 485, 10, 10); // 1v

        graphicsContext.fillOval(515, 445, 10, 10); // Sieppari

        graphicsContext.fillOval(312, 331, 10, 10); // 3p

        graphicsContext.fillOval(393, 403, 10, 10); // 2p

        graphicsContext.fillOval(480, 345, 10, 10); // 2v

    }



    @FXML
    private void kaannettyTahkoKuvio() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(173, 457, 10, 10); // 3v

        graphicsContext.fillOval(312, 403, 10, 10); // 1v

        graphicsContext.fillOval(445, 491, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 355, 10, 10); // 3p

        graphicsContext.fillOval(378, 321, 10, 10); // 2p

        graphicsContext.fillOval(545, 403, 10, 10); // 2v


        //graphicsContext.fillOval(435, 125, 10, 10); // 3p

    }

    @FXML
    private void kaannettyTahkoKuvioNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(173, 445, 10, 10); // 3v

        graphicsContext.fillOval(312, 410, 10, 10); // 1v

        graphicsContext.fillOval(445, 490, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 355, 10, 10); // 3p

        graphicsContext.fillOval(383, 323, 10, 10); // 2p

        graphicsContext.fillOval(545, 400, 10, 10); // 2v

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
    private void kaannettyPertsaNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(170, 445, 10, 10); // 3v

        graphicsContext.fillOval(412, 407, 10, 10); // 1v

        graphicsContext.fillOval(520, 445, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 345, 10, 10); // 3p

        graphicsContext.fillOval(326, 311, 10, 10); // 2p

        graphicsContext.fillOval(480, 332, 10, 10); // 2v


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
    private void pertsaNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(170, 445, 10, 10); // 3v

        graphicsContext.fillOval(326, 405, 10, 10); // 1v

        graphicsContext.fillOval(515, 445, 10, 10); // Sieppari

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
    private void karvausKahdellaNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);
        //graphicsContext.setStroke(Color.BROWN);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 410, 10, 10); // 3v

        graphicsContext.fillOval(278, 563, 10, 10); // 1v

        graphicsContext.fillOval(425, 563, 10, 10); // Sieppari

        graphicsContext.fillOval(207, 359, 10, 10); // 3p

        graphicsContext.fillOval(385, 294, 10, 10); // 2p

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
    private void karvausYhdellaNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 410, 10, 10); // 3v

        graphicsContext.fillOval(278, 563, 10, 10); // 1v

        graphicsContext.fillOval(525, 443, 10, 10); // Sieppari

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
    private void sailytysNaiset() {
        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.DARKMAGENTA);

        graphicsContext.fillOval(278, 127, 10, 10); // Kolmoskoppari

        graphicsContext.fillOval(435, 127, 10, 10); // Kakkoskoppari

        graphicsContext.fillOval(145, 405, 10, 10); // 3v

        graphicsContext.fillOval(293, 508, 10, 10); // 1v

        graphicsContext.fillOval(435, 491, 10, 10); // Sieppari

        graphicsContext.fillOval(307, 349, 10, 10); // 3p

        graphicsContext.fillOval(392, 362, 10, 10); // 2p

        graphicsContext.fillOval(545, 405, 10, 10); // 2v
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

        if (menuItemMiehet.isSelected()){
            lyonninSijaintiMetreissaMiestenRajat(xMetri, yMetri);

        } else {
            lyonninSijaintiMetreissaNaistenRajat(xMetri, yMetri);
        }

    }

    @FXML
    private void laskeEtaisyys() {

        if (menuItemMiehet.isSelected()){
            miehet();
        }else {
            naiset();
        }

    }

    private void miehet() {
        String kuvioxr = kuvio.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        sijaintitext.setText(haeSijaintiMiehet(YKOORDINAATTI));

        Double ulkopelaajaX = miestenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = miestenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(XKOORDINAATTI - ulkopelaajaX, 2) + Math.pow(YKOORDINAATTI - ulkopelaajaY, 2));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));
    }

    private void naiset() {
        String kuvioxr = kuvio.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        sijaintitext.setText(haeSijaintiNaiset(YKOORDINAATTI));

        Double ulkopelaajaX = naistenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = naistenSijaintiMapY.get(kuvioxr + ulkopelaaja);

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

        sijaintitext.setText(haeSijaintiMiehet(yLaskettu));

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

        XKOORDINAATTI = xLaskettu;
        YKOORDINAATTI = yLaskettu;

        Double ulkopelaajaX = naistenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = naistenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(xLaskettu - ulkopelaajaX, 2) + Math.pow(yLaskettu - ulkopelaajaY, 2));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));

        koordinaattix.setText(String.format(Locale.US, "%.2f", xLaskettu));
        koordinaattiy.setText(String.format(Locale.US, "%.2f", yLaskettu));

        sijaintitext.setText(haeSijaintiNaiset(yLaskettu));


    }

    @FXML
    private String haeSijaintiMiehet(Double y) {
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
    private String haeSijaintiNaiset(Double y) {
        if (y <= 6.5) {
            return "etulyhyt";
        }
        if (y > 6.5 && y < 24) {
            return "etupitkä";
        }
        if (y >= 24 && y < 53) {
            return "linja";
        }
        if (y >= 53) {
            return "takakenttä";
        }
        return "linja";
    }

    @FXML
    private void lyontitiedot() {

        if (menuItemMiehet.isSelected()){
            miehetlyontitiedot();
        } else {
            naisetlyontitiedot();
        }

    }

    private void miehetlyontitiedot() {
        double x = XKOORDINAATTI;
        double y = YKOORDINAATTI;

        String sijainti = haeSijaintiMiehet(y);

        Double juoksutodennakoisyys = laskeJuoksuTodennakoisyysMiehet();

        Double lapilyontitn = laskeTodennakoisyysLapi();

        String kuvioxr = kuvio.getValue();
        String lyontityyppi = tyyppi.getValue();
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
        String lopputulos = lopputuloscombobox.getValue();
        Integer juoksutxr = juoksut.getValue();
        String lapilyontixr = lapilyonti.getValue();
        Integer lyontinumeroxr = lyontinumero.getValue();

        String ulkopelijoukkuexr = ulkopelijoukkue.getText();
        String etenijaxr = etenijat.getValue().getNimi();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String kunnarixr = kunnari.getValue();
        String tilanne = tilannecombobox.getValue();
        String ulkopelisuoritusxr = ulkopelisuorituscombobox.getValue();

        Integer sisajoukkue = lyojat.getValue().getJoukkueID();
        Integer lyojaID = lyojat.getValue().getId();
        Integer ulkopelaajaID = ulkopelaajat.getValue().getId();
        Integer ulkopelijoukkueid = ulkopelaajat.getValue().getJoukkueID();
        Integer palot = palojenMaara.getValue();
        String suunta = lyontisuuntacombobox.getValue();
        String kumurankorkeus = kumuranTyyppi.getValue();
        String saumakorkeus = saumakorkeuscombobox.getValue();
        String karkaus = karkauscombobox.getValue();
        String ulkopelitempo = ulkopelitempocombobox.getValue();


        Lyontitiedot tiedot = new Lyontitiedot(
                x, y,
                sijainti, kuvioxr, lyontityyppi,
                merkkixr, syottoxr, lyojaxr, lyojaID,
                sisapelijoukkuexr, sisajoukkue, jaksoxr, vuoroparixr,
                OTTELUID, ulkopelipaikkaxr, ulkopelivirhexr,
                ulkopelisuorittjaxr, ulkopelaajaID, ulkopelisuoritusxr,
                vaaraallaxr, lopputulos, juoksutxr,
                lapilyontixr, lyontinumeroxr, ulkopelijoukkuexr, ulkopelijoukkueid,
                etenijaxr, etenijalaatuxr, juoksutodennakoisyys,
                kunnarixr, tilanne, palot, suunta, kumurankorkeus, saumakorkeus, karkaus, ulkopelitempo, lapilyontitn
        );

        taulukkoxr.getItems().addAll(tiedot);
    }

    private void naisetlyontitiedot() {
        double x = XKOORDINAATTI;
        double y = YKOORDINAATTI;

        String sijainti = haeSijaintiNaiset(y);

        Double juoksutodennakoisyys = laskeJuoksuTodennakoisyysNaiset();

        Double lapilyontitn = laskeTodennakoisyysLapiNaiset();

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
        String lyontixr = lopputuloscombobox.getValue();
        Integer juoksutxr = juoksut.getValue();
        String lapilyontixr = lapilyonti.getValue();
        Integer lyontinumeroxr = lyontinumero.getValue();

        String ulkopelijoukkuexr = ulkopelijoukkue.getText();
        String etenijaxr = etenijat.getValue().getNimi();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String kunnarixr = kunnari.getValue();
        String tilanne = tilannecombobox.getValue();
        String ulkopelisuoritusxr = ulkopelisuorituscombobox.getValue();

        Integer sisajoukkue = lyojat.getValue().getJoukkueID();
        Integer lyojaID = lyojat.getValue().getId();
        Integer ulkopelaajaID = ulkopelaajat.getValue().getId();
        Integer ulkopelijoukkueid = ulkopelaajat.getValue().getJoukkueID();
        Integer palot = palojenMaara.getValue();
        String suunta = lyontisuuntacombobox.getValue();
        String kumurankorkeus = kumuranTyyppi.getValue();
        String saumakorkeus = saumakorkeuscombobox.getValue();
        String karkaus = karkauscombobox.getValue();
        String ulkopelitempo = ulkopelitempocombobox.getValue();



        Lyontitiedot tiedot = new Lyontitiedot(
                x, y,
                sijainti, kuvioxr, tyyppixr,
                merkkixr, syottoxr, lyojaxr, lyojaID,
                sisapelijoukkuexr, sisajoukkue, jaksoxr, vuoroparixr,
                OTTELUID, ulkopelipaikkaxr, ulkopelivirhexr,
                ulkopelisuorittjaxr, ulkopelaajaID, ulkopelisuoritusxr,
                vaaraallaxr, lyontixr, juoksutxr,
                lapilyontixr, lyontinumeroxr, ulkopelijoukkuexr, ulkopelijoukkueid,
                etenijaxr, etenijalaatuxr, juoksutodennakoisyys,
                kunnarixr, tilanne, palot, suunta, kumurankorkeus, saumakorkeus, karkaus, ulkopelitempo, lapilyontitn
        );

        taulukkoxr.getItems().addAll(tiedot);
    }

    @FXML
    private void laskeTodennakoisyydet() {

        if (menuItemMiehet.isSelected()){
            todennakoisyydetMiehet();
        }
        else {
            todennakoisyydetNaiset();
        }
    }

    private void todennakoisyydetMiehet() {
        laskeJuoksuTodennakoisyysMiehet();
        laskeTodennakoisyysLapi();
    }

    private void todennakoisyydetNaiset() {
        laskeJuoksuTodennakoisyysNaiset();
        laskeTodennakoisyysLapiNaiset();
    }

    @FXML
    private double laskeJuoksuTodennakoisyysNaiset() {
        double x = XKOORDINAATTI;
        double y = YKOORDINAATTI;

        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();
        String sijainti = haeSijaintiNaiset(y);

        Double intercept = naistenXrMap.get("(Intercept)");
        Double kuvio = naistenXrMap.get(kuvioxr);
        Double tyyppi = naistenXrMap.get(tyyppixr);
        Double merkki = naistenXrMap.get(merkkixr);
        Double etenijalaatu = naistenXrMap.get(etenijalaatuxr);
        Double ulkopelaajaxr = naistenXrMap.get(ulkopelaaja);
        Double sijanti = naistenXrMap.get(sijainti);

        Double ulkopelaajaX = naistenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = naistenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x - ulkopelaajaX, 2) + Math.pow(y - ulkopelaajaY, 2));

        double juoksutn = 1 / (1 + Math.exp(-(intercept + kuvio + tyyppi + merkki + etenijalaatu + sijanti + ulkopelaajaxr * etaisyys)));

        lyonninEtaisyysulkopelaajasta.setText(String.format(Locale.US, "%.2f", etaisyys));

        juoksuodottama.setText(String.format(Locale.US, "%.4f", juoksutn));

        return juoksutn;
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
        String sijainti = haeSijaintiMiehet(y);

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
    private Double laskeTodennakoisyysLapi() {
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

        return juoksutn;
    }

    @FXML
    private Double laskeTodennakoisyysLapiNaiset() {
        double x = XKOORDINAATTI;
        double y = YKOORDINAATTI;

        Double intercept = naistenXrLapiMap.get("(Intercept)");
        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String etenijalaatuxr = etenijalaatucombobox.getValue();
        String ulkopelaaja = ulkopelipaikka.getValue();

        Double kuvio = naistenXrLapiMap.get(kuvioxr);
        Double tyyppi = naistenXrLapiMap.get(tyyppixr);
        Double merkki = naistenXrLapiMap.get(merkkixr);
        Double etenijalaatu = naistenXrLapiMap.get(etenijalaatuxr);
        Double ulkopelaajaxr = naistenXrLapiMap.get(ulkopelaaja);

        Double ulkopelaajaX = naistenSijaintiMapX.get(kuvioxr + ulkopelaaja);
        Double ulkopelaajaY = naistenSijaintiMapY.get(kuvioxr + ulkopelaaja);

        double etaisyys = Math.sqrt(Math.pow(x - ulkopelaajaX, 2) + Math.pow(y - ulkopelaajaY, 2));

        double juoksutn = 1 / (1 + Math.exp(-(intercept + kuvio + tyyppi + merkki + etenijalaatu + ulkopelaajaxr * etaisyys)));

        lapilyontiolettama.setText(String.format(Locale.US, "%.4f", juoksutn));

        return juoksutn;
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
                    "otteluID", "jakso", "vuoropari", "tilanne", "palot", "lyontinumero",
                    "sisajoukkue", "sisajoukkueid", "lyoja", "lyojaID", "etenija", "etenijanlaatu",
                    "ulkopelijoukkue", "ulkopelijoukkueid", "ulkopelisuorittaja", "ulkopelaajaID",
                    "ulkopelipaikka", "ulkopelisuoritus", "ulkopelivirhe", "ulkopelitempo",
                    "kuvio", "vaaraAlla", "merkki", "karkaus", "syotto", "lyonnintyyppi",
                    "saumakorkeus", "kumurankorkeus", "suunta", "koordinaattix", "koordinaattiy",
                    "sijainti", "lopputulos", "juoksut", "lapilyonti", "kunnari", "juoksutodennakoisyys", "lapilyontitn"
            ).get();

            CSVPrinter printer = format.print(writer);


            for (Lyontitiedot datum : data) {
                printer.printRecord(
                        datum.getOttelunID(),
                        datum.getJakso(),
                        datum.getVuoropari(),
                        datum.getTilanne(),
                        datum.getPalot(),
                        datum.getLyontinumero(),

                        datum.getSisajoukkue(),
                        datum.getSisajoukkueid(),
                        datum.getLyoja(),
                        datum.getLyojaID(),
                        datum.getEtenija(),
                        datum.getEtenijanlaatu(),

                        datum.getUlkopelijoukkue(),
                        datum.getUlkopelijoukkueID(),
                        datum.getUlkopelisuorittaja(),
                        datum.getUlkopelaajaID(),
                        datum.getUlkopelipaikka(),
                        datum.getUlkopelisuoritus(),
                        datum.getUlkopelivirhe(),
                        datum.getUlkopelitempo(),


                        datum.getKuvio(),
                        datum.getVaaraAlla(),
                        datum.getMerkki(),
                        datum.getKarkaus(),
                        datum.getSyotto(),
                        datum.getLyonnintyyppi(),
                        datum.getSaumakorkeus(),
                        datum.getKumurakorkeus(),
                        datum.getSuunta(),


                        datum.getKoordinaattix(),
                        datum.getKoordinaattiy(),
                        datum.getSijainti(),

                        datum.getLopputulos(),
                        datum.getJuoksut(),
                        datum.getLapilyonti(),
                        datum.getKunnari(),

                        datum.getJuoksutodennakoisyys(),
                        datum.getLapilyontitn()
                );
            }

            printer.flush();

            writer.close();
        } catch (IOException ioexception) {
            System.err.print(ioexception.getMessage());
        }
    }

    @FXML
    private void avaaOttelu() throws IOException {
        FileReader fileReader = null;

        CSVParser parse = null;
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

            fileReader = new FileReader(tiedosto.getAbsolutePath());

            List<Lyontitiedot> data = new ArrayList<>();

            CSVFormat format = CSVFormat.Builder.create().setHeader(
                    "otteluID", "jakso", "vuoropari", "tilanne", "palot", "lyontinumero",
                    "sisajoukkue", "sisajoukkueid", "lyoja", "lyojaID", "etenija", "etenijanlaatu",
                    "ulkopelijoukkue", "ulkopelijoukkueid", "ulkopelisuorittaja", "ulkopelaajaID",
                    "ulkopelipaikka", "ulkopelisuoritus", "ulkopelivirhe", "ulkopelitempo",
                    "kuvio", "vaaraAlla", "merkki", "karkaus", "syotto", "lyonnintyyppi",
                    "saumakorkeus", "kumurankorkeus", "suunta", "koordinaattix", "koordinaattiy",
                    "sijainti", "lopputulos", "juoksut", "lapilyonti", "kunnari", "juoksutodennakoisyys", "lapilyontitn"
            ).setSkipHeaderRecord(true).get();

            parse = CSVParser.parse(fileReader, format);

            for (CSVRecord i: parse) {
                Integer otteluid = Integer.valueOf(
                        i.get("otteluID")
                );
                String jakso = i.get("jakso");
                Integer vuoropari = Integer.valueOf(
                        i.get("vuoropari")
                );

                String tilanne = i.get("tilanne");
                Integer palot = Integer.valueOf(i.get("palot"));
                Integer lyontinumero = Integer.valueOf(i.get("lyontinumero"));

                String sisajoukkue = i.get("sisajoukkue");
                Integer sisajoukkueid = Integer.valueOf(i.get("sisajoukkueid"));
                String lyoja = i.get("lyoja");
                Integer lyojaid = Integer.valueOf(i.get("lyojaID"));

                String etenija = i.get("etenija");
                String etenijalaatu = i.get("etenijanlaatu");

                String ulkopelijoukkue = i.get("ulkopelijoukkue");
                Integer ulkopelijoukkueid = Integer.valueOf(i.get("ulkopelijoukkueid"));

                String ulkopelisuorittaja = i.get("ulkopelisuorittaja");
                Integer ulkopelaajaID = Integer.valueOf(i.get("ulkopelaajaID"));

                String ulkopelipaikka = i.get("ulkopelipaikka");
                String ulkopelisuoritus = i.get("ulkopelisuoritus");
                String ulkopelivirhe = i.get("ulkopelivirhe");
                String ulkopelitempo = i.get("ulkopelitempo");

                Double koordinaattix = Double.valueOf(i.get("koordinaattix"));
                Double koordinaattiy = Double.valueOf(i.get("koordinaattiy"));

                String kuvio = i.get("kuvio");
                String vaaraAlla = i.get("vaaraAlla");
                String merkki = i.get("merkki");
                String karkaus = i.get("karkaus");
                String syotto = i.get("syotto");


                String lyonnintyyppi = i.get("lyonnintyyppi");
                String saumakorkeus = i.get("saumakorkeus");
                String kumurankorkeus = i.get("kumurankorkeus");

                String sijainti = i.get("sijainti");
                String lopputulos = i.get("lopputulos");
                Integer juoksut = Integer.valueOf(i.get("juoksut"));
                String lapilyonti = i.get("lapilyonti");
                String kunnari = i.get("kunnari");

                Double juoksutodennakoisyys = Double.valueOf(i.get("juoksutodennakoisyys"));
                Double lapilyontitn = Double.valueOf(i.get("lapilyontitn"));


                data.add(new Lyontitiedot(
                koordinaattix, koordinaattiy,
                sijainti, kuvio, lyonnintyyppi,
                merkki, syotto, lyoja, lyojaid,
                sisajoukkue, sisajoukkueid ,jakso, vuoropari,
                otteluid, ulkopelipaikka, ulkopelivirhe,
                ulkopelisuorittaja, ulkopelaajaID, ulkopelisuoritus,
                vaaraAlla, lopputulos, juoksut,
                lapilyonti, lyontinumero, ulkopelijoukkue, ulkopelijoukkueid,
                etenija, etenijalaatu, juoksutodennakoisyys,
                kunnari, tilanne, palot, sijainti, kumurankorkeus, saumakorkeus, karkaus, ulkopelitempo, lapilyontitn
                ));

            }

            taulukkoxr.getItems().addAll(data);

            parse.close();

            fileReader.close();

        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }  finally {
            if (fileReader != null | parse != null) {
                fileReader.close();
                parse.close();
            }
        }
    }

    @FXML
    private JSONObject haeotteluntiedot() {
        try {
            String otteluid = ottelunid.getText();

            String ilma = "https://api.pesistulokset.fi/api/v1/public/match?id=" + otteluid + "&apikey=wRX0tTke3DZ8RLKAMntjZ81LwgNQuSN9";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ilma)).GET().headers(
                            "Content-Type", "application/json")
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