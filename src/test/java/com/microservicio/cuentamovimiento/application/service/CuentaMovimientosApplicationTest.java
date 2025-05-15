package com.microservicio.cuentamovimiento.application.service;

import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import com.microservicio.cuentamovimiento.domain.model.Movimiento;
import com.microservicio.cuentamovimiento.domain.port.out.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CuentaMovimientosApplicationTest {
    @Autowired
    private GestionCuentaServiceImpl cuentaService;

    @Autowired
    private GestionMovimientoServiceImpl movimientoService;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Test
    void crearCuentaExitosamente() {
        Cuenta cuenta = new Cuenta("CTA100", "Ahorro", new BigDecimal("1000.00"), true, "CL100");
        Cuenta creada = cuentaService.crearCuenta(cuenta);

        assertNotNull(creada);
        assertEquals("CTA100", creada.getNumeroCuenta());
    }

    @Test
    void registrarDepositoYActualizarSaldo() {
        cuentaService.crearCuenta(new Cuenta("CTA101", "Ahorro", new BigDecimal("1000.00"), true, "CL101"));

        Movimiento deposito = new Movimiento(null, LocalDate.now(), "DEPOSITO", new BigDecimal("400.00"), null, "CTA101");
        Movimiento resultado = movimientoService.registrarMovimiento(deposito);

        assertEquals(new BigDecimal("1400.00"), resultado.getSaldo());
    }

    @Test
    void registrarRetiroYActualizarSaldo() {
        cuentaService.crearCuenta(new Cuenta("CTA102", "Corriente", new BigDecimal("1200.00"), true, "CL102"));

        Movimiento retiro = new Movimiento(null, LocalDate.now(), "RETIRO", new BigDecimal("200.00"), null, "CTA102");
        Movimiento resultado = movimientoService.registrarMovimiento(retiro);

        assertEquals(new BigDecimal("1000.00"), resultado.getSaldo());
    }

    @Test
    void retiroConSaldoInsuficienteLanzaExcepcion() {
        cuentaService.crearCuenta(new Cuenta("CTA103", "Ahorro", new BigDecimal("100.00"), true, "CL103"));

        Movimiento retiro = new Movimiento(null, LocalDate.now(), "RETIRO", new BigDecimal("200.00"), null, "CTA103");

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            movimientoService.registrarMovimiento(retiro);
        });

        assertEquals("Saldo no disponible", ex.getMessage());
    }

    @Test
    void consultarMovimientosPorCuenta() {
        cuentaService.crearCuenta(new Cuenta("CTA105", "Ahorro", new BigDecimal("500.00"), true, "CL105"));
        movimientoService.registrarMovimiento(new Movimiento(null, LocalDate.now(), "DEPOSITO", new BigDecimal("100.00"), null, "CTA105"));

        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta("CTA105");

        assertFalse(movimientos.isEmpty());
        assertEquals("DEPOSITO", movimientos.get(0).getTipoMovimiento());
    }

    @Test
    void consultarMovimientosPorCuentaYFechas() {
        cuentaService.crearCuenta(new Cuenta("CTA108", "Ahorro", new BigDecimal("800.00"), true, "CL108"));

        movimientoService.registrarMovimiento(new Movimiento(null, LocalDate.of(2024, 5, 10), "DEPOSITO", new BigDecimal("200.00"), null, "CTA108"));
        movimientoService.registrarMovimiento(new Movimiento(null, LocalDate.of(2024, 6, 1), "RETIRO", new BigDecimal("100.00"), null, "CTA108"));

        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuentaYFechas(
                "CTA108", LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 31)
        );

        assertEquals(1, movimientos.size());
        assertEquals("DEPOSITO", movimientos.get(0).getTipoMovimiento());
    }

    @Test
    void crearCuentaYRegistrarDeposito() {

        Cuenta cuenta = new Cuenta("CTA001", "Ahorro", new BigDecimal("1000.00"), true, "CL001");
        Cuenta cuentaCreada = cuentaService.crearCuenta(cuenta);
        assertNotNull(cuentaCreada);

        Movimiento deposito = new Movimiento(null, LocalDate.now(), "DEPOSITO", new BigDecimal("500.00"), null, "CTA001");
        Movimiento resultado = movimientoService.registrarMovimiento(deposito);

        assertEquals(new BigDecimal("1500.00"), resultado.getSaldo());

        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta("CTA001");
        assertFalse(movimientos.isEmpty());
        assertEquals("DEPOSITO", movimientos.get(0).getTipoMovimiento());
        assertEquals(new BigDecimal("1500.00"), movimientos.get(0).getSaldo());
    }
}
