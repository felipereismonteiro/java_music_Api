package br.com.music.br.com.music;

import br.com.music.br.com.music.application.Main;
import br.com.music.br.com.music.repository.ArtistRepository;
import br.com.music.br.com.music.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private MusicRepository musicRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(artistRepository, musicRepository);
		main.optionProgram();
	}
}
