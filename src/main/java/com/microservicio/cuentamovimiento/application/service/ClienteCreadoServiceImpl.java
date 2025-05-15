package com.microservicio.cuentamovimiento.application.service;

import com.microservicio.cuentamovimiento.domain.model.ClienteCreadoEvent;
import com.microservicio.cuentamovimiento.domain.port.in.ClienteCreadoUseCase;
import org.springframework.stereotype.Service;

@Service
public class ClienteCreadoServiceImpl implements ClienteCreadoUseCase {

    @Override
    public void procesarClienteCreado(ClienteCreadoEvent cliente){
        System.out.println("prueba de procesamiento desde kafka" + cliente.getNombre());
    }
}
