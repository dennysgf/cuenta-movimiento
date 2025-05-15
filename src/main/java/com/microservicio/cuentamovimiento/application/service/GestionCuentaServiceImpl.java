package com.microservicio.cuentamovimiento.application.service;

import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import com.microservicio.cuentamovimiento.domain.port.in.GestionCuentaUseCase;
import com.microservicio.cuentamovimiento.domain.port.out.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestionCuentaServiceImpl implements GestionCuentaUseCase {

    private final CuentaRepository cuentaRepository;

    public GestionCuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        boolean existe = cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta()).isPresent();
        if (existe) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese número.");
        }
        return cuentaRepository.guardar(cuenta);
    }

    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        Optional<Cuenta> cuentaExistente = cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta());

        if (cuentaExistente.isEmpty()) {
            throw new IllegalArgumentException("La cuenta no existe.");
        }

        return cuentaRepository.guardar(cuenta);
    }

    @Override
    public List<Cuenta> listarCuentas() {
        return cuentaRepository.obtenerTodas();
    }

    @Override
    public Optional<Cuenta> obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.buscarPorNumeroCuenta(numeroCuenta);
    }

    @Override
    public void eliminarCuenta(String numeroCuenta) {
        cuentaRepository.eliminar(numeroCuenta);
    }
}
