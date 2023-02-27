package pl.projekt.alekino.domain.movie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.projekt.alekino.domain.agerestriction.AgeRestriction;
import pl.projekt.alekino.domain.agerestriction.AgeRestrictionValidator;
import pl.projekt.alekino.domain.genre.Genre;
import pl.projekt.alekino.domain.genre.GenreValidator;
import pl.projekt.alekino.domain.validation.exceptions.DuplicateException;
import pl.projekt.alekino.domain.validation.exceptions.NotContainsException;
import pl.projekt.alekino.domain.validation.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieValidatorTest {

    @Mock
    private AgeRestrictionValidator ageRestrictionValidator;

    @Mock
    private GenreValidator genreValidator;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieValidator movieValidator;

    @Test
    void testValidateExists() {
        Movie movie = new Movie(1L, "Title", "Description", 2000, "", 95, 0, List.of(new Genre(1L, "Action"), new Genre(2L, "Fantasy")), new AgeRestriction(1L, "+18", 18));
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));
        assertSame(movie, movieValidator.validateExists(123L));
        verify(movieRepository).findById(any());
    }

    @Test
    void testValidateExists2() {
        when(movieRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> movieValidator.validateExists(123L));
        verify(movieRepository).findById(any());
    }

    @Test
    void testValidateNotDuplicate() {
        Movie movie = new Movie(1L, "Title", "Description", 2000, "", 95, 0, List.of(new Genre(1L, "Action"), new Genre(2L, "Fantasy")), new AgeRestriction(1L, "+18", 18));
        when(movieRepository.findByTitle(any())).thenReturn(Optional.of(movie));
        assertThrows(DuplicateException.class, () -> movieValidator.validateNotDuplicate(movie));
        verify(movieRepository).findByTitle(any());
    }

    @Test
    void testValidateNotDuplicate2() {
        when(movieRepository.findByTitle(any())).thenReturn(Optional.empty());
        Movie movie = new Movie(1L, "Title", "Description", 2000, "", 95, 0, List.of(new Genre(1L, "Action"), new Genre(2L, "Fantasy")), new AgeRestriction(1L, "+18", 18));
        movieValidator.validateNotDuplicate(movie);
        verify(movieRepository).findByTitle(any());
        assertDoesNotThrow(() -> movieValidator.validateNotDuplicate(movie));
    }

    @Test
    void testValidateGenreExists() {
        Genre genre = new Genre(1L, "Name");
        when(genreValidator.validateExists(anyLong())).thenReturn(genre);
        assertSame(genre, movieValidator.validateGenreExists(123L));
        verify(genreValidator).validateExists(anyLong());
    }


    @Test
    void testValidateMovieNotContainsGenre2() {
        Movie movie = new Movie();
        movie.setGenres(new ArrayList<>());
        assertSame(movie, movieValidator.validateMovieNotContainsGenre(movie, 123L));
        verify(genreValidator).validateExists(anyLong());
    }

    @Test
    void testValidateMovieContainsGenre() {
        Genre genre = new Genre(2L, "Fantasy");
        Movie movie = new Movie(1L, "Title", "Description", 2000, "", 95, 0, List.of(new Genre(1L, "Action"), genre), new AgeRestriction(1L, "+18", 18));
        movieValidator.validateMovieContainsGenre(movie, genre);
        assertDoesNotThrow(() -> movieValidator.validateMovieContainsGenre(movie, genre));
    }

    @Test
    void testValidateMovieContainsGenre2() {
        Movie movie = new Movie();
        movie.setGenres(new ArrayList<>());
        assertThrows(NotContainsException.class, () -> movieValidator.validateMovieContainsGenre(movie, new Genre()));
    }

}
