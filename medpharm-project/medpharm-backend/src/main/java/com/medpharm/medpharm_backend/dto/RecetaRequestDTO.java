package com.medpharm.medpharm_backend.dto;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RecetaRequestDTO(
         @NotBlank(message = "El código de rastreo es obligatorio") 
         @Pattern(regexp = "^RCTM-\\d{4}$",message = "Formato inválido. Ejemplo: RCTM-1234") 
        String codigoReceta,

        @NotBlank(message = "El nombre del paciente es obligatorio")
        @Size(min = 5, message = "El nombre del paciente debe tener al menos 5 caracteres")
        String pacienteNombre,

        Long medicoId,

        @Pattern(regexp = "PENDIENTE|DESPACHADA|CANCELADA",
                message = "Estado inválido. Use PENDIENTE, DESPACHADA o CANCELADA")
        String estado,

        @PastOrPresent(message = "La fecha de emisión no puede ser futura")
        LocalDateTime fechaEmision,

        @NotEmpty(message = "La receta debe tener al menos un medicamento")
        @Valid
        List<DetalleRecetaDTO> detalles) {
}
