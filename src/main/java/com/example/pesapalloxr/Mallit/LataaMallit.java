package com.example.pesapalloxr.Mallit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class LataaMallit {

    public JSONObject lataamallit() {

        try {
            URL url = getClass().getResource("/mallit/mallit.json");

            Path path = Paths.get(Objects.requireNonNull(url).toURI());

            return new JSONObject(new String(Files.readAllBytes(path)));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public HashMap<String, Double> miestenXR(JSONObject jsonObject) {
        HashMap<String, Double> miestenXrMap = new HashMap<>();

        JSONArray msumalli = jsonObject.getJSONArray("msumallikulma");

        int pituus = msumalli.length();

        for (int i = 0; i < pituus; i++) {
            miestenXrMap.put(
                msumalli.getJSONObject(i).getString("avain"),
                msumalli.getJSONObject(i).getDouble("arvo")
            );
        }

        return miestenXrMap;
    }

    public HashMap<String, Double> miestenXlapilyontiMap(JSONObject jsonObject) {
        HashMap<String, Double> miestenXCOHmap = new HashMap<>();

        JSONArray msulapilyontimalli = jsonObject.getJSONArray("msulapilyontimallikulma");

        int pituus = msulapilyontimalli.length();

        for (int i = 0; i < pituus; i++) {
            miestenXCOHmap.put(
                msulapilyontimalli.getJSONObject(i).getString("avain"),
                msulapilyontimalli.getJSONObject(i).getDouble("arvo")
            );
        }

        return miestenXCOHmap;
    }
}
