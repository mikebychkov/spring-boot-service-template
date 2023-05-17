package service.template.api.repo;

import service.template.api.entity.SomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SomeEntityRepository extends JpaRepository<SomeEntity, UUID> {
}