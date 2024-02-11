package br.com.music.br.com.music.repository;

import br.com.music.br.com.music.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findFirstByNameIgnoreCase(String artistName);
}
