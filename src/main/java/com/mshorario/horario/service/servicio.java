package com.mshorario.horario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mshorario.horario.model.Horario;
import com.mshorario.horario.repository.repositorio;

@Service
public class servicio {

    private final repositorio hrepo;

    public servicio(repositorio hrepo){
        this.hrepo = hrepo; 
    }

    // crear
    public Horario crearHorario(Horario horario) {
        return hrepo.save(horario);

    }

    // mostrar todos
    public List<Horario> mostrarHorarios() {
        return hrepo.findAll();

    }

    // buscar por 
    public Optional<Horario> mostrarPorAsignatura(String asignatura) {
        return hrepo.mostrarPorAsignatura(asignatura);
    }


    // borrar
    public void eliminarPorHora(String hora){
        if (!hrepo.existsByHora(hora)){
            throw new IllegalArgumentException("no existe una clase a esa hora: " + hora);
        }   
        hrepo.deleteByHora(hora);

    }




}
