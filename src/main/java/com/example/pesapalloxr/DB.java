package com.example.pesapalloxr;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private DB() {

    }

    protected static Connection tiedot(String sarja) {
        Connection conn = null;
        try {
            Properties db = new Properties();

            db.load(new FileReader("./db.properties", StandardCharsets.UTF_8));

            String dbURL = "jdbc:postgresql://localhost:5432/" + sarja;
            //Properties dbSettings = new Properties();
            //dbSettings.setProperty("user", db.getProperty("USER"));
            //dbSettings.setProperty("password", db.getProperty("PASSWORD"));
            conn = DriverManager.getConnection(dbURL, db);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
