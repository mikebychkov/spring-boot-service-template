package service.template.security.user;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByIdOrNew(Long id);
    User findByUsername(String username);
    List<User> findAll();
    User save(User user);
    User save(UserDTO dto);
    void deleteById(Long id);
}
