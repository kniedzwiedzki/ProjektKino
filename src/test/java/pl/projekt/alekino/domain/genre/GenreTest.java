package pl.projekt.alekino.domain.genre;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreTest {
    @Test
    void testEquals() {
        Genre genre1 = new Genre(1L,"1");
        Genre genre2 = new Genre(2l,"2");
        Genre genre3 = new Genre(1l,"1");

        assertEquals(false, genre1.equals(genre2));
        assertEquals(true, genre1.equals(genre3));
    }

    @Test
    void testHashCode() {
        Genre genre1 = new Genre(1L, "1");
        Genre genre2 = new Genre(2L, "2");

        assertEquals(false, genre1.hashCode() == genre2.hashCode());
    }

    @Test
    void testToString() {
        Genre genre = mock(Genre.class);
        when(genre.toString()).thenReturn("AgeRestriction{ageRestrictionId=1, name='TestAgeRestriction'}");

        assertEquals("AgeRestriction{ageRestrictionId=1, name='TestAgeRestriction'}", genre.toString());
    }
}
