package pl.projekt.alekino.domain.agerestriction;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.alekino.domain.agerestriction.AgeRestrictionDto;
import pl.projekt.alekino.domain.agerestriction.AgeRestriction;
import pl.projekt.alekino.domain.agerestriction.AgeRestrictionRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AgeRestrictionService {

    private final AgeRestrictionRepository ageRestrictionRepository;
    private final ModelMapper modelMapper;

    public AgeRestrictionDto convertToDto(AgeRestriction p) {
        return modelMapper.map(p, AgeRestrictionDto.class);
    }

    public AgeRestriction convertToEntity(AgeRestrictionDto dto) {
        return modelMapper.map(dto, AgeRestriction.class);
    }

    @Transactional(readOnly = true)
    public List<AgeRestrictionDto> getAllAgeRestrictions() {
        return ageRestrictionRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<AgeRestrictionDto> getAgeRestrictionById(Long id) {
        return ageRestrictionRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public void addAgeRestriction(AgeRestrictionDto ageRestrictionDto) {
        AgeRestriction ageRestriction = convertToEntity(ageRestrictionDto);
        ageRestrictionRepository.save(ageRestriction);
    }
}
