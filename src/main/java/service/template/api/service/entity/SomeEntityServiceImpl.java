package service.template.api.service.entity;

import service.template.api.entity.SomeEntity;
import service.template.api.repo.SomeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SomeEntityServiceImpl implements SomeEntityService {

    private final SomeEntityRepository someEntityRepository;

    @Override
    public SomeEntity findById(UUID id) {

        return someEntityRepository.findById(id).orElseThrow();
    }

    @Override
    public List<SomeEntity> findByExample(Example<SomeEntity> example) {

        return someEntityRepository.findAll(example);
    }

    @Override
    public Page<SomeEntity> findAll(Pageable pageable) {

        return someEntityRepository.findAll(pageable);
    }
}
