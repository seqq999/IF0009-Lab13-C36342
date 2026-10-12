package com.medpharm.medpharm_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medpharm.medpharm_backend.domain.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findAllByOrderByNombreAsc();

}
