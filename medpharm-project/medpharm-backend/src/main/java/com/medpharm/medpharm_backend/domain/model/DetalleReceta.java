package com.medpharm.medpharm_backend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "detalles_receta")
public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receta_id")
    private RecetaMedica receta;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "dosisIndicada")
    private String dosis_indicada;

    public DetalleReceta() {
    }

    public DetalleReceta(Long id, RecetaMedica receta, Medicamento medicamento, Integer cantidad, String dosis_indicada) {
        this.id = id;
        this.receta = receta;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.dosis_indicada = dosis_indicada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecetaMedica getReceta() {
        return receta;
    }

    public void setReceta(RecetaMedica receta) {
        this.receta = receta;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDosisIndicada() {
        return dosis_indicada;
    }

    public void setDosisIndicada(String dosis_indicada) {
        this.dosis_indicada = dosis_indicada;
    }
}