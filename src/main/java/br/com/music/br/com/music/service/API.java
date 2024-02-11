package br.com.music.br.com.music.service;

import br.com.music.br.com.music.models.Artist;
import br.com.music.br.com.music.models.response.ResponseArtist;
import br.com.music.br.com.music.models.response.ResponseMusic;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    private static final String URL_BASE = "http://ws.audioscrobbler.com/2.0/";
    private static final Gson gson = new Gson();

    private static String lastFmConnection(String urlBase) throws IOException {
        URL url = new URL(urlBase);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    public static Artist getArtistInfos(String artistName) {
        String url = URL_BASE +
                "?method=artist.getinfo&artist=" +
                artistName.replace(" " , "+")
                + "&api_key=" +
                System.getenv("API_KEY") +
                "&format=json";
        try {
            return new Artist(gson.fromJson(lastFmConnection(url), ResponseArtist.class));
        } catch (IOException er) {
            System.out.println(er.getMessage());
        }
        return null;
    }

    public ResponseMusic getMusicInfos(String musicName) throws IOException {
        String url = URL_BASE +
                "?method=track.search" +
                "&track=" +
                musicName.replace(" " , "+")
                + "&api_key=" +
                System.getenv("API_KEY") +
                "&format=json";
        return gson.fromJson(lastFmConnection(url), ResponseMusic.class);
    }
}
