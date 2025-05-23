package com.mshorario.horario.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.mshorario.horario.model.Horario;
import com.mshorario.horario.repository.repositorio;

@Service
public class Servicio {
    private final repositorio hrepo;

    public Servicio(repositorio hrepo) {
        this.hrepo = hrepo;
    }

    public Horario crearHorario(Horario horario) {
        return hrepo.save(horario);
    }

    public List<Horario> mostrarHorarios() {
        return hrepo.findAll();
    }

    public Optional<Horario> mostrarPorAsignatura(String asignatura) {
        return hrepo.findByAsignatura(asignatura);
    }

    public void eliminarPorHora(String hora) {
        if (!hrepo.existsByHora(hora)) {
            throw new IllegalArgumentException("No existe horario a esa hora");
        }
        hrepo.deleteByHora(hora);
    }
}