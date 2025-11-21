package br.fiap.com.br.lyra.repository;

import br.fiap.com.br.lyra.model.CareerTrail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerTrailRepository extends JpaRepository<CareerTrail, Long> {
    Page<CareerTrail> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
