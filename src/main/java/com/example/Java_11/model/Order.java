package com.example.Java_11.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private String customerFirstName;
    private String customerLastName;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;

    public Order(String customerFirstName, String customerLastName, LocalDateTime orderDate, BigDecimal totalPrice)
    {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

}
