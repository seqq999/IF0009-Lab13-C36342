package com.medpharm.medpharm_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleRecetaDTO(
        @NotNull(message = "El medicamento es obligatorio")
        Long medicamentoId,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser un entero mayor a 0")
        Integer cantidad,

        @NotBlank(message = "La dosis indicada es obligatoria")
        String dosisIndicada) {
}