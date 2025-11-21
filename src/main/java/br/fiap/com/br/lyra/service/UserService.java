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
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional
    public UserDTO register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .experienceLevel(req.getExperienceLevel())
                .interests(req.getInterests())
                .locale(req.getLocale())
                .roles(Set.of("ROLE_USER"))
                .build();

        User saved = userRepository.save(user);

        return toDto(saved);
    }

    @Cacheable("users")
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    private UserDTO toDto(User u) {
        if (u == null) return null;
        return UserDTO.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .experienceLevel(u.getExperienceLevel())
                .interests(u.getInterests())
                .locale(u.getLocale())
                .build();
    }
}
