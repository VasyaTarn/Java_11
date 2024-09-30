package com.example.Java_11.DAO.MenuItemDAO;

import com.example.Java_11.model.ItemType;
import com.example.Java_11.model.MenuItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class MenuItemRowMapper implements RowMapper<MenuItem> {
    public MenuItem mapRow(ResultSet result, int rowNum) throws SQLException {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(result.getLong("item_id"));
        menuItem.setNameEn(result.getString("name_en"));
        menuItem.setNameOther(result.getString("name_other"));
        menuItem.setItemType(result.getLong("item_type_id"));
        menuItem.setPrice(result.getBigDecimal("price"));

        return menuItem;
    }
}
