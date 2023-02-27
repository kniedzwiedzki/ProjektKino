package pl.projekt.alekino.domain.agerestriction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AgeRestrictionServiceTest {

    @Mock
    private AgeRestrictionRepository ageRestrictionRepository;

    @InjectMocks
    private AgeRestrictionService ageRestrictionService;

    @Mock
    private AgeRestrictionValidator ageRestrictionValidator;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testConvertToDto() {
        AgeRestrictionDto ageRestrictionDto = new AgeRestrictionDto();
        when(modelMapper.map(any(), any())).thenReturn(ageRestrictionDto);
        assertSame(ageRestrictionDto, ageRestrictionService.convertToDto(new AgeRestriction()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testConvertToEntity() {
        AgeRestriction ageRestriction = new AgeRestriction();
        when(modelMapper.map(any(), any())).thenReturn(ageRestriction);
        assertSame(ageRestriction, ageRestrictionService.convertToEntity(new AgeRestrictionDto()));
        verify(modelMapper).map(any(), any());
    }

    @Test
    void testGetAllAgeRestrictions() {
        when(ageRestrictionRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(ageRestrictionService.getAllAgeRestrictions().isEmpty());
        verify(ageRestrictionRepository).findAll();
    }

    @Test
    void testGetAllAgeRestrictions2() {
        ArrayList<AgeRestriction> ageRestrictionList = new ArrayList<>();
        Collections.addAll(ageRestrictionList, new AgeRestriction(), new AgeRestriction(), new AgeRestriction());
        when(ageRestrictionRepository.findAll()).thenReturn(ageRestrictionList);
        when(modelMapper.map(any(), any())).thenReturn(new AgeRestrictionDto());
        assertEquals(3, ageRestrictionService.getAllAgeRestrictions().size());
        verify(ageRestrictionRepository).findAll();
        verify(modelMapper, atLeast(1)).map(any(), any());
    }

    @Test
    void testGetAllAgeRestrictions3() {
        ArrayList<AgeRestriction> ageRestrictionList = new ArrayList<>();
        ageRestrictionList.add(new AgeRestriction());
        ageRestrictionList.add(new AgeRestriction());
        when(ageRestrictionRepository.findAll()).thenReturn(ageRestrictionList);
        when(modelMapper.map(any(), any())).thenReturn(new AgeRestrictionDto());
        assertEquals(2, ageRestrictionService.getAllAgeRestrictions().size());
        verify(ageRestrictionRepository).findAll();
        verify(modelMapper, atLeast(1)).map(any(), any());
    }

    @Test
    void testAddAgeRestriction() {
        AgeRestriction ageRestriction = new AgeRestriction(1L, "Test", 5);
        when(ageRestrictionRepository.save(any())).thenReturn(ageRestriction);
        when(modelMapper.map(any(), any())).thenReturn(ageRestriction);
        doNothing().when(ageRestrictionValidator).validateNotDuplicate(any());
        doNothing().when(ageRestrictionValidator).validateInput(any());
        assertSame(ageRestriction, ageRestrictionService.addAgeRestriction(new AgeRestrictionDto()));
        verify(ageRestrictionRepository).save(any());
        verify(modelMapper).map(any(), any());
        verify(ageRestrictionValidator).validateNotDuplicate(any());
        verify(ageRestrictionValidator).validateInput(any());
    }

}

