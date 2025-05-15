-- Crear base de datos
CREATE DATABASE IF NOT EXISTS cuentamovimientodb;
\c cuentamovimientodb;


CREATE TABLE IF NOT EXISTS cuenta (
                                      numero_cuenta VARCHAR(20) PRIMARY KEY,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(10, 2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id VARCHAR(20) NOT NULL,
    cliente_nombre VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS movimiento (
                                          id SERIAL PRIMARY KEY,
                                          fecha DATE NOT NULL,
                                          tipo_movimiento VARCHAR(20) NOT NULL CHECK (tipo_movimiento IN ('DEPOSITO', 'RETIRO')),
    valor DECIMAL(10, 2) NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL,
    FOREIGN KEY (numero_cuenta) REFERENCES cuenta(numero_cuenta)
    );

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id, cliente_nombre) VALUES
                                                                                                       ('470751', 'Ahorro', 800.00, TRUE, 'CL108', 'Juan Pérez'),
                                                                                                       ('478758', 'Ahorro', 2000.00, TRUE, 'CL123', 'María Gómez');


INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
                                                                                 ('2025-05-14', 'DEPOSITO', 500.00, 2500.00, '478758'),
                                                                                 ('2025-05-14', 'RETIRO', 300.00, 2200.00, '478758'),
                                                                                 ('2024-05-10', 'DEPOSITO', 200.00, 1000.00, '470751');
