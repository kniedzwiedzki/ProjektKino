package pl.projekt.alekino.domain.agerestriction;


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

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/${app.prefix}/${app.version}/age-restrictions")
@Tag(name = "Age restrictions", description = "Motion picture association film rating")
public class AgeRestrictionsController {

    private final AgeRestrictionService ageRestrictionService;

    @GetMapping()
    @Operation(summary = "Get all Age restrictions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the age restrictions",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = AgeRestrictionDto.class)))}),
            @ApiResponse(responseCode = "204", description = "Age restrictions not found",
                    content = @Content)
    })
    public ResponseEntity<List<AgeRestrictionDto>> getAllAgeRestrictions() {
        List<AgeRestrictionDto> ageRes = ageRestrictionService.getAllAgeRestrictions();
        if (ageRes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ageRes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get age restriction by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the age restriction",
                    content = {@Content(schema = @Schema(implementation = AgeRestrictionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Age restriction not found", content = @Content)
    })
    public ResponseEntity<AgeRestrictionDto> getAgeRestrictionById(@PathVariable Long id) {
        return ageRestrictionService.getAgeRestrictionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    @Operation(summary = "Add new age restriction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Age restriction created",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request, wrong age restriction",
                    content = @Content)
    })
    public ResponseEntity<Void> addAgeRestriction(@RequestBody AgeRestrictionDto ageRestrictionDto) {
        ageRestrictionService.addAgeRestriction(ageRestrictionDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ageRestrictionDto.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
