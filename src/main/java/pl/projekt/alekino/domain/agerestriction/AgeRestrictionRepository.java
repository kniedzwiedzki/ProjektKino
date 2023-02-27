package pl.projekt.alekino.domain.agerestriction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projekt.alekino.domain.agerestriction.AgeRestriction;

import java.util.Optional;

@Repository
public interface AgeRestrictionRepository extends JpaRepository<AgeRestriction, Long> {

    Optional<AgeRestriction> findByName(String name);

}
