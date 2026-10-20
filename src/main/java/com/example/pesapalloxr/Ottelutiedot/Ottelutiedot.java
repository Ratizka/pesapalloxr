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

    public JSONObject haeotteluntiedot(String otteluid) {
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

    public ObservableList<Pelaajat> kotipelaajat(JSONObject jsonObject) {

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
        return kotilyojatlista;
    }

    public ObservableList<Pelaajat> vieraspelaajat(JSONObject jsonObject) {

        ObservableList<Pelaajat> vieraslyojatlista = FXCollections.observableArrayList();

        JSONArray vieraspelaajalista = jsonObject.getJSONObject("away").getJSONArray("players");

        int vierasjoukkueID = jsonObject.getJSONObject("away").getInt("sport_club_id");

        String vierasjoukkue = jsonObject.getJSONObject("away").getString("name");

        vieraslyojatlista.add(
            new Pelaajat(
                0, 0, " ",
                vierasjoukkueID, vierasjoukkue
            )
        );

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

        return vieraslyojatlista;
    }


}