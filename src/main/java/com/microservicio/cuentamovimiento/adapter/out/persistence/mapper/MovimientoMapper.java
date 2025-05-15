package com.microservicio.cuentamovimiento.adapter.out.persistence.mapper;

import com.microservicio.cuentamovimiento.adapter.out.persistence.entity.MovimientoEntity;
import com.microservicio.cuentamovimiento.domain.model.Movimiento;

public class MovimientoMapper {

    public static MovimientoEntity toEntity(Movimiento movimiento) {
        MovimientoEntity entity = new MovimientoEntity();
        entity.setId(movimiento.getId());
        entity.setFecha(movimiento.getFecha());
        entity.setTipoMovimiento(movimiento.getTipoMovimiento());
        entity.setValor(movimiento.getValor());
        entity.setSaldo(movimiento.getSaldo());
        entity.setNumeroCuenta(movimiento.getNumeroCuenta());
        return entity;
    }

    public static Movimiento toDomain(MovimientoEntity entity) {
        return new Movimiento(
                entity.getId(),
                entity.getFecha(),
                entity.getTipoMovimiento(),
                entity.getValor(),
                entity.getSaldo(),
                entity.getNumeroCuenta()
        );
    }
}
