package com.example.Java_11.DAO.OrderDAO;

import com.example.Java_11.model.MenuItem;
import com.example.Java_11.model.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class OrderRowMapper implements RowMapper<Order>{
    public Order mapRow(ResultSet result, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(result.getLong("order_id"));
        order.setCustomerFirstName(result.getString("customer_first_name"));
        order.setCustomerLastName(result.getString("customer_last_name"));
        order.setOrderDate(result.getTimestamp("order_date").toLocalDateTime());
        order.setTotalPrice(result.getBigDecimal("total_price"));

        return order;
    }
}
