package service.template.api.service.entity;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EntityService<T> {

    T findById(UUID id);
    List<T> findByExample(Example<T> example);

    Page<T> findAll(Pageable pageable);
}
