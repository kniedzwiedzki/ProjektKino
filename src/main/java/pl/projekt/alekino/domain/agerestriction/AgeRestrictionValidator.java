package pl.projekt.alekino.domain.agerestriction;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.core.GenericTypeResolver;
import pl.projekt.alekino.domain.validation.CustomValidator;
import pl.projekt.alekino.domain.validation.ValidationService;
import pl.projekt.alekino.domain.validation.exceptions.DuplicateException;
import pl.projekt.alekino.domain.validation.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AgeRestrictionValidator extends CustomValidator<AgeRestriction> implements ValidationService<AgeRestriction> {

    private final AgeRestrictionRepository repository;

    public AgeRestrictionValidator(Validator validator, AgeRestrictionRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public AgeRestriction validateExists(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(className, id));
    }

    @Override
    public void validateNotDuplicate(AgeRestriction ageRestriction) {
        repository.findByName(ageRestriction.getName()).ifPresent(a -> {
            throw new DuplicateException(className, "name", a.getName());
        });
    }
}

