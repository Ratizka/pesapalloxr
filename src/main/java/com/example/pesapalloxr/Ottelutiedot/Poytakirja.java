package com.example.pesapalloxr.Ottelutiedot;

import com.example.pesapalloxr.DataClasses.Kotiutustilanteet;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Poytakirja {

    private static String tilanne(JSONArray runnersAtBases) {
        if (runnersAtBases == null) {
            return "";
        }

        if (runnersAtBases.optInt(3, 0) != 0 && runnersAtBases.optInt(2, 0) != 0 && runnersAtBases.optInt(1, 0) != 0) {
            return "ajo";
        } else if (runnersAtBases.optInt(3, 0) != 0 && runnersAtBases.optInt(2, 0) != 0 && runnersAtBases.optInt(1, 0) == 0) {
            return "2-3";
        } else if (runnersAtBases.optInt(3, 0) != 0 && runnersAtBases.optInt(2, 0) == 0 && runnersAtBases.optInt(1, 0) != 0) {
            return "1-3";
        } else if (runnersAtBases.optInt(3, 0) != 0 && runnersAtBases.optInt(2, 0) == 0 && runnersAtBases.optInt(1, 0) == 0) {
            return "0-3";
        }

        return "";
    }

    private static Integer etenijaid(JSONArray runnersAtBases) {
        if (runnersAtBases == null) {
            return 0;
        }

        return runnersAtBases.optInt(3, 0);
    }

    public JSONArray poytakirja(String otteluid) {
        try {

            URL url = getClass().getResource("/properties/.properties");

            Path path = Paths.get(Objects.requireNonNull(url).toURI());

            Properties env = new Properties();

            env.load(Files.newInputStream(path));

            String peli = "https://api.pesistulokset.fi/api/v1/online/" + otteluid + "/events?apikey="
                + env.getProperty("apikey");

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(peli)).GET().
                headers("Content-Type", "application/json").build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());

            client.close();

            return jsonObject.getJSONArray("events");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Kotiutustilanteet> poytakirjakasittely(JSONArray events) {
        try {
            ArrayList<Kotiutustilanteet> kotiutustilanteet = new ArrayList<>();

            for (int i = 0; i < events.length(); i++) {
                JSONArray eventslist = events.getJSONObject(i).getJSONArray("events");

                int karkilyonnit = 0;
                int palot = 0;
                int juoksut = 0;
                int karkilyonnitkotia = 0;
                int karkilyontikotia = 0;
                int karkilyontiepaonnistuminenkotia = 0;
                int kunnari = 0;

                JSONArray lahtotilannelista = eventslist.getJSONObject(0).optJSONArray("runnersAtBases");
                JSONArray lopputilannelista = eventslist.getJSONObject(eventslist.length() - 1).optJSONArray("runnersAtBases");

                Integer jakso = events.getJSONObject(i).optInt("period");
                Integer vuoro = events.getJSONObject(i).optInt("inning");
                Integer joukkue = events.getJSONObject(i).optInt("team");
                Integer batter = events.getJSONObject(i).optInt("batter");
                Integer hitnumber = events.getJSONObject(i).optInt("hitNumber");
                Integer etenijanID = etenijaid(lahtotilannelista);

                JSONObject hit = events.getJSONObject(i).optJSONObject("hit");

                String lahtotilanne = tilanne(lahtotilannelista);
                String lopputilanne = tilanne(lopputilannelista);

                boolean koppi = false;

                if (hit != null) {
                    koppi = hit.optBoolean("caught");
                }

                for (int j = 0; j < eventslist.length(); j++) {
                    JSONArray texts = eventslist.getJSONObject(j).optJSONArray("texts");

                    for (int k = 0; k < texts.length(); k++) {
                        if (texts.optJSONObject(k) != null) {

                            if (texts.optJSONObject(k).has("pointhits")) {
                                karkilyonnit += 1;
                            }
                            if (texts.optJSONObject(k).has("score")) {
                                juoksut += 1;
                            }
                            if (texts.optJSONObject(k).optInt("pointhits") == 3) {
                                karkilyonnitkotia += 1;
                                karkilyontikotia = 1;
                            }
                            if (texts.optJSONObject(k).optInt("pointhitf") == 3) {
                                karkilyontiepaonnistuminenkotia = 1;
                            }
                            if (texts.optJSONObject(k).optInt("out") == 1) {
                                palot += 1;
                            }
                            if (texts.optJSONObject(k).has("homerun")) {
                                kunnari = 1;
                            }
                        }
                    }
                }

                kotiutustilanteet.add(new Kotiutustilanteet(i, jakso, vuoro, joukkue ,batter, hitnumber, etenijanID, koppi,
                        karkilyonnit, kunnari, palot, juoksut, karkilyontikotia, karkilyonnitkotia, karkilyontiepaonnistuminenkotia,
                        lahtotilanne, lopputilanne
                    )
                );
            }

            List<Kotiutustilanteet> filterarray = kotiutustilanteet.stream().filter(
                i -> (i.getKarkilyontikotia() == 1
                    | i.getKarkilyontiepaonnistuminenkotia() == 1)
            ).toList();

            return FXCollections.observableArrayList(filterarray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
