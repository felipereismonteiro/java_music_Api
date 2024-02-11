package br.com.music.br.com.music.application;

import br.com.music.br.com.music.models.Artist;
import br.com.music.br.com.music.models.Music;
import br.com.music.br.com.music.models.response.ResponseMusic;
import br.com.music.br.com.music.repository.ArtistRepository;
import br.com.music.br.com.music.repository.MusicRepository;
import br.com.music.br.com.music.service.API;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Gson gson = new Gson();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String WELLCOME_MESSAGE = "Escolha sua opção entre:\n" +
            "\n" +
            "1 - Cadastrar artistas\n" +
            "2 - Cadastrar musica\n" +
            "3 - Listar artistas\n" +
            "4 - Listar musicas\n" +
            " \n" +
            "0 - Sair\n";
    private final ArtistRepository artistRepository;
    private final MusicRepository musicRepository;

    public Main(ArtistRepository artistRepository, MusicRepository musicRepository) {
        this.artistRepository = artistRepository;
        this.musicRepository = musicRepository;
    }

    private static int chooseOption() {
        System.out.println(WELLCOME_MESSAGE);
        int option = scanner.nextInt();
        scanner.nextLine();

        return option;
    }

    public void optionProgram() {
        int opcao;
        do {
            opcao = chooseOption();
            try {
                switch (opcao) {
                    case 1:
                        cadastrarArtista();
                        break;
                    case 2:
                        cadastrarMusicas();
                        break;
                    case 3:
                        listarArtistas();
                        break;
                    case 4:
                        listarMusicas();
                        break;
                    case 0:
                        System.out.println("Saindo da aplicacao...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }

            } catch (InputMismatchException er) {
                System.out.println("Seu input nao obteve uma opção valida, tente novamente mais tarde...");
            }
        } while (opcao != 0);
    }

    private void listarMusicas() {
        List<Music> musics = musicRepository.findAll();
        musics.forEach(System.out::println);
    }

    private void listarArtistas() {
        List<Artist> artists = artistRepository.findAll();
        artists.forEach(System.out::println);
    }

    public void cadastrarArtista() {
        System.out.print("Digite o nome do artista: ");
        String artistName = scanner.nextLine();
        Artist artist = API.getArtistInfos(artistName);
        Artist artistFounded = artistRepository.findFirstByNameIgnoreCase(artistName);

        if (artistFounded == null) {
            artistRepository.save(artist);
        } else {
            System.out.println("Artista Já cadastrado!!!");
        }
    }

    public void cadastrarMusicas() {
        System.out.print("Digite o nome da musica: ");
        String musicName = scanner.nextLine();

        try {
            ResponseMusic musicSearched = new API().getMusicInfos(musicName);
            ResponseMusic.Results.Track MusicInfos = musicSearched.results().trackmatches().track().get(0);
            Artist foundedArtist = artistRepository.findFirstByNameIgnoreCase(MusicInfos.artist());
            Artist artist = API.getArtistInfos(MusicInfos.artist());
            Music musicFounded = musicRepository.findFirstByNameIgnoreCase(MusicInfos.artist());
            Music newMusic = new Music(musicSearched, artist);

            if (foundedArtist != null) {
                if (musicFounded == null) {
                    musicRepository.save(new Music(musicSearched, foundedArtist));
                } else {
                    System.out.println("Música já cadastrada!");
                }
            } else {
                System.out.println("Artista da música escolhida não encontrado na base de dados");
                System.out.println("Adicionando artista à lista!!!");

                artistRepository.save(artist);
                musicRepository.save(newMusic);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
