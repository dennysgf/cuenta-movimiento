package com.microservicio.cuentamovimiento.adapter.out.persistence;

import com.microservicio.cuentamovimiento.adapter.out.persistence.entity.CuentaEntity;
import com.microservicio.cuentamovimiento.adapter.out.persistence.mapper.CuentaMapper;
import com.microservicio.cuentamovimiento.adapter.out.persistence.repository.SpringCuentaJpaRepository;
import com.microservicio.cuentamovimiento.domain.model.Cuenta;
import com.microservicio.cuentamovimiento.domain.port.out.CuentaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaCuentaRepositoryAdapter implements CuentaRepository {

    private final SpringCuentaJpaRepository jpaRepository;

    public JpaCuentaRepositoryAdapter(SpringCuentaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    @Override
    public Cuenta guardar(Cuenta cuenta) {
        CuentaEntity entity = CuentaMapper.toEntity(cuenta);
        return CuentaMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta) {
        return jpaRepository.findById(numeroCuenta)
                .map(CuentaMapper::toDomain);
    }

    @Override
    public List<Cuenta> obtenerTodas() {
        return jpaRepository.findAll().stream()
                .map(CuentaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(String numeroCuenta) {
        jpaRepository.deleteById(numeroCuenta);
    }
}
