package com.example.Java_11.DAO.OrderItemDAO;

import com.example.Java_11.model.Order;
import com.example.Java_11.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    public OrderItem mapRow(ResultSet result, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(result.getLong("order_item_id"));
        orderItem.setOrderId(result.getLong("order_id"));
        orderItem.setItemId(result.getLong("item_id"));
        orderItem.setQuantity(result.getInt("quantity"));
        orderItem.setPrice(result.getBigDecimal("price"));

        return orderItem;
    }
}
