package com.example.Java_11.DAO.MenuItemDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.MenuItem;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemDao extends CRUDInterface<MenuItem> {
    public void updatePriceByItemType(long itemType, BigDecimal newPrice);
    public void deleteByItemType(long itemTypeId);
    public List<MenuItem> getMenuItemsByType(long itemTypeId);
}
