package com.microservicio.cuentamovimiento.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta")
public class CuentaEntity {
    @Id
    private String numeroCuenta;

    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String clienteId;

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
}
