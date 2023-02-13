package pl.projekt.alekino.domain.agerestriction;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "AGE_RESTRICTIONS")
public class AgeRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int minAge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeRestriction that = (AgeRestriction) o;
        return minAge == that.minAge && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, minAge);
    }

    @Override
    public String toString() {
        return "AgeRestriction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minAge=" + minAge +
                '}';
    }
}
