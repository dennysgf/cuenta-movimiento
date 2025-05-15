# Microservicio: Cuenta-Movimiento

## Descripción General
Este microservicio forma parte del sistema distribuido basado en microservicios, y se encarga de la gestión de cuentas bancarias y el registro de sus movimientos asociados (depósitos, retiros, transferencias).

## Arquitectura
- **Arquitectura adoptada:** Hexagonal (Ports & Adapters)
- **Comunicación:** Asíncrona mediante mensajería :Kafka 
- **Base de datos:** PostgreSQL 
- **Framework:** Spring Boot
- **Gestión de dependencias:** Maven

## Tecnologías Utilizadas
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Web
- PostgreSQL
- Lombok
- Kafka
- Maven
- Docker & Docker Compose
- Swagger / Springdoc OpenAPI
- Maven

## Ejecución Local
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/dennysgf/cuenta-movimiento.git
   ```
2. Entrar al directorio:
   ```bash
   cd cuenta-movimiento
   ```
3. Ejecutar con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   O bien, usando Docker Compose:
   ```bash
   docker-compose up --build
   ```

## Módulos y Capas
```
cuenta-movimiento/
├── domain/
│ ├── model/
│ └── port/
│ ├── in/
│ └── out/
├── application/
│ └── service/
├── adapter/
│ ├── in/
│ │ └── rest/
│ └── out/
│ └── persistence/
├── config/
└── CuentaMovimientosApplication.java
├── Dockerfile
├── pom.xml
```

## Endpoints Principales
- `GET /cuentas`
- `POST /cuentas`
- `PUT /cuentas/{id}`
- `DELETE /cuentas/{id}`

- `GET /movimientos`
- `POST /movimientos`

## Swagger - Documentación
Una vez en ejecución:
```
http://localhost:8082/swagger-ui.html
```

## Funcionalidades Implementadas (F1-F6, F8)
| Código | Descripción                                | Estado |
|--------|--------------------------------------------|--------|
| F1     | Crear cuenta bancaria                      | ✅     |
| F2     | Consultar datos de una cuenta              | ✅     |
| F3     | Registrar movimiento (retiro, depósito)    | ✅     |
| F4     | Listar movimientos de una cuenta           | ✅     |
| F5     | Validar saldo antes de registrar retiro    | ✅     |
| F6     | Sincronización con `cliente-persona`       | ✅     |
| F7     | Escuchar eventos de cliente creado         | ✅     |
| F8     | Eliminar movimientos por ID de cuenta      | ✅     |
## Consideraciones
- Recibe eventos desde Kafka enviados por `cliente-persona` para registrar cuentas automáticamente si es necesario.
- El servicio valida movimientos de acuerdo al saldo disponible.
- Preparado para despliegue en contenedores.

---

##  Comunicación asincrónica

El microservicio se comunica con `cliente-persona` usando mensajería basada en eventos.

- Escucha evento: `cliente-creado`
- Publica evento: `movimiento-registrado`


---


##  Endpoints REST

### Cuenta

| Método | Endpoint           | Descripción                |
|--------|--------------------|----------------------------|
| POST   | `/cuentas`         | Crear nueva cuenta         |
| GET    | `/cuentas/{id}`    | Obtener cuenta por ID      |
| DELETE | `/cuentas/{id}`    | Eliminar cuenta            |

### Movimiento

| Método | Endpoint                       | Descripción                        |
|--------|--------------------------------|------------------------------------|
| POST   | `/cuentas/{id}/movimientos`   | Crear nuevo movimiento (retiro o depósito) |
| GET    | `/cuentas/{id}/movimientos`   | Listar movimientos de una cuenta   |

---
    Elaborado por: Dennys Belduma

    Propósito: Evaluación técnica backend Java
