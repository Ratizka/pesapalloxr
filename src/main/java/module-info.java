module com.example.pesapalloxr {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.desktop;
    requires java.sql;
    requires org.apache.commons.csv;
    requires java.net.http;
    requires org.json;
    requires jdk.crypto.ec;
    requires org.jfxtras.styles.jmetro;

    opens com.example.pesapalloxr to javafx.fxml;
    exports com.example.pesapalloxr;
}