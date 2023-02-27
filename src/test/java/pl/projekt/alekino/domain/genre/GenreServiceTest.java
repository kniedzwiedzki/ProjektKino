package pl.projekt.alekino.domain.genre;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.projekt.alekino.domain.validation.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreValidator genreValidator;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testConvertToDto() {
        GenreDto genreDto = new GenreDto();
        when(modelMapper.map(any(), any())).thenReturn(genreDto);
        assertSame(genreDto, genreService.convertToDto(new Genre()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testConvertToDto2() {
        when(modelMapper.map(any(), any()))
                .thenThrow(new NotFoundException("Classname", "Name"));
        assertThrows(NotFoundException.class, () -> genreService.convertToDto(new Genre()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testConvertToEntity() {
        Genre genre = new Genre();
        when(modelMapper.map(any(), any())).thenReturn(genre);
        assertSame(genre, genreService.convertToEntity(new GenreDto()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testConvertToEntity2() {
        when(modelMapper.map(any(), any())).thenThrow(new NotFoundException("Classname", "Name"));
        assertThrows(NotFoundException.class, () -> genreService.convertToEntity(new GenreDto()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testGetAllGenres() {
        when(genreRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(genreService.getAllGenres().isEmpty());
        verify(genreRepository).findAll();
    }

    @Test
    void testGetAllGenres2() {
        ArrayList<Genre> genreList = new ArrayList<>();
        Collections.addAll(genreList, new Genre(1L, "Name"), new Genre(2L, "Name2"), new Genre(3L, "Name3"));
        when(genreRepository.findAll()).thenReturn(genreList);
        assertEquals(3, genreService.getAllGenres().size());
        verify(genreRepository).findAll();
        verify(modelMapper, atMost(3)).map(any(), any());
    }

    @Test
    void testGetGenreById() {
        GenreDto genreDto = new GenreDto();
        when(modelMapper.map(any(), any())).thenReturn(genreDto);
        when(genreValidator.validateExists(anyLong())).thenReturn(new Genre(1L, "Name"));
        assertSame(genreDto, genreService.getGenreById(123L));
        verify(modelMapper).map(any(), any());
        verify(genreValidator).validateExists(anyLong());
    }

    @Test
    void testGetGenresByName() {
        when(genreRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(genreService.getGenresByName(new ArrayList<>()).isEmpty());
        verify(genreRepository).findAll();
    }

    @Test
    void testGetGenresByName3() {
        ArrayList<Genre> genreList = new ArrayList<>();
        Collections.addAll(genreList, new Genre(1L, "Name"), new Genre(2L, "foo1"), new Genre(3L, "abc"));
        when(genreRepository.findAll()).thenReturn(genreList);
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo1");
        assertEquals(1, genreService.getGenresByName(stringList).size());
        verify(genreRepository).findAll();
    }

    @Test
    void testGetGenresByName4() {
        ArrayList<Genre> genreList = new ArrayList<>();
        Collections.addAll(genreList, new Genre(1L, "Name"), new Genre(2L, "foo1"), new Genre(3L, "abc"));
        when(genreRepository.findAll()).thenReturn(genreList);
        ArrayList<String> stringList = new ArrayList<>();
        Collections.addAll(stringList, "fOO1", "ABc");
        assertEquals(2, genreService.getGenresByName(stringList).size());
        verify(genreRepository).findAll();
    }

}

