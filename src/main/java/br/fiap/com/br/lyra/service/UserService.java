package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.dto.RegisterRequest;
import br.fiap.com.br.lyra.dto.UserDTO;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}

