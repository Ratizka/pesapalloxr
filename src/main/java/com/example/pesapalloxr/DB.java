package com.example.pesapalloxr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DB {

    protected Connection tiedot(String sarja) {

        Connection conn = null;

        try {

            URL url = getClass().getResource("/properties/.properties");

            Path path = Paths.get(Objects.requireNonNull(url).toURI());

            Properties db = new Properties();

            db.load(Files.newInputStream(path));

            String dbURL = "jdbc:postgresql://localhost:5432/" + sarja;

            conn = DriverManager.getConnection(dbURL, db);

        } catch (SQLException | IOException | URISyntaxException ex) {
            System.err.println(ex.getMessage());
        }

        return conn;

    }

}
