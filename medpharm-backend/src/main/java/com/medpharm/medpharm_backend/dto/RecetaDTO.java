package com.medpharm.medpharm_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.medpharm.medpharm_backend.domain.model.RecetaMedica;

public record RecetaDTO(
        Long id,
        String codigoReceta,
        String pacienteNombre,
        Long medicoId,
        String medicoNombre,
        String estado,
        LocalDateTime fechaEmision,
        List<DetalleRecetaResponseDTO> detalles) {

    public static RecetaDTO from(RecetaMedica r) {
        return new RecetaDTO(
                r.getId(),
                r.getCodigoReceta(),
                r.getPacienteNombre(),
                r.getMedico().getId(),
                r.getMedico().getNombreCompleto(),
                r.getEstado(),
                r.getFechaEmision(),
                r.getDetalles().stream()
                        .map(DetalleRecetaResponseDTO::from)
                        .toList());
    }
}