package com.example.Java_11;

import com.example.Java_11.DAO.CustomerDAO.CustomerDao;
import com.example.Java_11.DAO.OrderDAO.OrderDao;
import com.example.Java_11.DAO.OrderItemDAO.OrderItemDao;
import com.example.Java_11.DAO.WorkScheduleDAO.WorkScheduleDao;
import com.example.Java_11.menu.Menu;
import com.example.Java_11.model.Customer;
import com.example.Java_11.model.Order;
import com.example.Java_11.model.OrderItem;
import com.example.Java_11.model.WorkSchedule;
import com.example.Java_11.service.CafeDbInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class Java11Application {

	@Autowired
	private Menu menu;

	@Autowired
	private CafeDbInitializer cafeDbInitializer;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderItemDao orderItemDao;

	@Autowired
	private WorkScheduleDao workScheduleDao;

	public static void main(String[] args)
	{
		SpringApplication.run(Java11Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		cafeDbInitializer.createTables();
		menu.startMenu();
	}
}
