package com.mshorario.horario.controller;

import org.springframework.web.bind.annotation.*;
import com.mshorario.horario.model.Horario;
import com.mshorario.horario.service.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/ms")
public class controlador {

    private final Servicio servicio;

    @Autowired
    public controlador(Servicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/vertodos")
    public List<Horario> mostrarHorarios() {
        return servicio.mostrarHorarios();
    }

    @GetMapping("/asignatura/{asignatura}")
    public Optional<Horario> mostrarPorAsignatura(@PathVariable String asignatura) {
        return servicio.mostrarPorAsignatura(asignatura);
    }

    @PostMapping("/create")
    public Horario crearHorario(@RequestBody Horario horario) {
        return servicio.crearHorario(horario);
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> eliminarPorHora(@PathVariable String hora) {
        servicio.eliminarPorHora(hora);
        return ResponseEntity.ok("Horario eliminado correctamente");
    }
}
