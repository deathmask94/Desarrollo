package com.mshorario.horario.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mshorario.horario.dto.DeleteMensaje;
import com.mshorario.horario.model.Horario;
import com.mshorario.horario.service.Servicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ms")
@Tag(name = "HORARIO", description = "Endpoints API horario")
public class Controlador {

    private final Servicio servicio;

    @Autowired
    public Controlador(Servicio servicio) {
        this.servicio = servicio;
    }
    // 1ER ENDPOINT VER TODOS LOS HORARIOS
    @GetMapping("/vertodos")
    @Operation(summary = "Muestra todos los horarios",
                description= "Muestra todos los horarios Existentes")
    public CollectionModel<EntityModel<Horario>> mostrarHorarios() {
        List<EntityModel<Horario>> horarios = servicio.mostrarHorarios().stream()
            .map(horario -> EntityModel.of(horario,
                    linkTo(methodOn(Controlador.class).mostrarPorAsignatura(horario.getAsignatura())).withSelfRel(),
                    linkTo(methodOn(Controlador.class).mostrarHorarios()).withRel("horarios")))
            .collect(Collectors.toList());

        return CollectionModel.of(horarios,
                linkTo(methodOn(Controlador.class).mostrarHorarios()).withSelfRel());
    }
    // 2DO ENDPOINT VER HORARIOS POR ASIGNATURA
    @GetMapping("/asignatura/{asignatura}")
    @Operation(
        summary = "Muestra  horarios por asignatura",
        description = "Devuelve el horario correspondiente a la asignatura indicada.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Horario encontrado"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado")
        },
        parameters = {
            @Parameter(name = "asignatura", description = "Nombre de la asignatura", required = true)
        }
    )
    public ResponseEntity<EntityModel<Horario>> mostrarPorAsignatura(@PathVariable String asignatura) {
        Optional<Horario> horarioOpt = servicio.mostrarPorAsignatura(asignatura);

        return horarioOpt.map(horario -> ResponseEntity.ok(
                EntityModel.of(horario,
                        linkTo(methodOn(Controlador.class).mostrarPorAsignatura(asignatura)).withSelfRel(),
                        linkTo(methodOn(Controlador.class).mostrarHorarios()).withRel("horarios"))))
            .orElse(ResponseEntity.notFound().build());
    }
    // 3ER ENDPOINT CREAR HORARIO 
    @PostMapping("/create")
    @Operation(summary = "Crea un nuevo horario", description= "Crea un nuevo horario, debe ser insertado en formato JSON")
    public ResponseEntity<EntityModel<Horario>> crearHorario(@RequestBody Horario horario) {
    Horario creado = servicio.crearHorario(horario);

    
    String asignatura = creado.getAsignatura() != null ? creado.getAsignatura() : "";

    EntityModel<Horario> resource = EntityModel.of(creado,
            linkTo(methodOn(Controlador.class).mostrarPorAsignatura(asignatura)).withSelfRel(),
            linkTo(methodOn(Controlador.class).mostrarHorarios()).withRel("horarios"));

    return ResponseEntity
            .created(linkTo(methodOn(Controlador.class).mostrarPorAsignatura(asignatura)).toUri())
            .body(resource);
}
    //4TO ENDPOINT ELIMINA HORARIO
    @DeleteMapping("/borrar/{hora}")
    @Operation(summary = "Elimina un horario segun hora", description= "Elimina un horario segun hora especificada")
    public ResponseEntity<EntityModel<DeleteMensaje>> eliminarPorHora(@PathVariable String hora) {
    servicio.eliminarPorHora(hora);

    DeleteMensaje mensaje = new DeleteMensaje("Horario eliminado correctamente");

    EntityModel<DeleteMensaje> resource = EntityModel.of(
        mensaje,
        linkTo(methodOn(Controlador.class).mostrarHorarios()).withRel("horarios"),
        linkTo(methodOn(Controlador.class).crearHorario(new Horario())).withRel("crearHorario")
    );

    return ResponseEntity.ok(resource);
}



}
