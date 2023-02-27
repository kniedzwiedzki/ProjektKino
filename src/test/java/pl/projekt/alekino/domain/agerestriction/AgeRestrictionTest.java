package pl.projekt.alekino.domain.agerestriction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AgeRestrictionTest {

    @Test
    void testEquals() {
        AgeRestriction ageRestriction1 = new AgeRestriction(1L,"1",13);
        AgeRestriction ageRestriction2 = new AgeRestriction(2l,"2",18);
        AgeRestriction ageRestriction3 = new AgeRestriction(1l,"1",13);

        assertEquals(false, ageRestriction1.equals(ageRestriction2));
        assertEquals(true, ageRestriction1.equals(ageRestriction3));
    }

    @Test
    void testHashCode() {
        AgeRestriction ageRestriction1 = new AgeRestriction(1L, "1",13);
        AgeRestriction ageRestriction2 = new AgeRestriction(2L, "2",18);

        assertEquals(false, ageRestriction1.hashCode() == ageRestriction2.hashCode());
    }

    @Test
    void testToString() {
        AgeRestriction ageRestriction = mock(AgeRestriction.class);
        when(ageRestriction.toString()).thenReturn("AgeRestriction{ageRestrictionId=1, name='TestAgeRestriction',  minAge=13}");

        assertEquals("AgeRestriction{ageRestrictionId=1, name='TestAgeRestriction',  minAge=13}", ageRestriction.toString());
    }
}

