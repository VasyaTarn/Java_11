package com.example.Java_11.DAO.MenuItemDAO;

import com.example.Java_11.model.Customer;
import com.example.Java_11.model.ItemType;
import com.example.Java_11.model.MenuItem;
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
public class MenuItemDaoImpl implements MenuItemDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<MenuItem> menuItemRowMapper;

    @Override
    public void updatePriceByItemType(long itemType, BigDecimal newPrice) {
        String sql = "UPDATE public.MenuItems SET price = ? WHERE item_type_id = ?";

        try
        {
            jdbcTemplate.update(sql, newPrice, itemType);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteByItemType(long itemTypeId) {
        String sql = "DELETE FROM public.MenuItems WHERE item_type_id = ?";

        try
        {
            jdbcTemplate.update(sql, itemTypeId);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<MenuItem> getMenuItemsByType(long itemTypeId)
    {
        String sql = "SELECT item_id, name_en, name_other, item_type_id, price FROM public.MenuItems WHERE item_type_id = ?";

        try
        {
            return jdbcTemplate.query(sql, menuItemRowMapper, itemTypeId);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void save(MenuItem menuItem) {
        String sql =  "INSERT INTO public.MenuItems (name_en, name_other, item_type_id, price) " + "VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql,
                    menuItem.getNameEn(),
                    menuItem.getNameOther(),
                    menuItem.getItemType(),
                    menuItem.getPrice());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<MenuItem> menuItems) {
        String sql = "INSERT INTO public.MenuItems (name_en, name_other, item_type_id, price) VALUES (?, ?, ?, ?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    MenuItem menuItem = menuItems.get(i);
                    ps.setString(1, menuItem.getNameEn());
                    ps.setString(2, menuItem.getNameOther());
                    ps.setLong(3, menuItem.getItemType());
                    ps.setBigDecimal(4, menuItem.getPrice());
                }

                @Override
                public int getBatchSize() {
                    return menuItems.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(MenuItem menuItem) {
        String sql = "UPDATE public.MenuItems SET name_en = ?, name_other = ?, item_type_id = ?, price = ? WHERE item_id = ?";

        try {
            jdbcTemplate.update(sql,
                    menuItem.getNameEn(),
                    menuItem.getNameOther(),
                    menuItem.getItemType(),
                    menuItem.getPrice(),
                    menuItem.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(MenuItem menuItem) {
        String sql = "DELETE FROM public.MenuItems WHERE item_id = ?";

        try
        {
            jdbcTemplate.update(sql, menuItem.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<MenuItem> findAll() {
        String sql = "SELECT * FROM public.MenuItems";

        try
        {
            return jdbcTemplate.query(sql, menuItemRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.MenuItems";

        try
        {
            jdbcTemplate.update(sql);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
