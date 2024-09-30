package com.example.Java_11.DAO.itemTypeDAO;

import com.example.Java_11.model.Customer;
import com.example.Java_11.model.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemTypeDaoImpl implements ItemTypeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<ItemType> itemTypeRowMapper;

    @Override
    public void addItemTypeByName(String name) {
        String sql = "INSERT INTO public.ItemTypes (name) VALUES (?)";

        try
        {
            jdbcTemplate.update(sql, name);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void save(ItemType itemType) {
        String sql = "INSERT INTO public.ItemTypes (name) VALUES (?)";

        try
        {
            jdbcTemplate.update(sql, itemType.getName());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<ItemType> itemTypes) {
        String sql = "INSERT INTO public.ItemTypes (name) VALUES (?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException
                {
                    ItemType itemType = itemTypes.get(i);
                    ps.setString(1, itemType.getName());
                }

                @Override
                public int getBatchSize()
                {
                    return itemTypes.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(ItemType itemType) {
        String sql = "UPDATE public.ItemTypes SET name = ? WHERE id = ?";

        try
        {
            jdbcTemplate.update(sql, itemType.getName(), itemType.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(ItemType itemType) {
        String sql = "DELETE FROM public.ItemTypes WHERE id = ?";

        try
        {
            jdbcTemplate.update(sql, itemType.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<ItemType> findAll() {
        String sql = "SELECT * FROM public.ItemTypes";

        try
        {
            return jdbcTemplate.query(sql, itemTypeRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.ItemTypes";

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
