package pl.projekt.alekino.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projekt.alekino.domain.agerestriction.AgeRestrictionDto;
import pl.projekt.alekino.domain.genre.GenreDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieShortDto {

    private Long id;
    private String title;
    private Integer releaseYear;
    private String imageUrl;
    private double rating;
    private List<GenreDto> genres;
    private AgeRestrictionDto ageRestriction;

}
