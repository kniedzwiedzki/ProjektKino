package pl.projekt.alekino.domain.genre;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.alekino.domain.genre.GenreDto;
import pl.projekt.alekino.domain.genre.Genre;
import pl.projekt.alekino.domain.genre.GenreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreDto convertToDto(Genre p) {
        return modelMapper.map(p, GenreDto.class);
    }

    public Genre convertToEntity(GenreDto dto) {
        return modelMapper.map(dto, Genre.class);
    }

    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<GenreDto> getGenreById(Long id) {
        return genreRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public Optional<Genre> getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Transactional
    public GenreDto addGenre(GenreDto genreDto) {
        Genre genre = convertToEntity(genreDto);
        return convertToDto(genreRepository.save(genre));
    }

    @Transactional
    public void updateGenre(Long id, GenreDto genreDto) {
        Genre genre = convertToEntity(genreDto);
        genre.setId(id);
        genreRepository.save(genre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return genreRepository.existsById(id);
    }


    public List<Genre> getGenresByName(List<String> genresList) {
        List<Genre> result = new ArrayList<>();
        List<Genre> allGenres = genreRepository.findAll();
        for (String genreName : genresList) {
            if (allGenres.stream().anyMatch(g -> g.getName().equalsIgnoreCase(genreName))) {
                result.add(allGenres.stream().filter(g -> g.getName().equalsIgnoreCase(genreName)).findFirst().get());
            }
        }
        if (result.isEmpty()) {
            return Collections.emptyList();
        }
        return result;
    }
}
