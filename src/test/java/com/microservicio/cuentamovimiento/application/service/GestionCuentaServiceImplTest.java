package com.microservicio.cuentamovimiento.application.service;

import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import com.microservicio.cuentamovimiento.domain.port.out.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
public class GestionCuentaServiceImplTest {

    private CuentaRepository cuentaRepository;
    private GestionCuentaServiceImpl cuentaService;

    @BeforeEach
    void setUp() {
        cuentaRepository = mock(CuentaRepository.class);
        cuentaService = new GestionCuentaServiceImpl(cuentaRepository);
    }

    @Test
    void crearCuentaExitosamente() {
        Cuenta cuenta = new Cuenta("123456", "Ahorro", new BigDecimal("1000.00"), true, "CL001");

        when(cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta())).thenReturn(Optional.empty());
        when(cuentaRepository.guardar(cuenta)).thenReturn(cuenta);

        Cuenta resultado = cuentaService.crearCuenta(cuenta);

        assertNotNull(resultado);
        assertEquals("123456", resultado.getNumeroCuenta());
        verify(cuentaRepository).guardar(cuenta);
    }

    @Test
    void actualizarCuentaExistente() {
        Cuenta cuenta = new Cuenta("123456", "Corriente", new BigDecimal("500.00"), true, "CL001");

        when(cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta())).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.guardar(cuenta)).thenReturn(cuenta);

        Cuenta resultado = cuentaService.actualizarCuenta(cuenta);

        assertNotNull(resultado);
        assertEquals("123456", resultado.getNumeroCuenta());
        verify(cuentaRepository).guardar(cuenta);
    }

    @Test
    void listarTodasLasCuentas() {
        List<Cuenta> cuentas = List.of(
                new Cuenta("123456", "Ahorro", new BigDecimal("500.00"), true, "CL001")
        );

        when(cuentaRepository.obtenerTodas()).thenReturn(cuentas);

        List<Cuenta> resultado = cuentaService.listarCuentas();

        assertEquals(1, resultado.size());
        assertEquals("123456", resultado.get(0).getNumeroCuenta());
    }

    @Test
    void obtenerCuentaPorNumero() {
        Cuenta cuenta = new Cuenta("123456", "Ahorro", new BigDecimal("500.00"), true, "CL001");

        when(cuentaRepository.buscarPorNumeroCuenta("123456")).thenReturn(Optional.of(cuenta));

        Optional<Cuenta> resultado = cuentaService.obtenerCuentaPorNumero("123456");

        assertTrue(resultado.isPresent());
        assertEquals("123456", resultado.get().getNumeroCuenta());
    }

    @Test
    void eliminarCuenta() {
        doNothing().when(cuentaRepository).eliminar("123456");

        cuentaService.eliminarCuenta("123456");

        verify(cuentaRepository).eliminar("123456");
    }
}
