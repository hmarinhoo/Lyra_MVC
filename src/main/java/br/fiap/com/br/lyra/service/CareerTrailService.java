package br.fiap.com.br.lyra.service;


import br.fiap.com.br.lyra.dto.CareerTrailDTO;
import br.fiap.com.br.lyra.dto.CareerTrailListDTO;
import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.repository.CareerTrailRepository;
import org.springframework.transaction.annotation.Transactional;
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

    // // Aqui adicionamos @Transactional(readOnly = true) para manter a sessão aberta
    // @Transactional(readOnly = true)
    // public Page<CareerTrailListDTO> listByUser(Long userId, Pageable pageable) {
    //     return repository.findAllLight(userId, pageable);
    // }


    @Transactional
    public void deleteByUser(Long userId) {
        var trails = repository.findByUserIdOrderByCreatedAtDesc(userId);
        repository.deleteAll(trails);
    }

    @Transactional(readOnly = true)
    public CareerTrailDTO findLatestByUser(Long userId) {
        return repository.findLatestDTO(userId);
    }
}
