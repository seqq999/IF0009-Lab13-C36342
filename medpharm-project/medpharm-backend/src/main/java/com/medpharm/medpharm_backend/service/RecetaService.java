package com.medpharm.medpharm_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medpharm.medpharm_backend.domain.model.DetalleReceta;
import com.medpharm.medpharm_backend.domain.model.Medicamento;
import com.medpharm.medpharm_backend.domain.model.RecetaMedica;
import com.medpharm.medpharm_backend.domain.model.Usuario;
import com.medpharm.medpharm_backend.dto.DetalleRecetaDTO;
import com.medpharm.medpharm_backend.dto.RecetaRequestDTO;
import com.medpharm.medpharm_backend.dto.RecetaDTO;
import com.medpharm.medpharm_backend.exception.RecetaException;
import com.medpharm.medpharm_backend.exception.RecursoNoEncontradoException;
import com.medpharm.medpharm_backend.repository.MedicamentoRepository;
import com.medpharm.medpharm_backend.repository.RecetaMedicaRepository;
import com.medpharm.medpharm_backend.repository.UsuarioRepository;

@Service
public class RecetaService {
    private static final Set<String> ESTADOS_FILTRO = Set.of("PENDIENTE", "DESPACHADA", "CANCELADA");
    private static final Set<String> ESTADOS_ACTUALIZABLES = Set.of("DESPACHADA", "CANCELADA");

    private final RecetaMedicaRepository recetaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository userRepository;
    private final Random random = new Random();

    public RecetaService(RecetaMedicaRepository recetaRepository, MedicamentoRepository medicamentoRepository,
            UsuarioRepository userRepository) {
        this.recetaRepository = recetaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<RecetaMedica> findAll() {
        return recetaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<RecetaMedica> findAllByEstado(String estado) {
        String normalizado = estado.trim().toUpperCase();

        if (!ESTADOS_FILTRO.contains(normalizado)) {
            throw new RecetaException("Estado invalido: " + estado);
        }

        return recetaRepository.findByEstado(normalizado);
    }

    @Transactional
    public RecetaDTO createReceta(RecetaRequestDTO dto) {
        Usuario medico = getMedico(dto.medicoId());

        RecetaMedica receta = new RecetaMedica(
                generarCodigo(),
                dto.pacienteNombre(),
                medico,
                "PENDIENTE",
                dto.fechaEmision() != null ? dto.fechaEmision() : LocalDateTime.now());

        for (DetalleRecetaDTO d : dto.detalles()) {
            Medicamento medicamento = medicamentoRepository.findById(d.medicamentoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe el medicamento con id: " + d.medicamentoId()));

            if (medicamento.getStock() < d.cantidad()) {
                throw new RecetaException("Stock insuficiente de " + medicamento.getNombre()
                        + ": disponible " + medicamento.getStock() + ", solicitado " + d.cantidad());
            }

            DetalleReceta detalle = new DetalleReceta();
            detalle.setMedicamento(medicamento);
            detalle.setCantidad(d.cantidad());
            detalle.setDosisIndicada(d.dosisIndicada());
            receta.addDetalle(detalle);
        }

        RecetaMedica guardada = recetaRepository.save(receta);
        return RecetaDTO.from(guardada);
    }

    @Transactional
    public RecetaDTO actualizarEstado(Long id, String estado) {
        String nuevoEstado = validarEstado(estado);

        RecetaMedica receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la receta con el id: " + id));

        String estadoActual = receta.getEstado();
        if (!"PENDIENTE".equals(estadoActual)) {
            throw new RecetaException("La receta " + receta.getCodigoReceta()
                    + " ya está " + estadoActual + " y no puede cambiar de estado");
        }

        if ("DESPACHADA".equals(nuevoEstado)) {
            descontarStock(receta);
        }

        receta.setEstado(nuevoEstado);
        return RecetaDTO.from(receta);
    }

    private String generarCodigo() {
        String codigo;
        do {
            int numero = 1000 + random.nextInt(9000);
            codigo = "RCTM-2026-" + numero;
        } while (recetaRepository.existsByCodigoReceta(codigo));
        return codigo;
    }

    private Usuario getMedico(Long id) {
        if (id == null) {
            throw new RecetaException("El medico es obligatorio");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new RecetaException("No existe el medico con el id: " + id));
    }

    private String validarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new RecetaException("El estado es obligatorio");
        }

        String normalizado = estado.trim().toUpperCase();

        if (!ESTADOS_ACTUALIZABLES.contains(normalizado)) {
            throw new RecetaException("Estado invalido: " + normalizado + ". Use DESPACHADA o CANCELADA");
        }
        return normalizado;
    }

    private void descontarStock(RecetaMedica receta) {
        for (DetalleReceta d : receta.getDetalles()) {
            Medicamento m = d.getMedicamento();

            if (m.getStock() < d.getCantidad()) {
                throw new RecetaException("Stock insuficiente de " + m.getNombre()
                        + ": disponible " + m.getStock() + ", requerido " + d.getCantidad());
            }
            m.setStock(m.getStock() - d.getCantidad());
        }
    }
}