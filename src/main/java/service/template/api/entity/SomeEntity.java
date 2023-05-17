package service.template.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "some_entity")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SomeEntity {

    @Id
    private UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SomeEntity someEntity = (SomeEntity) o;
        return id.equals(someEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
