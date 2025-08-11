package com.example.pesapalloxr;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PesapalloxrController {

    public Canvas kentta;
    public TextField koordinaattix;
    public TextField koordinaattiy;
    public ComboBox<String> kuvio;
    public ComboBox<String> tyyppi;
    public ComboBox<String> merkki;
    public ComboBox<String> syotto;
    public TextField joukkue;
    public TextField lyoja;
    public ComboBox<String> jakso;
    public TableView<Lyontitiedot> taulukkoxr;
    public TableColumn<Lyontitiedot,Double> taulukkokoordinaattix;
    public TableColumn<Lyontitiedot,Double> taulukkokoordinaattiy;
    public TableColumn<Lyontitiedot, String> taulukkokuvio;
    public TableColumn<Lyontitiedot, String> taulukkosijainti;
    public TableColumn<Lyontitiedot, String> taulukkomerkki;
    public TableColumn<Lyontitiedot, String> taulukkosyotto;
    public TableColumn<Lyontitiedot, String> taulukkojakso;
    public TableColumn<Lyontitiedot, String> taulukkojoukkue;
    public TableColumn<Lyontitiedot, String> taulukkolyoja;
    public TableColumn<Lyontitiedot, String> taulukkotyyppi;
    public BorderPane ui;
    public TextField sijaintitext;
    public ComboBox<String> lyonticombobox;
    public RadioButton vierasjoukkue;


    @FXML
    public void initialize() {
        resoluutio();
        pesapallokentta();
        valikot();
        taulukontiedot();
        taulukkomuokkaus();
        sarakkeidenmuokkaus();
    }

    private void resoluutio() {
        Rectangle2D koko = Screen.getPrimary().getVisualBounds();
        double width = koko.getWidth();
        double height = koko.getHeight();
        ui.setPrefWidth(width);
        ui.setPrefHeight(height);
    }

    private void taulukontiedot() {
        taulukkoxr.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    }

    private void valikot() {
        kuvio.getItems().addAll("oulu", "pertsa", "ristivitonen", "kaannettypertsa", "tahko", "tahko2", "sailytys",
                "karvauskahdella", "karvausyhdella", "muu"
        );
        kuvio.getSelectionModel().selectFirst();


        tyyppi.getItems().addAll("koppi", "kumura", "muu", "nappi", "pomppu", "pussari", "pystari/viisto",
                "vaakamaila", "varsi"
        );
        tyyppi.getSelectionModel().selectFirst();


        merkki.getItems().addAll("lento", "jokumuu", "vapaa"
        );
        merkki.getSelectionModel().selectFirst();


        syotto.getItems().addAll("perus", "puolikorkea", "tolppa", "taktinen vaara"
        );
        syotto.getSelectionModel().selectFirst();

        jakso.getItems().addAll("1", "2", "supervuoro", "kotari");
        jakso.getSelectionModel().selectFirst();

        lyonticombobox.getItems().addAll("kentässä", "koppi");
        lyonticombobox.getSelectionModel().selectFirst();
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

        double mouseX = event.getX() - 2.5;
        double mouseY = event.getY() - 2.5;

        GraphicsContext graphicsContext = kentta.getGraphicsContext2D();

        graphicsContext.setFill(Color.RED);

        if (vierasjoukkue.isSelected()){
            graphicsContext.setFill(Color.BLUE);
        }
        graphicsContext.fillOval(mouseX, mouseY, 5, 5); // Center the oval
        koordinaattix.setText(String.valueOf(1 - mouseX / 750));
        koordinaattiy.setText(String.valueOf(mouseY / 750));

        String sijainti = "etulyhyt";

        double y = mouseY / 750;

        if (y >= 0.87){
            sijainti = "etulyhyt";
        }
        if (y < 0.87 && y/750 > 0.70){
            sijainti = "etupitkä";
        }
        if (y <= 0.7 && y >= 0.56){
            sijainti = "linjaetu";
        }
        if (y < 0.56 && y >= 0.35){
            sijainti = "linjataka";
        }
        if (y < 0.35){
            sijainti = "takakenttä";
        }

        sijaintitext.setText(sijainti);

        //lyontitiedot(mouseX, mouseY);
    }

    @FXML
    private void lyontitiedot(){
        String sijainti = "etulyhyt";


        double x = Double.parseDouble(koordinaattix.getText());

        double y = Double.parseDouble(koordinaattiy.getText());

        if (y >= 0.87){
            sijainti = "etulyhyt";
        }
        if (y < 0.87 && y > 0.70){
            sijainti = "etupitkä";
        }
        if (y <= 0.7 && y >= 0.56){
            sijainti = "linjaetu";
        }
        if (y < 0.56 && y >= 0.40){
            sijainti = "linjataka";
        }
        if (y < 0.40){
            sijainti = "takakenttä";
        }

        String kuvioxr = kuvio.getValue();
        String tyyppixr = tyyppi.getValue();
        String merrkixr = merkki.getValue();
        String syottoxr = syotto.getValue();
        String lyojaxr = lyoja.getText();
        String joukkuexr = joukkue.getText();
        String jaksoxr = jakso.getValue();

        Lyontitiedot tiedot = new Lyontitiedot(
                x, y,
                sijainti, kuvioxr, tyyppixr,
                merrkixr, syottoxr, lyojaxr,
                joukkuexr, jaksoxr
                );

        taulukkoxr.getItems().addAll(tiedot);
    }

    @FXML
    private void tallennacsvtiedostoon() throws IOException {

        try (FileWriter writer = new FileWriter("testi.csv")) {

            List<Lyontitiedot> data = new ArrayList<>(taulukkoxr.getItems());
            CSVFormat format = CSVFormat.Builder.create().setHeader("koordinaatti.x", "koordinaatti.y","kuvioxr",
                    "tyyppixr", "merkkixr", "sijaintixr", "syotto", "lyoja", "joukkue", "jakso"
                    ).get();
            CSVPrinter printer = format.print(writer);



            for (Lyontitiedot datum : data) {
                printer.printRecord(
                        datum.getKoordinaattix(),
                        datum.getKoordinaattiy(),
                        datum.getKuvio(),
                        datum.getTyyppi(),
                        datum.getMerkki(),
                        datum.getSijainti(),
                        datum.getSyotto(),
                        datum.getLyoja(),
                        datum.getJoukkue(),
                        datum.getJakso()
                        );
            }

            printer.flush();

        }
    }


}