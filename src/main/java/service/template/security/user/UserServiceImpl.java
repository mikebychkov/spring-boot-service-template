package service.template.security.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import service.template.config.exceptionhandling.exceptions.UserNotFound;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFound::new);
    }

    @Override
    public User findByIdOrNew(Long id) {
        return userRepository.findById(id)
                .map(User::new)
                .map(u -> {
                    u.setPassword(null);
                    return u;
                }).orElse(new User());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFound::new);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {

        Long id = Optional.ofNullable(user.getId()).filter(Objects::nonNull).orElse(null);

        if (id != null && user.getPassword().isBlank()) {
            User dbuser = findById(user.getId());
            user.setPassword(dbuser.getPassword());
        } else {
            user.setId(null);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public User save(UserDTO dto) {

        User newUser = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        return save(newUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
