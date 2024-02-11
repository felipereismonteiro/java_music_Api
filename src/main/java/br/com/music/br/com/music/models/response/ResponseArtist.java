package br.com.music.br.com.music.models.response;

public record ResponseArtist(Artist artist) {
    public record Artist(String name, Bio bio){}
    public record Bio(String summary) {}
}

