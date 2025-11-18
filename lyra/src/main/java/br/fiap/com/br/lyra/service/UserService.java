package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.dto.RegisterRequest;
import br.fiap.com.br.lyra.dto.UserDTO;

public interface UserService {
    UserDTO register(RegisterRequest request);
    UserDTO findById(Long id);
    UserDTO findByEmail(String email);
}
