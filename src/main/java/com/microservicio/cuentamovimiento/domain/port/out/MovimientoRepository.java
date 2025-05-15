package com.microservicio.cuentamovimiento.domain.port.out;

import com.microservicio.cuentamovimiento.domain.model.Movimiento;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository {
    Movimiento guardar(Movimiento movimiento);

    List<Movimiento> buscarPorNumeroCuenta(String numeroCuenta);

    List<Movimiento> buscarPorCuentaYFechas(String numeroCuenta, LocalDate desde, LocalDate hasta);
}