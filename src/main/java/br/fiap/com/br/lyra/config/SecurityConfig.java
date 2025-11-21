package br.fiap.com.br.lyra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // páginas públicas
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                // tudo o resto exige autenticação
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")          // seu template login.html
                .loginProcessingUrl("/login") // endpoint que processa o POST do form
                .defaultSuccessUrl("/home", true) // redireciona após login
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
