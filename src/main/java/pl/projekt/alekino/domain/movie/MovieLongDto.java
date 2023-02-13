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
public class MovieLongDto {

    private Long id;
    private String title;
    private String originalTitle;
    private Integer releaseYear;
    private String imageUrl;
    private int duration;
    private double rating;
    private List<GenreDto> genres;
    private AgeRestrictionDto ageRestriction;

}
