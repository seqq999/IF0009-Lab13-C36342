package com.medpharm.medpharm_backend.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "rol")
    private String rol;

    public Usuario() {
    }

    public Usuario(Long id, String username, String password, String nombre_completo, String rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombre_completo;
        this.rol = rol;
    }

    public void setUsername(String username){
        this.username = username;
    }


}
