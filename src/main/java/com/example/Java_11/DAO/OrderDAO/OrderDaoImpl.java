package com.example.Java_11.DAO.OrderDAO;

import com.example.Java_11.model.MenuItem;
import com.example.Java_11.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<Order> orderRowMapper;

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO public.Orders (customer_first_name, customer_last_name, order_date, total_price) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql,
                    order.getCustomerFirstName(),
                    order.getCustomerLastName(),
                    Timestamp.valueOf(order.getOrderDate()),
                    order.getTotalPrice());
        }
        catch(DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Order> orders) {
        String sql = "INSERT INTO public.Orders (customer_first_name, customer_last_name, order_date, total_price) VALUES (?, ?, ?, ?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Order order = orders.get(i);
                    ps.setString(1, order.getCustomerFirstName());
                    ps.setString(2, order.getCustomerLastName());
                    ps.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
                    ps.setBigDecimal(4, order.getTotalPrice());
                }

                @Override
                public int getBatchSize() {
                    return orders.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        String sql = "UPDATE public.Orders SET customer_first_name = ?, customer_last_name = ?, order_date = ?, total_price = ? WHERE order_id = ?";

        try {
            jdbcTemplate.update(sql,
                    order.getCustomerFirstName(),
                    order.getCustomerLastName(),
                    Timestamp.valueOf(order.getOrderDate()),
                    order.getTotalPrice(),
                    order.getId());
        }
        catch(DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Order order) {
        String sql = "DELETE FROM public.Orders WHERE order_id = ?";

        try
        {
            jdbcTemplate.update(sql, order.getId());
        }
        catch(DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<Order> findAll() {
        String sql = "SELECT * FROM public.Orders";

        try
        {
            return jdbcTemplate.query(sql, orderRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.Orders";

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
    public List<Order> findOrdersByDate(LocalDateTime date) {
        String sql = "SELECT * FROM public.Orders WHERE order_date = ?";

        try
        {
            return jdbcTemplate.query(sql, new Object[]{Timestamp.valueOf(date)}, orderRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM public.Orders WHERE order_date BETWEEN ? AND ?";

        try
        {
            return jdbcTemplate.query(sql, new Object[]{Timestamp.valueOf(startDate), Timestamp.valueOf(endDate)}, orderRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal findAverageOrderAmountByDate(LocalDateTime date) {
        String sql = "SELECT AVG(o.total_price) " + "FROM public.Orders o " + "WHERE o.order_date = ?";

        try
        {
            return jdbcTemplate.queryForObject(sql, new Object[]{date}, BigDecimal.class);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal findMaxOrderAmountByDate(LocalDateTime date) {
        String sql = "SELECT MAX(o.total_price) " + "FROM public.Orders o " + "WHERE o.order_date = ?";

        try
        {
            return jdbcTemplate.queryForObject(sql, new Object[]{date}, BigDecimal.class);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Order findCustomerWithMaxOrderByDate(LocalDateTime date) {
        String sql = "SELECT order_id, customer_first_name, customer_last_name, order_date, total_price " +
                "FROM public.Orders " +
                "WHERE order_date = ? " +
                "ORDER BY total_price DESC " +
                "LIMIT 1;";

        try
        {
            return jdbcTemplate.queryForObject(sql, new Object[]{date}, orderRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
