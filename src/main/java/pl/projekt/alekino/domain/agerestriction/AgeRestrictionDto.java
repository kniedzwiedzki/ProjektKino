package pl.projekt.alekino.domain.agerestriction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgeRestrictionDto {

    private Long id;
    private String name;
    private int minAge;
}
