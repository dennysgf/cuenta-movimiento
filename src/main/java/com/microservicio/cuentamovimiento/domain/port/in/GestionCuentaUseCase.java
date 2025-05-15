package com.microservicio.cuentamovimiento.domain.port.in;

import com.microservicio.cuentamovimiento.domain.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface GestionCuentaUseCase {

    Cuenta crearCuenta(Cuenta cuenta);

    Cuenta actualizarCuenta(Cuenta cuenta);

    List<Cuenta> listarCuentas();

    Optional<Cuenta> obtenerCuentaPorNumero(String numeroCuenta);

    void eliminarCuenta(String numeroCuenta);

}
