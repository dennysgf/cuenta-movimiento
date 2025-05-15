package com.microservicio.cuentamovimiento.adapter.out.persistence.repository;

import com.microservicio.cuentamovimiento.adapter.out.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringCuentaJpaRepository extends JpaRepository<CuentaEntity, String> {
}
