package com.microservicio.cuentamovimiento.adapter.out.persistence;

import com.microservicio.cuentamovimiento.adapter.out.persistence.entity.MovimientoEntity;
import com.microservicio.cuentamovimiento.adapter.out.persistence.mapper.MovimientoMapper;
import com.microservicio.cuentamovimiento.adapter.out.persistence.repository.SpringMovimientoJpaRepository;
import com.microservicio.cuentamovimiento.domain.model.Movimiento;
import com.microservicio.cuentamovimiento.domain.port.out.MovimientoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaMovimientoRepositoryAdapter implements MovimientoRepository {

    private final SpringMovimientoJpaRepository jpaRepository;

    public JpaMovimientoRepositoryAdapter(SpringMovimientoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    @Override
    public Movimiento guardar(Movimiento movimiento) {
        MovimientoEntity entity = MovimientoMapper.toEntity(movimiento);
        return MovimientoMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public List<Movimiento> buscarPorNumeroCuenta(String numeroCuenta) {
        return jpaRepository.findByNumeroCuenta(numeroCuenta).stream()
                .map(MovimientoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimiento> buscarPorCuentaYFechas(String numeroCuenta, LocalDate desde, LocalDate hasta) {
        return jpaRepository.findByNumeroCuentaAndFechaBetween(numeroCuenta, desde, hasta).stream()
                .map(MovimientoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
