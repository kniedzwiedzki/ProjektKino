package pl.projekt.alekino.domain.movie;

import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import pl.projekt.alekino.domain.agerestriction.AgeRestriction;
import pl.projekt.alekino.domain.agerestriction.AgeRestrictionValidator;
import pl.projekt.alekino.domain.genre.Genre;
import pl.projekt.alekino.domain.genre.GenreValidator;
import pl.projekt.alekino.domain.validation.CustomValidator;
import pl.projekt.alekino.domain.validation.ValidationService;
import pl.projekt.alekino.domain.validation.exceptions.DuplicateException;
import pl.projekt.alekino.domain.validation.exceptions.NotContainsException;
import pl.projekt.alekino.domain.validation.exceptions.NotFoundException;

@Service
public class MovieValidator extends CustomValidator<Movie> implements ValidationService<Movie> {

    private final MovieRepository repository;
    private final GenreValidator genreValidator;
    private final AgeRestrictionValidator ageRestrictionValidator;

    public MovieValidator(Validator validator, MovieRepository repository, GenreValidator genreValidator, AgeRestrictionValidator ageRestrictionValidator) {
        super(validator);
        this.repository = repository;
        this.genreValidator = genreValidator;
        this.ageRestrictionValidator = ageRestrictionValidator;
    }

    @Override
    public Movie validateExists(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(className, id));
    }

    @Override
    public void validateNotDuplicate(Movie movie) {
        repository.findByTitle(movie.getTitle()).ifPresent(a -> {
            throw new DuplicateException(className, "title", a.getTitle());
        });
    }

    public Genre validateGenreExists(long id) {
        return genreValidator.validateExists(id);
    }

    public AgeRestriction validateAgeRestrictionExists(long id) {
        return ageRestrictionValidator.validateExists(id);
    }

    public Movie validateMovieNotContainsGenre(Movie movie, Long id) {
        movie.getGenres().stream()
                .filter(genre -> genre.getId().equals(id))
                .findAny()
                .ifPresent(genre -> {
                    throw new DuplicateException(className, "genre", genre.getName());
                });
        movie.getGenres().add(genreValidator.validateExists(id));
        return movie;
    }

    public Movie validateMovieContainsGenre(Movie movie, Genre genre) {
        if (movie.getGenres().stream().noneMatch(g -> g.getId().equals(genre.getId())))
            throw new NotContainsException(className, "genre", genre.getName());
        return movie;
    }
}

