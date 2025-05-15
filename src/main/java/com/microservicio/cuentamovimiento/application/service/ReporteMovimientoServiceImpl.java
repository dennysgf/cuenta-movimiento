package com.microservicio.cuentamovimiento.application.service;

import com.microservicio.cuentamovimiento.adapter.in.rest.dto.MovimientoResumenDto;
import com.microservicio.cuentamovimiento.domain.port.in.ReporteMovimientoUseCase;
import com.microservicio.cuentamovimiento.domain.port.out.CuentaRepository;
import com.microservicio.cuentamovimiento.domain.port.out.MovimientoRepository;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicReference;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteMovimientoServiceImpl implements ReporteMovimientoUseCase {


    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public ReporteMovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<MovimientoResumenDto> obtenerResumen(String clienteId, LocalDate desde, LocalDate hasta) {
        var cuentas = cuentaRepository.buscarPorClienteId(clienteId);

        return cuentas.stream()
                .flatMap(cuenta -> {
                    var movimientos = movimientoRepository
                            .buscarPorCuentaYFechas(cuenta.getNumeroCuenta(), desde, hasta);

                    BigDecimal saldoInicial = cuenta.getSaldoInicial();
                    AtomicReference<BigDecimal> saldoRef = new AtomicReference<>(saldoInicial);

                    return movimientos.stream().map(movimiento -> {
                        BigDecimal movimientoValor = movimiento.getValor().multiply(
                                movimiento.getTipoMovimiento().equalsIgnoreCase("RETIRO") ? BigDecimal.valueOf(-1) : BigDecimal.ONE);

                        saldoRef.set(saldoRef.get().add(movimientoValor));

                        return new MovimientoResumenDto(
                                movimiento.getFecha().toString(),
                                cuenta.getClienteNombre(),
                                cuenta.getNumeroCuenta(),
                                cuenta.getTipoCuenta(),
                                saldoInicial,
                                cuenta.getEstado(),
                                movimientoValor,
                                saldoRef.get()
                        );
                    });

                })
                .collect(Collectors.toList());
    }
}
