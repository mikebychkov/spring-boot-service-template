package service.template.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.template.api.entity.SomeEntity;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SomeEntityDTO {

    private UUID id;

    public static SomeEntityDTO of(SomeEntity someEntity) {

        if (someEntity == null) return null;

        SomeEntityDTO rsl = new SomeEntityDTO();

        rsl.setId(someEntity.getId());

        return rsl;
    }
}
