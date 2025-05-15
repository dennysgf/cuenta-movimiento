package com.microservicio.cuentamovimiento.adapter.in.rest;


import com.microservicio.cuentamovimiento.adapter.in.rest.dto.CuentaDTO;
import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import com.microservicio.cuentamovimiento.domain.port.in.GestionCuentaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cuentas")
public class CuentaController {
    private final GestionCuentaUseCase cuentaUseCase;

    public CuentaController(GestionCuentaUseCase cuentaUseCase) {
        this.cuentaUseCase = cuentaUseCase;
    }

    @Operation(summary = "Crear nueva cuenta bancaria")
    @PostMapping
    public ResponseEntity<Cuenta> crear(@Valid @RequestBody CuentaDTO cuentaDto) {
        Cuenta cuenta = new Cuenta(
                cuentaDto.getNumeroCuenta(),
                cuentaDto.getTipoCuenta(),
                cuentaDto.getSaldoInicial(),
                cuentaDto.getEstado(),
                cuentaDto.getClienteId()
        );
        return ResponseEntity.ok(cuentaUseCase.crearCuenta(cuenta));
    }

    @Operation(summary = "Actualizar una cuenta existente")
    @PutMapping
    public ResponseEntity<Cuenta> actualizar(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaUseCase.actualizarCuenta(cuenta));
    }

    @Operation(summary = "Listar todas las cuentas")
    @GetMapping
    public ResponseEntity<List<Cuenta>> listar() {
        return ResponseEntity.ok(cuentaUseCase.listarCuentas());
    }

    @Operation(summary = "Obtener cuenta por número")
    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> obtener(@PathVariable String numeroCuenta) {
        return cuentaUseCase.obtenerCuentaPorNumero(numeroCuenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar cuenta por número")
    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<Void> eliminar(@PathVariable String numeroCuenta) {
        cuentaUseCase.eliminarCuenta(numeroCuenta);
        return ResponseEntity.noContent().build();
    }
}
