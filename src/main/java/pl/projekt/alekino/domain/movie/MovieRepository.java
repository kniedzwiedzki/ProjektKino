package pl.projekt.alekino.domain.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projekt.alekino.domain.movie.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
