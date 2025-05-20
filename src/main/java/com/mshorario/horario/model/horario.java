package com.mshorario.horario.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "horarios")

public class horario {

    private String dia;
    private String hora;
    private String asignatura;
    private String idCarrera;
    



}
