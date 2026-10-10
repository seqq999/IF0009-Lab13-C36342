package com.medpharm.medpharm_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medpharm.medpharm_backend.domain.model.Medicamento;
import com.medpharm.medpharm_backend.repository.MedicamentoRepository;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<Medicamento> findAll() {
        return medicamentoRepository.findAllByOrderByNombreAsc();
    }
}
