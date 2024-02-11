package br.com.music.br.com.music.repository;

import br.com.music.br.com.music.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Music findFirstByNameIgnoreCase(String musicName);
}
