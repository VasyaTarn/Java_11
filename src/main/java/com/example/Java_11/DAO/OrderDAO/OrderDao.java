package com.example.Java_11.DAO.OrderDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends CRUDInterface<Order> {
    public List<Order> findOrdersByDate(LocalDateTime date);
    public List<Order> findOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    public BigDecimal findAverageOrderAmountByDate(LocalDateTime date);
    public BigDecimal findMaxOrderAmountByDate(LocalDateTime date);
    public Order findCustomerWithMaxOrderByDate(LocalDateTime date);
}
