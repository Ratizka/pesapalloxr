module com.example.pesapalloxr {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.desktop;
    requires java.sql;
    requires java.net.http;

    requires org.apache.commons.csv;
    requires org.json;
    requires jdk.crypto.ec;
    requires org.jfxtras.styles.jmetro;
    requires javafx.graphics;

    opens com.example.pesapalloxr to javafx.fxml;
    exports com.example.pesapalloxr;
}