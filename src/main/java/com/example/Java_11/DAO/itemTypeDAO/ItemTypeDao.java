package com.example.Java_11.DAO.itemTypeDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.ItemType;

public interface ItemTypeDao extends CRUDInterface<ItemType> {
    public void addItemTypeByName(String name);
}
