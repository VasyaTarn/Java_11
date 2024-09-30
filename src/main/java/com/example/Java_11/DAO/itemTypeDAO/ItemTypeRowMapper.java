package com.example.Java_11.DAO.itemTypeDAO;

import com.example.Java_11.model.Customer;
import com.example.Java_11.model.ItemType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ItemTypeRowMapper implements RowMapper<ItemType> {
    public ItemType mapRow(ResultSet result, int rowNum) throws SQLException {
        ItemType itemType = new ItemType();
        itemType.setId(result.getLong("id"));
        itemType.setName(result.getString("name"));
        return itemType;
    }
}
