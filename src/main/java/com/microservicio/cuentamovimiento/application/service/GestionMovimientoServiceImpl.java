package com.microservicio.cuentamovimiento.application.service;

import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import com.microservicio.cuentamovimiento.domain.model.Movimiento;
import com.microservicio.cuentamovimiento.domain.port.in.GestionMovimientoUseCase;
import com.microservicio.cuentamovimiento.domain.port.out.CuentaRepository;
import com.microservicio.cuentamovimiento.domain.port.out.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class GestionMovimientoServiceImpl implements GestionMovimientoUseCase {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public GestionMovimientoServiceImpl(
            MovimientoRepository movimientoRepository,
            CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }
    @Override
    public Movimiento registrarMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.buscarPorNumeroCuenta(movimiento.getNumeroCuenta())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal nuevoSaldo;

        if ("RETIRO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            if (saldoActual.compareTo(movimiento.getValor()) < 0) {
                throw new IllegalStateException("Saldo no disponible");
            }
            nuevoSaldo = saldoActual.subtract(movimiento.getValor());
        } else if ("DEPOSITO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            nuevoSaldo = saldoActual.add(movimiento.getValor());
        } else {
            throw new IllegalArgumentException("Tipo de movimiento inválido");
        }

        movimiento.setSaldo(nuevoSaldo);
        movimientoRepository.guardar(movimiento);

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.guardar(cuenta);

        return movimiento;
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuenta(String numeroCuenta) {
        return movimientoRepository.buscarPorNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaYFechas(String numeroCuenta, LocalDate desde, LocalDate hasta) {
        return movimientoRepository.buscarPorCuentaYFechas(numeroCuenta, desde, hasta);
    }

    @Override
    public Movimiento actualizarMovimiento(Movimiento movimiento) {
        List<Movimiento> movimientos = movimientoRepository.buscarPorNumeroCuenta(movimiento.getNumeroCuenta());

        boolean existe = movimientos.stream()
                .anyMatch(m -> m.getId().equals(movimiento.getId()));

        if (!existe) {
            throw new IllegalArgumentException("El movimiento no existe.");
        }

        return movimientoRepository.guardar(movimiento);
    }

}
