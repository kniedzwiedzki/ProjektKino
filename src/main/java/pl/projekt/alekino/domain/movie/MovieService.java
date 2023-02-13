package pl.projekt.alekino.domain.movie;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.alekino.domain.genre.GenreDto;
import pl.projekt.alekino.domain.genre.GenreService;
import pl.projekt.alekino.domain.movie.MovieLongDto;
import pl.projekt.alekino.domain.movie.MovieShortDto;
import pl.projekt.alekino.domain.genre.Genre;
import pl.projekt.alekino.domain.movie.Movie;
import pl.projekt.alekino.domain.movie.MovieRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreService genreService;
    private final ModelMapper modelMapper;

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

    public List<MovieShortDto> getAllMovies(List<String> genres) {
        List<Movie> movies = movieRepository.findAll();

        List<Genre> genresEntities = genreService.getGenresByName(genres);
        if (genresEntities.isEmpty()) {
            return movies.stream()
                    .map(this::convertToShortDto)
                    .toList();
        } else {
            for (Genre genre : genresEntities) {
                movies = movies.stream()
                        .filter(movie -> movie.getGenres()
                                .stream()
                                .map(Genre::getId)
                                .anyMatch(id -> id.equals(genre.getId())))
                        .toList();
            }
            return movies.stream()
                    .map(this::convertToShortDto)
                    .toList();
        }

    }

    @Transactional(readOnly = true)
    public Optional<MovieLongDto> getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::convertToLongDto);
    }

    @Transactional
    public Optional<MovieLongDto> addMovie(MovieLongDto movie) {
        Movie movieEntity = convertToEntity(movie);
        movieEntity.setId(null);
        return Optional.of(convertToLongDto(movieRepository.save(movieEntity)));
    }

    @Transactional
    public Optional<MovieLongDto> updateMovie(Long id, MovieLongDto movie) {
        if (!movieRepository.existsById(id))
            return Optional.empty();
        Movie movieEntity = convertToEntity(movie);
        movieEntity.setId(id);
        return Optional.of(convertToLongDto(movieRepository.save(movieEntity)));
    }

    @Transactional
    public boolean deleteMovie(Long id) {
        if (!movieRepository.existsById(id))
            return false;
        movieRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<GenreDto> getGenresOfMovieById(Long id) {
        return movieRepository.findById(id)
                .map(value -> value.getGenres()
                        .stream()
                        .map(genreService::convertToDto)
                        .toList())
                .orElse(Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return movieRepository.existsById(id);
    }

    @Transactional
    public Optional<GenreDto> addGenreToMovie(Long id, GenreDto genre) {
        Optional<GenreDto> newGenre = genreService.getGenreById(genre.getId());
        if (newGenre.isEmpty())
            return Optional.empty();
        Movie movie = movieRepository.findById(id).get();
        if (movie.getGenres().stream().map(Genre::getId).noneMatch(genre.getId()::equals)) {
            movie.getGenres().add(genreService.convertToEntity(newGenre.get()));
            movieRepository.save(movie);
            return newGenre;
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteGenreFromMovie(Long id, Long genreId) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty())
            return false;
        Optional<Genre> genre = genreService.getGenreById(genreId)
                .map(genreService::convertToEntity);
        if (genre.isEmpty())
            return false;

        if (movie.get().getGenres().contains(genre.get())) {
            movie.get().getGenres().remove(genre.get());
            movieRepository.save(movie.get());
            return true;
        }
        return false;
    }
}
