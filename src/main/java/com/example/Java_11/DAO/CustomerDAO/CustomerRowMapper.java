package com.example.Java_11.DAO.CustomerDAO;

import com.example.Java_11.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CustomerRowMapper implements RowMapper<Customer> {
    public Customer mapRow(ResultSet result, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(result.getLong("customer_id"));
        customer.setFirstName(result.getString("first_name"));
        customer.setLastName(result.getString("last_name"));
        customer.setPatronymic(result.getString("patronymic"));
        customer.setBirthDate(result.getDate("birth_date").toLocalDate());
        customer.setPhone(result.getString("phone"));
        customer.setEmail(result.getString("email"));
        customer.setDiscount(result.getBigDecimal("discount"));
        return customer;
    }
}
