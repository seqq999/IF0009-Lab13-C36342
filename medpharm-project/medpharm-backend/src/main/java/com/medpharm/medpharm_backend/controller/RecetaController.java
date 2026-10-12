package com.medpharm.medpharm_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medpharm.medpharm_backend.domain.model.RecetaMedica;
import com.medpharm.medpharm_backend.dto.RecetaRequestDTO;
import com.medpharm.medpharm_backend.dto.RecetaDTO;
import com.medpharm.medpharm_backend.service.RecetaService;

@RestController 
@RequestMapping("/api/v1/recetas") 
@CrossOrigin(origins = "http://localhost:4200") 
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService){
        this.recetaService = recetaService;
    }

    @GetMapping
    public List<RecetaMedica> findAll(){
        return recetaService.findAll();
    }

    @GetMapping("estado/{estado}")
    public List<RecetaMedica> findAllByEstado(@RequestParam String estado) {
        return recetaService.findAllByEstado(estado);
    }

    @PostMapping
    public ResponseEntity<RecetaDTO> create(@RequestBody RecetaRequestDTO dto) {
        RecetaDTO created = recetaService.createReceta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/estado")
    public RecetaDTO actualizarEstado(@PathVariable Long id, @RequestParam String estado){
        return recetaService.actualizarEstado(id, estado);
    } 
}
