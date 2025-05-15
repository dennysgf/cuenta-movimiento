package com.microservicio.cuentamovimiento.domain.model;

import java.math.BigDecimal;

public class Cuenta {
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String clienteId;

    public Cuenta() {
    }
    public Cuenta(String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial, Boolean estado, String clienteId) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.clienteId = clienteId;
    }

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

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}
