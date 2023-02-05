package service.template.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import service.template.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;

    public static UserDTO of(User user) {

        if (user == null) return null;

        UserDTO rsl = new UserDTO();

        rsl.setId(user.getId());
        rsl.setUsername(user.getUsername());
        rsl.setFirstName(user.getFirstName());
        rsl.setLastName(user.getLastName());

        return rsl;
    }
}
