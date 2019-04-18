package dev.go.arquitetura.microservices.auth.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.go.arquitetura.microservices.core.model.ApplicationUser;
import dev.go.arquitetura.microservices.core.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository userRepository;

    public @Override UserDetails loadUserByUsername(String username) {

        log.info("Pesquisando no banco de dados usuário pelo nome '{}'", username);

        ApplicationUser user = userRepository.findByUsername(username);

        log.info("Usuário encontrado '{}'", user);

        if (Objects.isNull(user))
            throw new UsernameNotFoundException(String.format("Usuário '%s' não encontrado", username));

        return new CustomUserDetails(user);
    }
}