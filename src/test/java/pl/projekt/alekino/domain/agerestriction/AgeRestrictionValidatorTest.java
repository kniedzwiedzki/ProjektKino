package pl.projekt.alekino.domain.agerestriction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.projekt.alekino.domain.validation.exceptions.DuplicateException;
import pl.projekt.alekino.domain.validation.exceptions.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AgeRestrictionValidatorTest {

    @Mock
    private AgeRestrictionRepository ageRestrictionRepository;
    @InjectMocks
    private AgeRestrictionValidator ageRestrictionValidator;

    @Test
    void testValidateExists() {
        AgeRestriction ageRestriction = new AgeRestriction(1L, "G", 0);
        when(ageRestrictionRepository.findById(1L)).thenReturn(Optional.of(ageRestriction));
        assertSame(ageRestriction, ageRestrictionValidator.validateExists(1L));
        verify(ageRestrictionRepository).findById(any());
    }

    @Test
    void testValidateExists2() {
        when(ageRestrictionRepository.findById( any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ageRestrictionValidator.validateExists(123L));
        verify(ageRestrictionRepository).findById(any());
    }

    @Test
    void testValidateNotDuplicate() {
        when(ageRestrictionRepository.findByName(any())).thenReturn(Optional.of(new AgeRestriction()));
        assertThrows(DuplicateException.class, () -> ageRestrictionValidator.validateNotDuplicate(new AgeRestriction()));
        verify(ageRestrictionRepository).findByName(any());
    }

    @Test
    void testValidateNotDuplicate2() {
        AgeRestriction ageRestriction = mock(AgeRestriction.class);
        when(ageRestriction.getName()).thenThrow(new DuplicateException("Class Name", "Property", "Name"));
        Optional<AgeRestriction> ofResult = Optional.of(ageRestriction);
        when(ageRestrictionRepository.findByName(any())).thenReturn(ofResult);
        assertThrows(DuplicateException.class, () -> ageRestrictionValidator.validateNotDuplicate(new AgeRestriction()));
        verify(ageRestrictionRepository).findByName(any());
        verify(ageRestriction).getName();
    }

    @Test
    void testValidateNotDuplicate3() {
        when(ageRestrictionRepository.findByName(any())).thenReturn(Optional.empty());
        AgeRestriction ageRestriction = new AgeRestriction();
        ageRestrictionValidator.validateNotDuplicate(ageRestriction);
        verify(ageRestrictionRepository).findByName(any());
        assertEquals(0, ageRestriction.getMinAge());
    }

}

