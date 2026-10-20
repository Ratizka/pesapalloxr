package com.example.pesapalloxr.Ottelutiedot;

import com.example.pesapalloxr.DataClasses.Pelaajat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class Ottelutiedot {

    public JSONObject haeOttelunTiedot(String otteluid) {
        try {
            URL url = getClass().getResource("/properties/.properties");

            Path path = Paths.get(Objects.requireNonNull(url).toURI());

            Properties env = new Properties();

            env.load(Files.newInputStream(path));

            String ilma = "https://api.pesistulokset.fi/api/v1/public/match?id=" + otteluid + "&apikey="
                + env.getProperty("apikey");

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ilma)).GET().headers(
                "Content-Type", "application/json").build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());

            client.close();

            if (jsonObject.has("error")) {
                return null;
            }
            return jsonObject;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public ObservableList<Pelaajat> suodatakotiPelaajat(JSONObject jsonObject) {

        ObservableList<Pelaajat> pelaajaLista = FXCollections.observableArrayList();

        JSONArray kotiPelaajat = jsonObject.getJSONObject("home").getJSONArray("players");

        int kotipelaajapituus = kotiPelaajat.length();

        int seuraID = jsonObject.getJSONObject("home").getInt("sport_club_id");
        int joukkueID = jsonObject.getJSONObject("home").getInt("id");
        String joukkue = jsonObject.getJSONObject("home").getString("name");

        for (int i = 0; i < kotipelaajapituus; i++) {

            JSONObject pelaaja = kotiPelaajat.getJSONObject(i);

            pelaajaLista.add(
                new Pelaajat(pelaaja.getInt("id"), pelaaja.getInt("number"), pelaaja.getString("name"),
                    seuraID, joukkueID, joukkue
                )
            );
        }
        return pelaajaLista;
    }

    public ObservableList<Pelaajat> suodatavierasPelaajat(JSONObject jsonObject) {

        ObservableList<Pelaajat> pelaajaLista = FXCollections.observableArrayList();

        JSONArray kotiPelaajat = jsonObject.getJSONObject("away").getJSONArray("players");

        int pelaajaListaPituus = kotiPelaajat.length();

        int seuraID = jsonObject.getJSONObject("away").getInt("sport_club_id");
        int joukkueID = jsonObject.getJSONObject("away").getInt("id");
        String joukkue = jsonObject.getJSONObject("away").getString("name");

        pelaajaLista.add(
            new Pelaajat(
                0, 0, " ",
                seuraID, joukkueID, joukkue
            )
        );

        for (int i = 0; i < pelaajaListaPituus; i++) {

            JSONObject pelaaja = kotiPelaajat.getJSONObject(i);

            pelaajaLista.add(
                new Pelaajat(pelaaja.getInt("id"), pelaaja.getInt("number"), pelaaja.getString("name"),
                    seuraID, joukkueID, joukkue
                )
            );
        }

        return pelaajaLista;
    }


}