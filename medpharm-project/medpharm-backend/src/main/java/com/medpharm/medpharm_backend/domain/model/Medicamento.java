package com.medpharm.medpharm_backend.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "precio_unitario")
    private BigDecimal precio_unitario;

    public Medicamento() {
    }

    public Medicamento(Long id, String codigo, String nombre, Integer stock, BigDecimal precio_unitario) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.precio_unitario = precio_unitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrecioUnitario() {
        return precio_unitario;
    }

    public void setPrecioUnitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
}