package com.medpharm.medpharm_backend.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recetas_medicas")
public class RecetaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_receta")
    private String codigoReceta;

    @Column(name = "paciente_nombre")
    private String pacienteNombre;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Usuario medico;

    @Column(name = "estado")
    private String estado; // PENDIENTE, DESPACHADA, CANCELADA

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReceta> detalles = new ArrayList<>();

    public RecetaMedica() {
    }

    public RecetaMedica(String codigoReceta, String pacienteNombre, Usuario medico,
            String estado, LocalDateTime fechaEmision) {
        this.codigoReceta = codigoReceta;
        this.pacienteNombre = pacienteNombre;
        this.medico = medico;
        this.estado = estado;
        this.fechaEmision = fechaEmision;
    }

    public RecetaMedica(Long id, String codigoReceta, String pacienteNombre, Usuario medico,
            String estado, LocalDateTime fechaEmision) {
        this.id = id;
        this.codigoReceta = codigoReceta;
        this.pacienteNombre = pacienteNombre;
        this.medico = medico;
        this.estado = estado;
        this.fechaEmision = fechaEmision;
    }

    public void addDetalle(DetalleReceta detalle) {
        detalles.add(detalle);
        detalle.setReceta(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoReceta() {
        return codigoReceta;
    }

    public void setCodigoReceta(String codigoReceta) {
        this.codigoReceta = codigoReceta;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public List<DetalleReceta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleReceta> detalles) {
        this.detalles = detalles;
    }
}
