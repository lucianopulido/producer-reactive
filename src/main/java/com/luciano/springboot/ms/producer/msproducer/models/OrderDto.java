package com.luciano.springboot.ms.producer.msproducer.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @NotEmpty(message = "El campo orderId no debe ser nulo ni vacio")
    private String orderId;
    @NotEmpty(message = "El campo customerId no debe ser nulo ni vacio")
    private String customerId;
    @NotEmpty(message = "El campo products no debe ser nulo ni vacio")
    @Size(min = 1, message = "La lista de productos debe ser de al menos un elemento")
    private List <String> products;
}
