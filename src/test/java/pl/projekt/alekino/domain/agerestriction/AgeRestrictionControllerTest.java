package pl.projekt.alekino.domain.agerestriction;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AgeRestrictionControllerTest {
    @Mock
    private AgeRestrictionService ageRestrictionService;

    @Mock
    private AgeRestrictionRepository ageRestrictionRepo;

    @Mock
    private AgeRestrictionValidator ageRestrictionValidator;

    @InjectMocks
    private AgeRestrictionsController ageRestrictionController;

    @Test
    public void testGetAllAgeRestrictions_ReturnSuccess() {
        List<AgeRestrictionDto> ageRestriction = new ArrayList<>();
        ageRestriction.add(new AgeRestrictionDto(1L, "1",13 ));
        ageRestriction.add(new AgeRestrictionDto(2L, "2",13 ));
        when(ageRestrictionService.getAllAgeRestrictions()).thenReturn(ageRestriction);

        ResponseEntity<List<AgeRestrictionDto>> response = ageRestrictionController.getAllAgeRestrictions();

        HttpStatus expectedStatus = ageRestriction.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        assertEquals(response.getStatusCode(), expectedStatus);
        assertEquals(response.getBody(), ageRestriction);
    }
}


