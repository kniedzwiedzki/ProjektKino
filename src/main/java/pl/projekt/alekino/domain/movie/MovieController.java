package pl.projekt.alekino.domain.movie;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.projekt.alekino.domain.genre.GenreDto;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/${app.prefix}/${app.version}/movies")
@Tag(name = "Movie", description = "Movie endpoints")
public class MovieController {

    private final MovieService movieService;

    @GetMapping()
    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the movies",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = MovieShortDto.class)))}),
            @ApiResponse(responseCode = "204", description = "Movies not found", content = @Content)
    })
    public ResponseEntity<List<MovieShortDto>> getAllMovies(@RequestParam Optional<List<String>> genres) {
        if (genres.isEmpty()) {
            return ResponseEntity.ok(movieService.getAllMovies());
        }
        List<MovieShortDto> movies = movieService.getAllMovies(genres.get());
        if (movies.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the movie",
                    content = {@Content(schema = @Schema(implementation = MovieLongDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    public ResponseEntity<MovieLongDto> getMovieById(@PathVariable Long id) {
        Optional<MovieLongDto> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/genres")
    @Operation(summary = "Get genres of movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the genres",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = GenreDto.class)))}),
            @ApiResponse(responseCode = "204", description = "No genres found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    public ResponseEntity<List<GenreDto>> getGenresOfMovieById(@PathVariable Long id) {
        if (!movieService.exists(id))
            return ResponseEntity.notFound().build();
        List<GenreDto> genres = movieService.getGenresOfMovieById(id);
        if (genres.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(genres);
    }

    @PostMapping()
    @Operation(summary = "Add new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong data",
                    content = @Content)
    })
    public ResponseEntity<Void> addMovie(@RequestBody MovieLongDto movie) {
        Optional<MovieLongDto> movieDto = movieService.addMovie(movie);
        if (movieDto.isPresent()) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(movieDto.get().getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/genres")
    @Operation(summary = "Add genre to movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre added",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    public ResponseEntity<Void> addGenreToMovie(@PathVariable Long id, @RequestBody GenreDto genre) {
        if (!movieService.exists(id))
            return ResponseEntity.notFound().build();
        Optional<GenreDto> genreDto = movieService.addGenreToMovie(id, genre);
        if (genreDto.isPresent()) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(genreDto.get().getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    public ResponseEntity<Void> updateMovie(@PathVariable Long id, @RequestBody MovieLongDto movie) {
        Optional<MovieLongDto> movieDto = movieService.updateMovie(id, movie);
        if (movieDto.isPresent())
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/genres/{genreId}")
    @Operation(summary = "Delete genre from movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Genre deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie or genre not found", content = @Content)
    })
    public ResponseEntity<Void> deleteGenreFromMovie(@PathVariable Long id, @PathVariable Long genreId) {
        if (movieService.deleteGenreFromMovie(id, genreId))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }


}
