package com.medpharm.medpharm_backend.dto;

import jakarta.validation.constraints.NotBlank;

public record EstadoRequestDTO(
        @NotBlank(message = "El estado es obligatorio") String estado) {
}
