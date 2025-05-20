package com.mshorario.horario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class controlador {


    @GetMapping("/horario")
    public String mostrarHorario(@RequestParam String param) {
        return new String();
    }
    





}
