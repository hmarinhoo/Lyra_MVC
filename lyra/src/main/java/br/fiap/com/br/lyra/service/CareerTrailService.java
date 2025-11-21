package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.repository.CareerTrailRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CareerTrailService {

    private final CareerTrailRepository repository;

    public CareerTrailService(CareerTrailRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CareerTrail save(CareerTrail trail) {
        return repository.save(trail);
    }

    @Cacheable("careerTrails")
    public Page<CareerTrail> listByUser(Long userId, Pageable pageable) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
}
