package com.microservicio.cuentamovimiento.domain.port.in;

import com.microservicio.cuentamovimiento.domain.model.ClienteCreadoEvent;

public interface ClienteCreadoUseCase {
    void procesarClienteCreado(ClienteCreadoEvent cliente);
}
