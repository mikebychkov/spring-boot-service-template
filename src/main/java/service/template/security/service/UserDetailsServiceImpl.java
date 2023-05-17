package service.template.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.template.config.exceptionhandling.exceptions.UserNotFound;
import service.template.security.user.User;
import service.template.security.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService  {

    private final UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = repository.findByUsernameIgnoreCase(username);

        return user.orElseThrow(UserNotFound::new);
    }
}