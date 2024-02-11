package br.com.music.br.com.music.models;

import br.com.music.br.com.music.models.response.ResponseMusic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Basic
    @NotBlank
    @Range(min = 3, max = 100, message = "Nome minimo de caracteres de 3 e maximo de 100")
    private String name;
    @Column
    @Basic
    @NotBlank
    private String url;
    @NotNull
    @NotBlank
    @ManyToOne
    private Artist artist;

    public Music() {}

    public Music(ResponseMusic music, Artist artist) {
        this.url = music.results().trackmatches().track().get(0).url();
        this.name = music.results().trackmatches().track().get(0).name();
        this.artist = artist;
    }

    public String getNome() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
