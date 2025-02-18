package com.example.Java_11.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    private Long id;
    private String nameEn;
    private String nameOther;
    private long itemType;
    private BigDecimal price;

    public MenuItem(String nameEn, String nameOther, long itemType, BigDecimal price)
    {
        this.nameEn = nameEn;
        this.nameOther = nameOther;
        this.itemType = itemType;
        this.price = price;
    }
}

