package pl.projekt.alekino.domain.movie;

import org.junit.jupiter.api.Test;
import pl.projekt.alekino.domain.agerestriction.AgeRestriction;
import pl.projekt.alekino.domain.genre.Genre;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieTest {

    @Test
    void testEquals() {
        AgeRestriction ageRestriction1 = new AgeRestriction(1L,"1",13);
        ArrayList<Genre> genreList = new ArrayList<>();
        Movie movie1 = new Movie(1L,"nazwa1","nazwa1",1998,"https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg",155,3,genreList,ageRestriction1);
        Movie movie2 = new Movie(2L,"nazwa2","nazwa2",1999,"https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg",150,2,genreList,ageRestriction1);
        Movie movie3 = new Movie(1L,"nazwa1","nazwa1",1998,"https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg",155,3,genreList,ageRestriction1);

        assertEquals(false, movie1.equals(movie2));
        assertEquals(true, movie1.equals(movie3));
    }

    @Test
    void testHashCode() {
        AgeRestriction ageRestriction1 = new AgeRestriction(1L,"1",13);
        ArrayList<Genre> genreList = new ArrayList<>();
        Movie movie1 = new Movie(1L,"nazwa1","nazwa1",1998,"https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg",155,3,genreList,ageRestriction1);
        Movie movie2 = new Movie(2L,"nazwa2","nazwa2",1999,"https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg",150,2,genreList,ageRestriction1);


        assertEquals(false, movie1.hashCode() == movie2.hashCode());
    }

    @Test
    void testToString() {
        Movie movie = mock(Movie.class);
        when(movie.toString()).thenReturn("Movie{id=1, title='nazwa1' originalTitle='nazwa1', releaseYear=1998, imageUrl='https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg', duration=155, rating=3,genres=genres,  ageRestriction=ageRestriction}");

        assertEquals("Movie{id=1, title='nazwa1' originalTitle='nazwa1', releaseYear=1998, imageUrl='https://fwcdn.pl/fpo/09/36/936/8022172.3.jpg', duration=155, rating=3,genres=genres,  ageRestriction=ageRestriction}", movie.toString());
    }
}
