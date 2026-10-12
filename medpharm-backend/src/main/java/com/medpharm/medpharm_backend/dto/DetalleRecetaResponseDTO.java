package com.medpharm.medpharm_backend.dto;

import java.math.BigDecimal;

import com.medpharm.medpharm_backend.domain.model.DetalleReceta;

public record DetalleRecetaResponseDTO(
        Long id,
        Long medicamentoId,
        String medicamentoCodigo,
        String medicamentoNombre,
        BigDecimal precioUnitario,
        Integer cantidad,
        String dosisIndicada) {

    public static DetalleRecetaResponseDTO from(DetalleReceta d) {
        return new DetalleRecetaResponseDTO(
                d.getId(),
                d.getMedicamento().getId(),
                d.getMedicamento().getCodigo(),
                d.getMedicamento().getNombre(),
                d.getMedicamento().getPrecioUnitario(),
                d.getCantidad(),
                d.getDosisIndicada());
    }
}