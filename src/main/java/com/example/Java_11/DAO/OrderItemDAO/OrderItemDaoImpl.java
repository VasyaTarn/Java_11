package com.example.Java_11.DAO.OrderItemDAO;

import com.example.Java_11.model.Order;
import com.example.Java_11.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<OrderItem> orderItemRowMapper;

    @Override
    public void save(OrderItem orderItem) {
        String sql = "INSERT INTO public.OrderItems (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try
        {
            jdbcTemplate.update(sql,
                    orderItem.getOrderId(),
                    orderItem.getItemId(),
                    orderItem.getQuantity(),
                    orderItem.getPrice());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<OrderItem> orderItems) {
        String sql = "INSERT INTO public.OrderItems (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    OrderItem orderItem = orderItems.get(i);
                    ps.setLong(1, orderItem.getOrderId());
                    ps.setLong(2, orderItem.getItemId());
                    ps.setInt(3, orderItem.getQuantity());
                    ps.setBigDecimal(4, orderItem.getPrice());
                }

                @Override
                public int getBatchSize() {
                    return orderItems.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(OrderItem orderItem) {
        String sql = "UPDATE public.OrderItems SET order_id = ?, item_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";

        try
        {
            jdbcTemplate.update(sql,
                    orderItem.getOrderId(),
                    orderItem.getItemId(),
                    orderItem.getQuantity(),
                    orderItem.getPrice(),
                    orderItem.getOrderItemId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(OrderItem orderItem) {
        String sql = "DELETE FROM public.OrderItems WHERE order_item_id = ?";

        try
        {
            jdbcTemplate.update(sql, orderItem.getOrderItemId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<OrderItem> findAll() {
        String sql = "SELECT * FROM public.OrderItems";

        try
        {
            return jdbcTemplate.query(sql, orderItemRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.OrderItems";

        try
        {
            jdbcTemplate.update(sql);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int countDrinkOrdersByDate(LocalDateTime date) {
        String sql = "SELECT COUNT(*) FROM public.OrderItems oi " +
                "JOIN public.MenuItems mi ON oi.item_id = mi.item_id " +
                "WHERE mi.item_type_id = 1 AND oi.order_id IN " +
                "(SELECT order_id FROM public.Orders WHERE order_date = ?)";

        try
        {
            return jdbcTemplate.queryForObject(sql, new Object[]{date}, Integer.class);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int countDessertOrdersByDate(LocalDateTime date) {
        String sql = "SELECT COUNT(*) FROM public.OrderItems oi " +
                "JOIN public.MenuItems mi ON oi.item_id = mi.item_id " +
                "WHERE mi.item_type_id = 2 AND oi.order_id IN " +
                "(SELECT order_id FROM public.Orders WHERE order_date = ?)";

        try
        {
            return jdbcTemplate.queryForObject(sql, new Object[]{date}, Integer.class);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return 0;
        }
    }
}
