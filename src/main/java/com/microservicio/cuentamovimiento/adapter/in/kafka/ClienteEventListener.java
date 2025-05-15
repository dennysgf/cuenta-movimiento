package com.microservicio.cuentamovimiento.adapter.in.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.cuentamovimiento.domain.model.ClienteCreadoEvent;
import com.microservicio.cuentamovimiento.domain.port.in.ClienteCreadoUseCase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventListener {

    private final ClienteCreadoUseCase clienteCreadoUseCase;
    private final ObjectMapper objectMapper;

    public ClienteEventListener(ClienteCreadoUseCase clienteCreadoUseCase) {
        this.clienteCreadoUseCase = clienteCreadoUseCase;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "cliente-creado-topic", groupId = "cuenta-movimientos-group")
    public void escucharClienteCreado(ConsumerRecord<String, String> record) {
        try {
            ClienteCreadoEvent evento = objectMapper.readValue(record.value(), ClienteCreadoEvent.class);
            clienteCreadoUseCase.procesarClienteCreado(evento);
        } catch (Exception ex) {
            throw new RuntimeException("Error al procesar evento ClienteCreado", ex);
        }
    }
}
