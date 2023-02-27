package pl.projekt.alekino.domain.genre;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.projekt.alekino.domain.validation.exceptions.DuplicateException;
import pl.projekt.alekino.domain.validation.exceptions.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreValidatorTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreValidator genreValidator;

    @Test
    void testValidateExists() {
        Genre genre = new Genre();
        when(genreRepository.findById(any())).thenReturn(Optional.of(genre));
        assertSame(genre, genreValidator.validateExists(123L));
        verify(genreRepository).findById(any());
    }

    @Test
    void testValidateExists2() {
        when(genreRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> genreValidator.validateExists(123L));
        verify(genreRepository).findById(any());
    }

    @Test
    void testValidateExists4() {
        Genre genre = new Genre();
        when(genreRepository.findByName(any())).thenReturn(Optional.of(genre));
        assertSame(genre, genreValidator.validateExists("Name"));
        verify(genreRepository).findByName(any());
    }

    @Test
    void testValidateExists5() {
        when(genreRepository.findByName(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> genreValidator.validateExists("Name"));
        verify(genreRepository).findByName(any());
    }

    @Test
    void testValidateNotDuplicate() {
        when(genreRepository.findByName(any())).thenReturn(Optional.of(new Genre()));
        assertThrows(DuplicateException.class, () -> genreValidator.validateNotDuplicate(new Genre()));
        verify(genreRepository).findByName(any());
    }

    @Test
    void testValidateNotDuplicate2() {
        when(genreRepository.findByName(any())).thenReturn(Optional.of(new Genre(1L, "Name")));
        assertThrows(DuplicateException.class, () -> genreValidator.validateNotDuplicate(new Genre(2L, "Name2")));
        verify(genreRepository).findByName(any());
    }

    @Test
    void testValidateNotDuplicate3() {
        when(genreRepository.findByName(any())).thenReturn(Optional.empty());
        genreValidator.validateNotDuplicate(new Genre());
        verify(genreRepository).findByName(any());
    }

}

