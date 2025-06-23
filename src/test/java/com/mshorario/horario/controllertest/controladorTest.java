package com.mshorario.horario.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshorario.horario.controller.Controlador;
import com.mshorario.horario.model.Horario;
import com.mshorario.horario.service.Servicio;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Controlador.class)
public class controladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Servicio servicio;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void mostrarHorarios_deberiaRetornarListaHorarios() throws Exception {
        Horario h1 = new Horario();
        Horario h2 = new Horario();
        when(servicio.mostrarHorarios()).thenReturn(List.of(h1, h2));

        mockMvc.perform(get("/ms/vertodos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void mostrarPorAsignatura_deberiaRetornarUnHorario() throws Exception {
        Horario h = new Horario();
        when(servicio.mostrarPorAsignatura("Matemáticas")).thenReturn(Optional.of(h));

        mockMvc.perform(get("/ms/asignatura/Matemáticas"))
                .andExpect(status().isOk());
    }

    @Test
    void crearHorario_deberiaRetornarHorarioCreado() throws Exception {
    Horario h = new Horario();
    h.setAsignatura("Matematicas"); 
    when(servicio.crearHorario(any(Horario.class))).thenReturn(h);

    mockMvc.perform(post("/ms/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(h)))
            .andExpect(status().isCreated()); 
}


    @Test
    void eliminarPorHora_deberiaRetornarMensajeExito() throws Exception {
    
    doNothing().when(servicio).eliminarPorHora("08:00");

    mockMvc.perform(delete("/ms/borrar/08:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.mensaje").value("Horario eliminado correctamente"));
}

}


    

