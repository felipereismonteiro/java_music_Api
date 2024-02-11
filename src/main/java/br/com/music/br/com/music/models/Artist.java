package br.com.music.br.com.music.models;

import br.com.music.br.com.music.models.response.ResponseArtist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column()
    @Basic
    @NotBlank
    @NotNull
    private String name;
    @Column()
    @Basic
    @NotNull
    @NotBlank
    private String summary;
    @Column(nullable = true)
    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Music> musics;

    public Artist(){}

    public Artist(ResponseArtist artist) {
        this.name = artist.artist().name();
        String summary = artist.artist().bio().summary();
        if (summary.length() > 255) {
            this.summary = summary.substring(0, 244).trim().concat("...");
        } else {
            this.summary = summary;
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", musics=" + musics +
                '}';
    }
}
