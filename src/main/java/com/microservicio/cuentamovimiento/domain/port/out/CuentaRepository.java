package com.microservicio.cuentamovimiento.domain.port.out;

import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository {

    Cuenta guardar(Cuenta cuenta);

    Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta);

    List<Cuenta> obtenerTodas();

    void eliminar(String numeroCuenta);

    List<Cuenta> buscarPorClienteId(String clienteId);
}
