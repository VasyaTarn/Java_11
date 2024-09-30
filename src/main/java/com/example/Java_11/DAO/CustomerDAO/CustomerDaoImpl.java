package com.example.Java_11.DAO.CustomerDAO;
import com.example.Java_11.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<Customer> customerRowMapper;

    @Override
    public void updateDiscount(long id, BigDecimal discount)
    {
        String sql = "UPDATE public.Customers SET discount = ? WHERE customer_id = ?";

        try
        {
            jdbcTemplate.update(sql, discount, id);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteCustomerById(long id)
    {
        String sql = "DELETE FROM public.Customers WHERE customer_id = ?";

        try
        {
            jdbcTemplate.update(sql, id);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Customer customer) {
        String sql =  "INSERT INTO public.Customers (first_name, last_name, patronymic, birth_date, phone, email, discount) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql,
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getPatronymic(),
                    Date.valueOf(customer.getBirthDate()),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getDiscount());
        }
        catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Customer> customers) {
        String sql = "INSERT INTO public.Customers (first_name, last_name, patronymic, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Customer customer = customers.get(i);
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setString(3, customer.getPatronymic());
                    ps.setDate(4, Date.valueOf(customer.getBirthDate()));
                    ps.setString(5, customer.getPhone());
                    ps.setString(6, customer.getEmail());
                    ps.setBigDecimal(7, customer.getDiscount());
                }

                @Override
                public int getBatchSize() {
                    return customers.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE public.Customers SET first_name = ?, last_name = ?, patronymic = ?, birth_date = ?, phone = ?, email = ?, discount = ? WHERE customer_id = ?";

        try
        {
            jdbcTemplate.update(sql,
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getPatronymic(),
                    Date.valueOf(customer.getBirthDate()),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getDiscount(),
                    customer.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Customer customer) {
        String sql = "DELETE FROM public.Customers WHERE customer_id = ?";

        try
        {
            jdbcTemplate.update(sql, customer.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<Customer> findAll() {
        String sql = "SELECT * FROM public.Customers";

        try
        {
            return jdbcTemplate.query(sql, customerRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.Customers";

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
    public BigDecimal findMinDiscount() {
        String sql = "SELECT MIN(discount) FROM public.Customers";

        try
        {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return BigDecimal.ZERO;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal findMaxDiscount() {
        String sql = "SELECT MAX(discount) FROM public.Customers";

        try
        {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findCustomersWithMinDiscount() {
        String sql = "SELECT * FROM public.Customers WHERE discount = (SELECT MIN(discount) FROM public.Customers)";

        try
        {
            return jdbcTemplate.query(sql, customerRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findCustomersWithMaxDiscount() {
        String sql = "SELECT * FROM public.Customers WHERE discount = (SELECT MAX(discount) FROM public.Customers)";

        try
        {
            return jdbcTemplate.query(sql, customerRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal findAverageDiscount() {
        String sql = "SELECT AVG(discount) FROM public.Customers";

        try
        {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findYoungestCustomer() {
        String sql = "SELECT * FROM public.Customers ORDER BY birth_date DESC LIMIT 1";

        try
        {
            return jdbcTemplate.queryForObject(sql, customerRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findOldestCustomer() {
        String sql = "SELECT * FROM public.Customers ORDER BY birth_date ASC LIMIT 1";

        try
        {
            return jdbcTemplate.queryForObject(sql, customerRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findCustomersWithBirthdayToday() {
        String sql = "SELECT * FROM public.Customers WHERE EXTRACT(MONTH FROM birth_date) = EXTRACT(MONTH FROM CURRENT_DATE) " +
                "AND EXTRACT(DAY FROM birth_date) = EXTRACT(DAY FROM CURRENT_DATE)";

        try
        {
            return jdbcTemplate.query(sql, customerRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findCustomersWithoutEmail() {
        String sql = "SELECT * FROM public.Customers WHERE email IS NULL OR email = ''";

        try
        {
            return jdbcTemplate.query(sql, customerRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

}
