package pl.projekt.alekino.domain.validation;

import org.springframework.stereotype.Component;

@Component
public interface ValidationService<T> {

    T validateExists(long id);

    void validateNotDuplicate(T t);
}
