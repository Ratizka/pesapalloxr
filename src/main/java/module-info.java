module com.example.pesapalloxr {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires java.desktop;
    requires java.sql;
    requires java.net.http;

    requires jdk.crypto.ec;
    requires org.apache.commons.csv;
    requires org.json;
    requires org.jfxtras.styles.jmetro;

    opens com.example.pesapalloxr to javafx.fxml;
    exports com.example.pesapalloxr;
    exports com.example.pesapalloxr.db;
    opens com.example.pesapalloxr.db to javafx.fxml;
    exports com.example.pesapalloxr.DataClasses;
    opens com.example.pesapalloxr.DataClasses to javafx.fxml;
    exports com.example.pesapalloxr.Ottelutiedot;
    opens com.example.pesapalloxr.Ottelutiedot to javafx.fxml;
}