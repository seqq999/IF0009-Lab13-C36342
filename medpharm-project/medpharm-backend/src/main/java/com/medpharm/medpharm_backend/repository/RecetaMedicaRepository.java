package com.medpharm.medpharm_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.medpharm.medpharm_backend.domain.model.RecetaMedica;

public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, Long> {

    @EntityGraph(attributePaths = { "medico", "detalles", "detalles.medicamento" })
    List<RecetaMedica> findAll();

    @EntityGraph(attributePaths = { "medico", "detalles", "detalles.medicamento" })
    List<RecetaMedica> findByEstado(String estado);

    boolean existsByCodigoReceta(String codigo);
}
