package pl.projekt.alekino.domain.movie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.projekt.alekino.domain.agerestriction.AgeRestriction;
import pl.projekt.alekino.domain.genre.Genre;
import pl.projekt.alekino.domain.genre.GenreDto;
import pl.projekt.alekino.domain.genre.GenreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private GenreService genreService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieValidator movieValidator;

    @Test
    void testConvertToLongDto() {
        MovieLongDto movieLongDto = new MovieLongDto();
        when(modelMapper.map(any(), any())).thenReturn(movieLongDto);
        assertSame(movieLongDto, movieService.convertToLongDto(new Movie()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testConvertToShortDto() {
        MovieShortDto movieShortDto = new MovieShortDto();
        when(modelMapper.map(any(), any())).thenReturn(movieShortDto);
        assertSame(movieShortDto, movieService.convertToShortDto(new Movie()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testConvertToEntity() {
        Movie movie = new Movie();
        when(modelMapper.map(any(), any())).thenReturn(movie);
        assertSame(movie, movieService.convertToEntity(new MovieShortDto()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testGetAllMovies() {
        when(movieRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(movieService.getAllMovies().isEmpty());
        verify(movieRepository).findAll();
    }

    @Test
    void testGetAllMovies2() {
        ArrayList<Movie> movieList = new ArrayList<>();
        Collections.addAll(movieList, new Movie(), new Movie(), new Movie());
        when(movieRepository.findAll()).thenReturn(movieList);
        when(modelMapper.map(any(), any())).thenReturn(new MovieShortDto());
        assertEquals(3, movieService.getAllMovies().size());
        verify(movieRepository).findAll();
        verify(modelMapper, atLeast(3)).map(any(), any());
    }

    @Test
    void testGetAllMovies3() {
        ArrayList<Movie> movieList = new ArrayList<>();
        Genre genre1 = new Genre(1L, "Action");
        Genre genre2 = new Genre(2L, "Fantasy");
        Genre genre3 = new Genre(3L, "Comedy");
        AgeRestriction ageRestriction = new AgeRestriction(1L, "+18", 18);
        Movie movie1 = new Movie(1L, "Title", "Description", 2000, "", 95, 0, List.of(genre1, genre2, genre3), ageRestriction);
        Movie movie2 = new Movie(2L, "Title", "Description", 2000, "", 95, 0, List.of(genre1, genre2), ageRestriction);
        Movie movie3 = new Movie(3L, "Title", "Description", 2000, "", 95, 0, List.of(genre1, genre3), ageRestriction);
        Collections.addAll(movieList, movie1, movie2, movie3);
        when(movieRepository.findAll()).thenReturn(movieList);
        when(genreService.getGenresByName(any())).thenReturn(List.of(genre2));
        when(modelMapper.map(any(), any())).thenReturn(new MovieShortDto());
        assertEquals(2, movieService.getAllMovies(Optional.of(List.of(genre2.getName()))).size());
        verify(movieRepository).findAll();
        verify(genreService).getGenresByName(any());
        verify(modelMapper, atLeast(1)).map(any(), any());
    }

    @Test
    void testGetAllMovies4() {
        ArrayList<Movie> movieList = new ArrayList<>();
        Genre genre1 = new Genre(1L, "Action");
        Genre genre2 = new Genre(2L, "Fantasy");
        Genre genre3 = new Genre(3L, "Comedy");
        AgeRestriction ageRestriction = new AgeRestriction(1L, "+18", 18);
        Movie movie1 = new Movie(1L, "Title", "Description", 2000, "", 95, 0, List.of(genre1, genre2, genre3), ageRestriction);
        Movie movie2 = new Movie(2L, "Title", "Description", 2000, "", 95, 0, List.of(genre1, genre2), ageRestriction);
        Movie movie3 = new Movie(3L, "Title", "Description", 2000, "", 95, 0, List.of(genre1, genre3), ageRestriction);
        Collections.addAll(movieList, movie1, movie2, movie3);
        when(movieRepository.findAll()).thenReturn(movieList);
        ArrayList<Genre> genreList = new ArrayList<>();
        Collections.addAll(genreList, genre1, genre2);
        when(genreService.getGenresByName(any())).thenReturn(genreList);
        assertEquals(2, movieService.getAllMovies(Optional.of(genreList.stream().map(Genre::getName).toList())).size());
    }

    @Test
    void testGetMovieById() {
        MovieLongDto movieLongDto = new MovieLongDto();
        when(modelMapper.map(any(), any())).thenReturn(movieLongDto);
        when(movieValidator.validateExists(anyLong())).thenReturn(new Movie());
        assertSame(movieLongDto, movieService.getMovieById(123L));
        verify(modelMapper).map(any(), any());
        verify(movieValidator).validateExists(anyLong());
    }

    @Test
    void testValidateMovie() {
        when(movieValidator.validateGenreExists(anyLong())).thenReturn(new Genre());
        when(movieValidator.validateAgeRestrictionExists(anyLong())).thenReturn(new AgeRestriction());
        doNothing().when(movieValidator).validateNotDuplicate(any());
        doNothing().when(movieValidator).validateInput(any());

        ArrayList<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(123L, "Name"));
        Movie movie = mock(Movie.class);
        when(movie.getGenres()).thenReturn(genreList);
        when(movie.getAgeRestriction()).thenReturn(new AgeRestriction(123L, "Name", 1));
        movieService.validateMovie(movie);
        verify(movieValidator).validateAgeRestrictionExists(anyLong());
        verify(movieValidator).validateGenreExists(anyLong());
        verify(movieValidator).validateNotDuplicate(any());
        verify(movieValidator).validateInput(any());
        verify(movie).getGenres();
        verify(movie).getAgeRestriction();
    }

    @Test
    void testDeleteMovie() {
        doNothing().when(movieRepository).deleteById(any());
        when(movieValidator.validateExists(anyLong())).thenReturn(new Movie());
        movieService.deleteMovie(123L);
        verify(movieRepository).deleteById(any());
        verify(movieValidator).validateExists(anyLong());
    }

    @Test
    void testGetGenresOfMovieById() {
        Movie movie = new Movie();
        movie.setGenres(new ArrayList<>());
        when(movieValidator.validateExists(anyLong())).thenReturn(movie);
        assertTrue(movieService.getGenresOfMovieById(123L).isEmpty());
        verify(movieValidator).validateExists(anyLong());
    }

    @Test
    void testGetGenresOfMovieById2() {
        Movie movie = new Movie();
        Genre genre1 = new Genre(1L, "Name1");
        Genre genre2 = new Genre(2L, "Name2");
        Genre genre3 = new Genre(3L, "Name3");
        movie.setGenres(List.of(genre1, genre2, genre3));
        when(movieValidator.validateExists(anyLong())).thenReturn(movie);
        assertEquals(3, movieService.getGenresOfMovieById(123L).size());
        verify(movieValidator).validateExists(anyLong());
    }

    @Test
    void testAddGenreToMovie() {
        when(movieRepository.save(any())).thenReturn(new Movie());
        GenreDto genreDto = new GenreDto();
        when(genreService.getGenreById(any())).thenReturn(genreDto);
        when(movieValidator.validateExists(anyLong())).thenReturn(new Movie());
        when(movieValidator.validateMovieNotContainsGenre(any(), any())).thenReturn(new Movie());
        assertSame(genreDto, movieService.addGenreToMovie(123L, new GenreDto()));
        verify(movieRepository).save(any());
        verify(genreService).getGenreById(any());
        verify(movieValidator).validateExists(anyLong());
        verify(movieValidator).validateMovieNotContainsGenre(any(), any());
    }

    @Test
    void testDeleteGenreFromMovie() {
        when(movieRepository.save(any())).thenReturn(new Movie());
        when(movieValidator.validateGenreExists(anyLong())).thenReturn(new Genre());
        ArrayList<Genre> genres = new ArrayList<>();
        when(movieValidator.validateExists(anyLong())).thenReturn(
                new Movie(123L, "Dr", "Dr", 1, "https://example.org/example", 1, 10.0d, genres, new AgeRestriction()));
        when(movieValidator.validateMovieContainsGenre(any(), any())).thenReturn(new Movie());
        movieService.deleteGenreFromMovie(123L, 123L);
        verify(movieRepository).save(any());
        verify(movieValidator).validateGenreExists(anyLong());
        verify(movieValidator).validateExists(anyLong());
        verify(movieValidator).validateMovieContainsGenre(any(), any());
    }

}
