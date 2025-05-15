package com.microservicio.cuentamovimiento.application.service;

import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import com.microservicio.cuentamovimiento.domain.model.Movimiento;
import com.microservicio.cuentamovimiento.domain.port.out.CuentaRepository;
import com.microservicio.cuentamovimiento.domain.port.out.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class GestionMovimientoServiceImplTest {

    private MovimientoRepository movimientoRepository;
    private CuentaRepository cuentaRepository;
    private GestionMovimientoServiceImpl movimientoService;

    @BeforeEach
    void setUp() {
        movimientoRepository = mock(MovimientoRepository.class);
        cuentaRepository = mock(CuentaRepository.class);
        movimientoService = new GestionMovimientoServiceImpl(movimientoRepository, cuentaRepository);
    }

    @Test
    void registrarDepositoExitoso() {
        Cuenta cuenta = new Cuenta("123456", "Ahorro", new BigDecimal("1000.00"), true, "CL001", null);
        Movimiento movimiento = new Movimiento(null, LocalDate.now(), "DEPOSITO", new BigDecimal("500.00"), null, "123456");

        when(cuentaRepository.buscarPorNumeroCuenta("123456")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.guardar(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(cuentaRepository.guardar(any())).thenReturn(cuenta);

        Movimiento resultado = movimientoService.registrarMovimiento(movimiento);

        assertEquals(new BigDecimal("1500.00"), resultado.getSaldo());
        verify(movimientoRepository).guardar(movimiento);
        verify(cuentaRepository).guardar(cuenta);
    }

    @Test
    void registrarRetiroConSaldoSuficiente() {
        Cuenta cuenta = new Cuenta("123456", "Ahorro", new BigDecimal("1000.00"), true, "CL001", null);
        Movimiento movimiento = new Movimiento(null, LocalDate.now(), "RETIRO", new BigDecimal("200.00"), null, "123456");

        when(cuentaRepository.buscarPorNumeroCuenta("123456")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.guardar(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(cuentaRepository.guardar(any())).thenReturn(cuenta);

        Movimiento resultado = movimientoService.registrarMovimiento(movimiento);

        assertEquals(new BigDecimal("800.00"), resultado.getSaldo());
    }

    @Test
    void registrarRetiroConSaldoInsuficiente() {
        Cuenta cuenta = new Cuenta("123456", "Ahorro", new BigDecimal("100.00"), true, "CL001", null);
        Movimiento movimiento = new Movimiento(null, LocalDate.now(), "RETIRO", new BigDecimal("200.00"), null, "123456");

        when(cuentaRepository.buscarPorNumeroCuenta("123456")).thenReturn(Optional.of(cuenta));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> movimientoService.registrarMovimiento(movimiento));

        assertEquals("Saldo no disponible", ex.getMessage());
    }

    @Test
    void obtenerMovimientosPorCuenta() {
        Movimiento movimiento = new Movimiento(1L, LocalDate.now(), "DEPOSITO", new BigDecimal("300.00"), new BigDecimal("1300.00"), "123456");

        when(movimientoRepository.buscarPorNumeroCuenta("123456"))
                .thenReturn(List.of(movimiento));

        List<Movimiento> resultado = movimientoService.obtenerMovimientosPorCuenta("123456");

        assertEquals(1, resultado.size());
        assertEquals("DEPOSITO", resultado.get(0).getTipoMovimiento());
    }

}
