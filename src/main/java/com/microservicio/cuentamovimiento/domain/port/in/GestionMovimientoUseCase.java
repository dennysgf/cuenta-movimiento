package com.microservicio.cuentamovimiento.domain.port.in;

import com.microservicio.cuentamovimiento.domain.model.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface GestionMovimientoUseCase {

    Movimiento registrarMovimiento(Movimiento movimiento);

    Movimiento actualizarMovimiento(Movimiento movimiento);

    List<Movimiento> obtenerMovimientosPorCuenta(String numeroCuenta);

    List<Movimiento> obtenerMovimientosPorCuentaYFechas(String numeroCuenta, LocalDate desde, LocalDate hasta);

}
