package com.microservicio.cuentamovimiento.adapter.out.persistence.repository;

import com.microservicio.cuentamovimiento.adapter.out.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringCuentaJpaRepository extends JpaRepository<CuentaEntity, String> {
    List<CuentaEntity> findByClienteId(String clienteId);

}
