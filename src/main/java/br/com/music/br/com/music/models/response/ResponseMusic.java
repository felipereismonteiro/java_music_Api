package br.com.music.br.com.music.models.response;

import java.util.List;

public record ResponseMusic(Results results) {
    public record Results(Trackmatches trackmatches) {
        public record Trackmatches(List<Track> track) {}

        public record Track(String name, String artist, String url) {
        }
    }
}
