package pl.projekt.alekino.domain.movie;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import pl.projekt.alekino.domain.agerestriction.AgeRestriction;
import pl.projekt.alekino.domain.genre.Genre;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Field must not be blank")
    @Size(min = 1, max = 50, message = "Field must be between {min} and {max} characters")
    private String title;

    @NotBlank(message = "Field must not be blank")
    @Size(min = 1, max = 50, message = "Field must be between {min} and {max} characters")
    private String originalTitle;
    @Range(min = 1900, max = 2030, message = "Field must be between {min} and {max}")
    private Integer releaseYear;
    private String imageUrl;
    @NotNull(message = "Field must not be null")
    private int duration;
    @Transient
    private double rating;

    @ManyToMany
    @JoinTable(name = "MOVIES_X_GENRES",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private List<Genre> genres;

    @ManyToOne
    @JoinColumn(name = "AGE_RESTRICTION_ID")
    private AgeRestriction ageRestriction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return duration == movie.duration && Double.compare(movie.rating, rating) == 0 && Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(originalTitle, movie.originalTitle) && Objects.equals(releaseYear, movie.releaseYear) && Objects.equals(imageUrl, movie.imageUrl) && Objects.equals(genres, movie.genres) && Objects.equals(ageRestriction, movie.ageRestriction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, originalTitle, releaseYear, imageUrl, duration, rating, genres, ageRestriction);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", releaseYear=" + releaseYear +
                ", imageUrl='" + imageUrl + '\'' +
                ", duration=" + duration +
                ", rating=" + rating +
                ", genres=" + genres +
                ", ageRestriction=" + ageRestriction +
                '}';
    }
}