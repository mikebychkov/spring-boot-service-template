package service.template.api.controller;

import service.template.api.dto.UserDTO;
import service.template.api.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entity")
@RequiredArgsConstructor
public class EntityController {

    private final UserService userService;

    @GetMapping("/users")
    public Page<UserDTO> getUsers(Pageable pageable) {

        return userService.findAll(pageable).map(UserDTO::of);
    }
}
