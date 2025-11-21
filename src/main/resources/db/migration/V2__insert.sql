-- Cria um usuário padrão para testes de login
-- Email: teste@teste.com
-- Senha: 12345678 (BCrypt abaixo)

INSERT INTO users (
    name,
    email,
    password,
    experience_level,
    interests,
    locale,
    created_at,
    updated_at
) VALUES (
    'Usuário Teste',
    'teste@teste.com',
    '$2a$12$kKcX7R9VboHF4brxoDmE3OMFZWrmaG5YYwjXCbH2swvxguejlzgsK', -- senha: 12345678
    'iniciante',
    'java,backend,carreira',
    'pt_BR',
    NOW(),
    NOW()
);

-- Adiciona papel USER ao usuário inserido
INSERT INTO user_roles (user_id, role)
SELECT id, 'USER'
FROM users
WHERE email = 'teste@teste.com';

-- Opcional: adicionar papel ADMIN também
-- INSERT INTO user_roles (user_id, role)
-- SELECT id, 'ADMIN'
-- FROM users
-- WHERE email = 'teste@teste.com';
