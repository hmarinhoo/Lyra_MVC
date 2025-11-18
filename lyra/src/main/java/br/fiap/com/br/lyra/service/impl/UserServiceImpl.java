package br.fiap.com.br.lyra.service.impl;

import br.fiap.com.br.lyra.dto.RegisterRequest;
import br.fiap.com.br.lyra.dto.UserDTO;
import br.fiap.com.br.lyra.repository.UserRepository;
import br.fiap.com.br.lyra.service.UserService;
import br.fiap.com.br.lyra.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        User u = new User();
        u.setName(request.getName());
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setExperienceLevel(request.getExperienceLevel());
        u.setInterests(request.getInterests());
        u.setLocale(request.getLocale());
        u.setRoles(Collections.singleton("ROLE_USER"));
        User saved = userRepository.save(u);
        return toDto(saved);
    }

    @Override
    public UserDTO findById(Long id) {
        return userRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toDto).orElse(null);
    }

    private UserDTO toDto(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        dto.setExperienceLevel(u.getExperienceLevel());
        dto.setInterests(u.getInterests());
        dto.setLocale(u.getLocale());
        return dto;
    }
}
