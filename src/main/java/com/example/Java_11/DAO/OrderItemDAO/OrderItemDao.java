package com.example.Java_11.DAO.OrderItemDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.OrderItem;

import java.time.LocalDateTime;

public interface OrderItemDao extends CRUDInterface<OrderItem> {
    public int countDrinkOrdersByDate(LocalDateTime date);
    public int countDessertOrdersByDate(LocalDateTime date);
}
