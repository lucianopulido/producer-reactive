package com.luciano.springboot.ms.producer.msproducer.services;

import com.luciano.springboot.ms.producer.msproducer.models.OrderDto;
import reactor.core.publisher.Mono;

public interface IOrderService {
    Mono<Void> sendOrder(OrderDto orderDto);
}
