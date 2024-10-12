package com.luciano.springboot.ms.producer.msproducer.controllers;

import com.luciano.springboot.ms.producer.msproducer.models.OrderDto;
import com.luciano.springboot.ms.producer.msproducer.services.IOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/producer")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createOrder(@Valid @RequestBody OrderDto orderDto) {
        return orderService.sendOrder(orderDto)
                .then(Mono.just(new ResponseEntity<>("Order sent successfully", HttpStatus.CREATED)))
                .onErrorResume(e -> {
                    return Mono.just(new ResponseEntity<>("Error sending order: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }
}
