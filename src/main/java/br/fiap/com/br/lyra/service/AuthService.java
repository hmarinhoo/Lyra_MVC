package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    // Usado para registrar novo usuário
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Valida email e senha (para login via Spring Security)
    public boolean authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        return encoder.matches(password, user.getPassword());
    }
}
