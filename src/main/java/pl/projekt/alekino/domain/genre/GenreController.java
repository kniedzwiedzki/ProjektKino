package pl.projekt.alekino.domain.genre;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/${app.prefix}/${app.version}/genres")
@Tag(name = "Genre", description = "Movie Genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping()
    @Operation(summary = "Get all genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the genres",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = GenreDto.class)))}),
            @ApiResponse(responseCode = "204", description = "Genres not found",
                    content = @Content)
    })
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> genres = genreService.getAllGenres();
        if (genres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get genre by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the genre",
                    content = {@Content(schema = @Schema(implementation = GenreDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content)
    })
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Add new genre", security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong genre", content = @Content)
    })
    public ResponseEntity<Void> addGenre(@RequestBody GenreDto genreDto) {
        GenreDto genre = genreService.addGenre(genreDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(genre.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Update genre", security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated",
                    content = {@Content(schema = @Schema(implementation = GenreDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content)
    })
    public ResponseEntity<Void> updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto) {
        genreService.updateGenre(id, genreDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Delete genre", security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Genre deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content)
    })
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

}

