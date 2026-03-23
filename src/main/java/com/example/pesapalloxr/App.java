package com.example.pesapalloxr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.Objects;


public class App extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        JMetro jMetro = new JMetro(Style.LIGHT);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet/style.css")).toExternalForm());
        jMetro.setScene(scene);
        stage.setTitle("Pesäpalloxr");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}