package com.luciano.springboot.ms.producer.msproducer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luciano.springboot.ms.producer.msproducer.models.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@Service
@Slf4j
public class OrderService implements IOrderService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${kafka.topic}")
    private String topic;

    public OrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Mono<Void> sendOrder(OrderDto orderDto) {
        return Mono.fromCallable(() -> {
                    String orderJson = objectMapper.writeValueAsString(orderDto);
                    log.info("Enviando mensaje: {}", orderJson);
                    return orderJson;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(orderJson -> Mono.fromFuture(kafkaTemplate.send(topic, orderJson)).then())
                .doOnError(e -> log.error("Error al enviar la orden: {}", e.getMessage()));
    }
}
