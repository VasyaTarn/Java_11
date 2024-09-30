package com.example.Java_11.menu;


import com.example.Java_11.DAO.CustomerDAO.CustomerDao;
import com.example.Java_11.DAO.MenuItemDAO.MenuItemDao;
import com.example.Java_11.DAO.StaffDAO.StaffDao;
import com.example.Java_11.model.MenuItem;
import com.example.Java_11.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Scanner;

@Service
public class Menu {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private MenuItemDao menuItemDao;

    @Autowired
    private StaffDao staffDao;

    public void startMenu()
    {
        System.out.println("-".repeat(20) + " Menu " + "-".repeat(20));

        System.out.println("1. Adding a new item to the cafe's assortment");
        System.out.println("2. Adding information about new staff");
        System.out.println("3. Change the price of a specific type of product");
        System.out.println("4. Change mailing address for confectioner");
        System.out.println("5. Change barista contact phone number");
        System.out.println("6. Change the discount percentage of a specific customer");
        System.out.println("7. Delete information about a specific product type");
        System.out.println("8. Delete information about a specific waiter");
        System.out.println("9. Delete information about a specific barista");
        System.out.println("10. Delete information about a specific customer");
        System.out.println("11. Show all products of a certain type");
        System.out.println("12. Show staff information");

        System.out.println("Choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            menuItem1Execute();
        }
        if (choice == 2) {
            menuItem2Execute();
        }
        if (choice == 3) {
            menuItem3Execute();
        }
        if (choice == 4) {
            menuItem4Execute();
        }
        if (choice == 5) {
            menuItem5Execute();
        }
        if (choice == 6) {
            menuItem6Execute();
        }
        if (choice == 7) {
            menuItem7Execute();
        }
        if (choice == 8) {
            menuItem8Execute();
        }
        if (choice == 9) {
            menuItem9Execute();
        }
        if (choice == 10) {
            menuItem10Execute();
        }
        if (choice == 11) {
            menuItem11Execute();
        }
        if (choice == 12) {
            menuItem12Execute();
        }

    }

    public void menuItem1Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name in English: ");
        String nameEn = scanner.nextLine();

        System.out.print("Enter the name in another language: ");
        String nameOther = scanner.nextLine();

        System.out.print("Enter the item type (e.g., 1 for drinks, 2 for desserts): ");
        long itemType = scanner.nextLong();

        System.out.print("Enter the price (e.g., 9.99): ");
        BigDecimal price = scanner.nextBigDecimal();

        MenuItem newItem = new MenuItem(nameEn, nameOther, itemType, price);

        menuItemDao.save(newItem);
    }

    public void menuItem2Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter patronymic: ");
        String patronymic = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter position (e.g., 1 for Barista, 2 for Waiter, 3 for Confectioner): ");
        long position = scanner.nextLong();

        Staff newStaff = new Staff(firstName, lastName, patronymic, phone, email, position);

        staffDao.save(newStaff);
    }

    public void menuItem3Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the item type (e.g., 1 for drinks, 2 for desserts): ");
        long itemType = scanner.nextLong();

        System.out.print("Enter the new price (e.g., 12.99): ");
        BigDecimal newPrice = scanner.nextBigDecimal();

        menuItemDao.updatePriceByItemType(itemType, newPrice);
    }

    public void menuItem4Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the new email for the confectioner: ");
        String newEmail = scanner.nextLine();

        staffDao.updateEmailByConfectioner(newEmail);
    }

    public void menuItem5Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the new phone number for the barista: ");
        String newPhone = scanner.nextLine();

        staffDao.updatePhoneByBarista(newPhone);
    }

    public void menuItem6Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ID of the item to update: ");
        long id = scanner.nextLong();

        System.out.print("Enter the new discount (e.g., 0.15 for 15%): ");
        BigDecimal discount = scanner.nextBigDecimal();

        customerDao.updateDiscount(id, discount);
    }

    public void menuItem7Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the item type ID to delete: ");
        long itemTypeId = scanner.nextLong();

        menuItemDao.deleteByItemType(itemTypeId);
    }

    public void menuItem8Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the staff ID of the waiter to delete: ");
        long staffId = scanner.nextLong();

        staffDao.deleteStaffByWaiter(staffId);
    }

    public void menuItem9Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the staff ID of the barista to delete: ");
        long staffId = scanner.nextLong();

        staffDao.deleteStaffByBarista(staffId);
    }

    public void menuItem10Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the customer ID to delete: ");
        long id = scanner.nextLong();

        customerDao.deleteCustomerById(id);
    }

    public void menuItem11Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the item type ID to retrieve menu items: ");
        long itemTypeId = scanner.nextLong();

        System.out.println("Menu items of type ID " + itemTypeId + ":");
        for (MenuItem item : menuItemDao.getMenuItemsByType(itemTypeId)) {
            System.out.println(item);
        }
    }

    public void menuItem12Execute()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the position ID to retrieve staff members: ");
        long positionId = scanner.nextLong();

        System.out.println("Staff members with position ID " + positionId + ":");
        for (Staff staff : staffDao.getStuffByPosition(positionId)) {
            System.out.println(staff);
        }
    }

}
