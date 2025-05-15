package com.microservicio.cuentamovimiento.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteCreadoEvent {

    public ClienteCreadoEvent(String clienteId, String nombre, String identificacion) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.identificacion = identificacion;
    }
    private String clienteId;

    public String getClienteId() {
        return clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    private String nombre;
    private String identificacion;

    public ClienteCreadoEvent(){

    }

}
