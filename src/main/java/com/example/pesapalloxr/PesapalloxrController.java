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
    private final Map<String, Double> miestenSijaintiMap = new HashMap<>();
    private final Map<String, Double> miestenEtaisyysVaikutusMap = new HashMap<>();

    public ComboBox<String> ulkopelisuorituscombobox;
    public ComboBox<String> lyontisuuntacombobox;
    public TextField testi;
    @FXML private TextField sisajoukkeuid;
    @FXML private TextField ulkojoukkueid;
    @FXML private ComboBox<String> etenijalaatucombobox;
    @FXML private ComboBox<Lyojat> kotietenija;
    @FXML private ComboBox<Lyojat> vierasetenija;
    @FXML private ComboBox<String> ulkopelivirhe;
    @FXML private ComboBox<Integer> juoksut;
    @FXML private ComboBox<String> ulkopelipaikka;
    @FXML private ComboBox<Lyojat> kotilyojat;
    @FXML private ComboBox<Lyojat> vieraslyojat;
    @FXML private TextField ottelunid;
    @FXML private ComboBox<Lyojat> kotiulkopelaajat;
    @FXML private ComboBox<Lyojat> vierasulkopelaajat;
    @FXML private TextField ulkopelisuorittaja;
    @FXML private TextField ulkopelijoukkue;
    @FXML private ComboBox<Integer> vuoropari;
    @FXML private TextField etenija;
    @FXML private ComboBox<String> vaaraalla;
    @FXML private ComboBox<String> lapilyonti;
    @FXML private ComboBox<String> kunnari;
    @FXML private ComboBox<Integer> lyontinumero;
    @FXML private TextField idottelu;
    @FXML private TableColumn<Lyontitiedot, Integer> taulukkoOttelunID;
    @FXML private TableColumn<Lyontitiedot, Integer> taulukkovuoropari;
    @FXML private TableColumn<Lyontitiedot, String> taulukkoetenija;
    @FXML private TableColumn<Lyontitiedot, String> taulukkoulkopelijoukkue;
    @FXML private TableColumn<Lyontitiedot, String> taulukkoulkopelaaja;
    @FXML private TableColumn<Lyontitiedot, String> taulukkoulkopelipaikka;
    @FXML private TableColumn<Lyontitiedot, Integer> taulukkolyontinumero;
    @FXML private TableColumn<Lyontitiedot, String> taulukkovaaraalla;
    @FXML private TableColumn<Lyontitiedot, String> taulukkolyonti;
    @FXML private TableColumn<Lyontitiedot, Integer> taulukkojuoksut;
    @FXML private TableColumn<Lyontitiedot, String> taulukkolapilyonti;
    @FXML private TableColumn<Lyontitiedot, String> taulukkokunnari;
    @FXML private TableColumn<Lyontitiedot, String> taulukkoulkopelivirhe;
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
        lyontityyppit();
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
        //taulukkokunnari.setCellValueFactory(new PropertyValueFactory<>(""));
        taulukkoulkopelivirhe.setCellValueFactory(new PropertyValueFactory<>("ulkopelivirhe"));


    }

    private void valikot() {
        kuvio.getItems().addAll("oulu", "pertsa", "ristivitonen", "kaannettypertsa", "tahko", "tahko2", "sailytys",
                "karvauskahdella", "karvausyhdella", "muu"
        );
        kuvio.getSelectionModel().selectFirst();


        tyyppi.getItems().addAll("kumura", "vaakamaila", "nappi", "pomppu", "pussari", "pystari/viisto",
                "varsi", "koppi");
        tyyppi.getSelectionModel().selectFirst();


        merkki.getItems().addAll("vapaa", "jokumuu", "lento"
        );
        merkki.getSelectionModel().selectFirst();


        syotto.getItems().addAll("perus", "puolikorkea", "tolppa", "taktinen väärä");
        syotto.getSelectionModel().selectFirst();

        jakso.getItems().addAll("1", "2", "supervuoro", "kotari");
        jakso.getSelectionModel().selectFirst();

        lyonticombobox.getItems().addAll("kärkilyönti", "palo", "haava");
        lyonticombobox.getSelectionModel().selectFirst();

        juoksut.getItems().addAll(0, 1, 2, 3, 4);
        juoksut.getSelectionModel().selectFirst();

        ulkopelivirhe.getItems().addAll("ei", "kyllä");
        ulkopelivirhe.getSelectionModel().selectFirst();

        etenijalaatucombobox.getItems().addAll("erinomainen", "hyvä", "keskiverto", "heikko", "huono");
        etenijalaatucombobox.getSelectionModel().selectFirst();


        ulkopelipaikka.getItems().addAll("l", "s", "1v", "3v", "3p", "2p", "2v", "3k", "2k");
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
                "kolmosraja", "kolmosluukku", "kolmosauma", "kolmospussi",
                "keskisauma", "keskipussi", "kakkossauma", "kakkospussi", "kakkosluukku", "kakkosraja",
                "keskikentta", "kolmosjatke", "kolmoskoppari", "keskitakanen", "kakkokopppari", "kakkosjatke"
        );
        lyontisuuntacombobox.getSelectionModel().selectFirst();

        ulkopelisuorituscombobox.getItems().addAll("ei", "kyllä");
        ulkopelisuorituscombobox.getSelectionModel().selectFirst();
    }

    private void lyontityyppit(){
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
        miestenXrMap.put("jokumuu", 0.000);
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

        graphicsContext.strokeLine(210, 592.5, 600, 420); // 1-2 väli

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

        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.RED);

        if (vierasjoukkue.isSelected()) {
            graphicsContext.setFill(Color.BLUE);
        }
        graphicsContext.fillOval(mouseX, mouseY, 5, 5); // Center the oval
        koordinaattix.setText(String.format(Locale.US, "%.3f", 1 - mouseX / 750));
        koordinaattiy.setText(String.format(Locale.US, "%.3f",mouseY / 750));

        String sijainti = "etulyhyt";

        double y = mouseY / 750;

        if (y >= 0.87) {
            sijainti = "etulyhyt";
        }
        if (y < 0.87 && y > 0.70) {
            sijainti = "etupitkä";
        }
        if (y <= 0.7 && y >= 0.56) {
            sijainti = "linjaetu";
        }
        if (y < 0.56 && y >= 0.35) {
            sijainti = "linjataka";
        }
        if (y < 0.35) {
            sijainti = "takakenttä";
        }

        sijaintitext.setText(sijainti);

        //lyontitiedot(mouseX, mouseY);
    }

    @FXML
    private void lyontitiedot() {
        String sijainti = "etulyhyt";

        double x = Double.parseDouble(koordinaattix.getText());

        double y = Double.parseDouble(koordinaattiy.getText());

        if (y >= 0.87) {
            sijainti = "etulyhyt";
        }
        if (y < 0.87 && y > 0.70) {
            sijainti = "etupitkä";
        }
        if (y <= 0.7 && y >= 0.56) {
            sijainti = "linjaetu";
        }
        if (y < 0.56 && y >= 0.40) {
            sijainti = "linjataka";
        }
        if (y < 0.40) {
            sijainti = "takakenttä";
        }

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

        Lyontitiedot tiedot = new Lyontitiedot(
                x, y,
                sijainti, kuvioxr, tyyppixr,
                merrkixr, syottoxr, lyojaxr,
                joukkuexr, jaksoxr, vuoroparixr,
                1, ulkopelixr, ulkopelivirhexr,
                ulkopelisuorittjaxr, vaaraallaxr, lyontixr,
                juoksutxr, lapilyontixr, lyontinumeroxr, ulkopelijoukkuexr,
                etenijaxr, etenijalaatuxr
        );

        taulukkoxr.getItems().addAll(tiedot);

        juoksuodottama(sijainti);
        haearvo(tyyppixr);
    }

    private void haearvo(String avain){
        System.out.print(miestenXrMap.get(avain));
        System.out.print("\n");
        System.out.print(1 / (1 + Math.exp(miestenXrMap.get(avain))));
        System.out.print("\n");
        System.out.print(1 / (1 + Math.exp(-(-3.17379 + 0.28894 + 0.56405 + 1.77917 + 0.14177 +
                0.145122190 * 5.76319 +  1.31913)))
        );
    }

    @FXML
    private void juoksuodottama(String sijainti){
        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merkkixr = merkki.getValue();
        String etenijalaatuxr = etenijalaatucombobox.getValue();

        Double kuvio = miestenXrMap.get(kuvioxr);
        Double tyyppi = miestenXrMap.get(tyyppixr);
        Double merkki = miestenXrMap.get(merkkixr);
        Double etenijalaatu = miestenXrMap.get(etenijalaatuxr);
        Double sijanti = miestenXrMap.get(sijainti);

        double juoksutn = 1 / (1 + Math.exp(-(-3.17379 + kuvio + tyyppi + merkki + etenijalaatu + sijanti)));

        testi.setText(String.valueOf(juoksutn));

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
                    "otteluID", "koordinaatti.x", "koordinaatti.y", "kuvioxr",
                    "tyyppixr", "merkkixr", "sijaintixr", "syotto", "lyoja",
                    "sisajoukkue", "jakso", "vuoropari", "ulkopelipaikka",
                    "ulkopelivirhe", "ulkopelisuorittaja", "vaaraAlla", "lyonti",
                    "juoksut", "lapilyonti", "lyontinumero", "ulkopelijoukkue",
                    "etenija", "etenijanlaatu"
            ).get();

            CSVPrinter printer = format.print(writer);


            for (Lyontitiedot datum : data) {
                printer.printRecord(
                        datum.getOttelunID(),
                        datum.getKoordinaattix(),
                        datum.getKoordinaattiy(),
                        datum.getKuvio(),
                        datum.getTyyppi(),
                        datum.getMerkki(),
                        datum.getSijainti(),
                        datum.getSyotto(),
                        datum.getLyoja(),
                        datum.getJoukkue(),
                        datum.getJakso(),
                        datum.getVuoropari(),
                        datum.getUlkopelipaikka(),
                        datum.getUlkopelivirhe(),
                        datum.getUlkopelisuorittaja(),
                        datum.getVaaraAlla(),
                        datum.getLyonti(),
                        datum.getJuoksut(),
                        datum.getLapilyonti(),
                        datum.getLyontinumero(),
                        datum.getUlkopelijoukkue(),
                        datum.getEtenija(),
                        datum.getEtenijanlaatu()
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