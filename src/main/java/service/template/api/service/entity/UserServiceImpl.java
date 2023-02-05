package service.template.api.service.entity;

import service.template.api.entity.User;
import service.template.api.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(UUID id) {

        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> findByExample(Example<User> example) {

        return userRepository.findAll(example);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {

        return userRepository.findAll(pageable);
    }
}
