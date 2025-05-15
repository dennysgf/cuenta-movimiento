package com.microservicio.cuentamovimiento.domain.port.in;

import com.microservicio.cuentamovimiento.adapter.in.rest.dto.MovimientoResumenDto;

import java.time.LocalDate;
import java.util.List;

public interface ReporteMovimientoUseCase {
    List<MovimientoResumenDto> obtenerResumen(String clienteId, LocalDate desde, LocalDate hasta);
}
