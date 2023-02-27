package pl.projekt.alekino.domain.genre;

import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import pl.projekt.alekino.domain.validation.CustomValidator;
import pl.projekt.alekino.domain.validation.ValidationService;
import pl.projekt.alekino.domain.validation.exceptions.DuplicateException;
import pl.projekt.alekino.domain.validation.exceptions.NotFoundException;

@Service
public class GenreValidator extends CustomValidator<Genre> implements ValidationService<Genre> {

    private final GenreRepository repository;

    public GenreValidator(Validator validator, GenreRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public Genre validateExists(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(className, id));
    }

    public Genre validateExists(String name) {
        return repository.findByName(name).orElseThrow(() -> new NotFoundException(className, name));
    }

    @Override
    public void validateNotDuplicate(Genre genre) {
        repository.findByName(genre.getName()).ifPresent(a -> {
            throw new DuplicateException(className, "name", a.getName());
        });
    }
}

