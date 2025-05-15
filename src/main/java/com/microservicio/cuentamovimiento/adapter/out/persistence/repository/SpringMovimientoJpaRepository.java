package com.microservicio.cuentamovimiento.adapter.out.persistence.repository;

import com.microservicio.cuentamovimiento.adapter.out.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringMovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {

    List<MovimientoEntity> findByNumeroCuenta(String numeroCuenta);

    List<MovimientoEntity> findByNumeroCuentaAndFechaBetween(String numeroCuenta, LocalDate desde, LocalDate hasta);
}
