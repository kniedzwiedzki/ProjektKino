package pl.projekt.alekino.domain.movie;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.alekino.domain.genre.GenreDto;
import pl.projekt.alekino.domain.genre.GenreService;
import pl.projekt.alekino.domain.genre.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreService genreService;
    private final ModelMapper modelMapper;

    private final MovieValidator validator;

    public MovieLongDto convertToLongDto(Movie p) {
        return modelMapper.map(p, MovieLongDto.class);
    }

    public MovieShortDto convertToShortDto(Movie p) {
        return modelMapper.map(p, MovieShortDto.class);
    }

    public <T> Movie convertToEntity(T dto) {
        return modelMapper.map(dto, Movie.class);
    }

    public List<MovieShortDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::convertToShortDto)
                .toList();
    }

    public List<MovieShortDto> getAllMovies(Optional<List<String>> genres) {
        List<Movie> movies = movieRepository.findAll();
        if (genres.isPresent()) {
            List<Genre> genresEntities = genreService.getGenresByName(genres.get());
            if (!genresEntities.isEmpty()) {
                for (Genre genre : genresEntities) {
                    movies = movies.stream()
                            .filter(movie -> movie.getGenres()
                                    .stream()
                                    .map(Genre::getId)
                                    .anyMatch(id -> id.equals(genre.getId())))
                            .toList();
                }
            }
        }
        return movies.stream()
                .map(this::convertToShortDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public MovieLongDto getMovieById(Long id) {
        return convertToLongDto(validator.validateExists(id));
    }


    @Transactional
    public MovieLongDto addMovie(MovieLongDto movie) {
        Movie movieEntity = convertToEntity(movie);
        movieEntity.setId(null);
        validateMovie(movieEntity);
        return convertToLongDto(movieRepository.save(movieEntity));
    }

    @Transactional
    public MovieLongDto updateMovie(Long id, MovieLongDto movie) {
        validator.validateExists(id);
        Movie movieEntity = convertToEntity(movie);
        movieEntity.setId(id);
        validateMovie(movieEntity);
        return convertToLongDto(movieRepository.save(movieEntity));
    }

    public void validateMovie(Movie movie) {
        validator.validateNotDuplicate(movie);
        validator.validateInput(movie);
        movie.getGenres().forEach(genre -> validator.validateGenreExists(genre.getId()));
        validator.validateAgeRestrictionExists(movie.getAgeRestriction().getId());
    }

    @Transactional
    public void deleteMovie(Long id) {
        validator.validateExists(id);
        movieRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<GenreDto> getGenresOfMovieById(Long id) {
        return validator.validateExists(id)
                .getGenres()
                .stream()
                .map(genreService::convertToDto)
                .toList();
    }

    @Transactional
    public GenreDto addGenreToMovie(Long id, GenreDto genre) {
        GenreDto newGenre = genreService.getGenreById(genre.getId());
        Movie movie = validator.validateExists(id);
        movie = validator.validateMovieNotContainsGenre(movie, newGenre.getId());
        movieRepository.save(movie);
        return newGenre;
    }

    @Transactional
    public void deleteGenreFromMovie(Long id, Long genreId) {
        Movie movie = validator.validateExists(id);
        Genre genre = validator.validateGenreExists(genreId);
        validator.validateMovieContainsGenre(movie, genre);
        movie.getGenres().remove(genre);
        movieRepository.save(movie);
    }
}

