package com.example.Java_11.DAO.CustomerDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerDao extends CRUDInterface<Customer> {
    public void updateDiscount(long id, BigDecimal discount);
    public void deleteCustomerById(long id);
    public BigDecimal findMinDiscount();
    public BigDecimal findMaxDiscount();
    public List<Customer> findCustomersWithMinDiscount();
    public List<Customer> findCustomersWithMaxDiscount();
    public BigDecimal findAverageDiscount();
    public Customer findYoungestCustomer();
    public Customer findOldestCustomer();
    public List<Customer> findCustomersWithBirthdayToday();
    public List<Customer> findCustomersWithoutEmail();
}
