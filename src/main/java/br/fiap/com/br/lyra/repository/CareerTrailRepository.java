package br.fiap.com.br.lyra.repository;

import br.fiap.com.br.lyra.dto.CareerTrailDTO;
import br.fiap.com.br.lyra.dto.CareerTrailListDTO;
import br.fiap.com.br.lyra.model.CareerTrail;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerTrailRepository extends JpaRepository<CareerTrail, Long> {

    // Já existente para dashboard/pagination
    Page<CareerTrail> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // Novo método para deletar todas as trilhas de um usuário
    List<CareerTrail> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
        SELECT new br.fiap.com.br.lyra.dto.CareerTrailDTO(
            c.profile,
            c.content,
            c.createdAt
        )
        FROM CareerTrail c
        WHERE c.user.id = :userId
        ORDER BY c.createdAt DESC
    """)
    CareerTrailDTO findLatestDTO(Long userId);

    // @Query("""
    //     SELECT new br.fiap.com.br.lyra.dto.CareerTrailListDTO(
    //         c.id, c.profile, c.createdAt
    //     )
    //     FROM CareerTrail c
    //     WHERE c.user.id = :userId
    //     ORDER BY c.createdAt DESC
    // """)
    // Page<CareerTrailListDTO> findAllLight(Long userId, Pageable pageable);
}
