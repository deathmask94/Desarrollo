package com.mshorario.horario.dto;

public class DeleteMensaje {
    
    private String mensaje;

    public DeleteMensaje() {}

    public DeleteMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}



