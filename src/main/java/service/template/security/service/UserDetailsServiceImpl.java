package service.template.security.service;

import service.template.security.user.AuthUser;
import service.template.security.user.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService  {

    private final AuthUserRepository repository;

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuthUser> user = repository.findByUsernameIgnoreCase(username);

        return user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}