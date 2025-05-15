package com.microservicio.cuentamovimiento.adapter.out.persistence.mapper;

import com.microservicio.cuentamovimiento.adapter.out.persistence.entity.CuentaEntity;
import com.microservicio.cuentamovimiento.domain.model.Cuenta;

public class CuentaMapper {
    public static CuentaEntity toEntity(Cuenta cuenta) {
        CuentaEntity entity = new CuentaEntity();
        entity.setNumeroCuenta(cuenta.getNumeroCuenta());
        entity.setTipoCuenta(cuenta.getTipoCuenta());
        entity.setSaldoInicial(cuenta.getSaldoInicial());
        entity.setEstado(cuenta.getEstado());
        entity.setClienteId(cuenta.getClienteId());
        return entity;
    }
    public static Cuenta toDomain(CuentaEntity entity) {
        return new Cuenta(
                entity.getNumeroCuenta(),
                entity.getTipoCuenta(),
                entity.getSaldoInicial(),
                entity.getEstado(),
                entity.getClienteId()
        );
    }
}
