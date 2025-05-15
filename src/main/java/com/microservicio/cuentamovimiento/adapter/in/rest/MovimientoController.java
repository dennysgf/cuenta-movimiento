package com.microservicio.cuentamovimiento.adapter.in.rest;


import com.microservicio.cuentamovimiento.domain.model.Movimiento;
import com.microservicio.cuentamovimiento.domain.port.in.GestionMovimientoUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final GestionMovimientoUseCase movimientoUseCase;

    public MovimientoController(GestionMovimientoUseCase movimientoUseCase) {
        this.movimientoUseCase = movimientoUseCase;
    }

    @PostMapping
    public ResponseEntity<Movimiento> registrar(@RequestBody Movimiento movimiento) {
        return ResponseEntity.ok(movimientoUseCase.registrarMovimiento(movimiento));
    }

    @PutMapping
    public ResponseEntity<Movimiento> actualizar(@RequestBody Movimiento movimiento) {
        return ResponseEntity.ok(movimientoUseCase.actualizarMovimiento(movimiento));
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<List<Movimiento>> listarPorCuenta(@PathVariable String numeroCuenta) {
        return ResponseEntity.ok(movimientoUseCase.obtenerMovimientosPorCuenta(numeroCuenta));
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<Movimiento>> reportePorFechas(
            @RequestParam String numeroCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        return ResponseEntity.ok(
                movimientoUseCase.obtenerMovimientosPorCuentaYFechas(numeroCuenta, desde, hasta)
        );
    }
}
