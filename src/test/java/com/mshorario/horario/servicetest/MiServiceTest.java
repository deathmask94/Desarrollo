package com.mshorario.horario.servicetest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mshorario.horario.model.Horario;
import com.mshorario.horario.repository.repositorio;
import com.mshorario.horario.service.Servicio;

@ExtendWith(MockitoExtension.class)
public class MiServiceTest {

    
    @InjectMocks
    private Servicio servicio;

    @Mock
    private repositorio repository;


    // crear horario
    @Test
    void crearHorario_deberiaRetornarHorarioGuardado() {
    // given
    Horario nuevoHorario = new Horario();
    when(repository.save(nuevoHorario)).thenReturn(nuevoHorario);

    // when
    Horario resultado = servicio.crearHorario(nuevoHorario);

    // then
    assertEquals(nuevoHorario, resultado);
    verify(repository).save(nuevoHorario);
}


    // ver todos los horarios
    @Test
    void obtenerhorarios_listacompleta_deberiamostrartodosloshorarios() {
    // given
    Horario horario1 = new Horario();
    Horario horario2 = new Horario();
    Horario horario3 = new Horario();
    List<Horario> horarios = List.of(horario1, horario2, horario3);
    when(repository.findAll()).thenReturn(horarios);

    // when
    List<Horario> result = servicio.mostrarHorarios();

    // then
    assertEquals(3, result.size());
    assertTrue(result.contains(horario1));
    verify(repository).findAll();
    }



    // mostrar por asignatura
    @Test
    void mostrarPorAsignatura_deberiaRetornarHorarioCorrecto() {
    // given
    String asignatura = "Matematicas";
    Horario esperado = new Horario();
    when(repository.findByAsignatura(asignatura)).thenReturn(Optional.of(esperado));

    // when
    Optional<Horario> resultado = servicio.mostrarPorAsignatura(asignatura);

    // then
    assertTrue(resultado.isPresent());
    assertEquals(esperado, resultado.get());
    verify(repository).findByAsignatura(asignatura);
}


    // eliminar por hora
    @Test
    void eliminarPorHora_conHoraExistente_deberiaEliminarSinErrores() {
    // given
    String hora = "08:00";
    when(repository.existsByHora(hora)).thenReturn(true);

    // when
    servicio.eliminarPorHora(hora);

    // then
    verify(repository).existsByHora(hora);
    verify(repository).deleteByHora(hora);
}



}
