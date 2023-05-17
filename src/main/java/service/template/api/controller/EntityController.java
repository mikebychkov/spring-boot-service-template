package service.template.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.template.api.dto.SomeEntityDTO;
import service.template.api.service.entity.SomeEntityService;

@RestController
@RequestMapping("/entity")
@RequiredArgsConstructor
public class EntityController {

    private final SomeEntityService someEntityService;

    @GetMapping("/some-entities")
    public Page<SomeEntityDTO> getSomeEntity(Pageable pageable) {

        return someEntityService.findAll(pageable).map(SomeEntityDTO::of);
    }
}
