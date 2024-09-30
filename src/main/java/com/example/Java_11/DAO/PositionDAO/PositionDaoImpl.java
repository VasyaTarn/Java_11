package com.example.Java_11.DAO.PositionDAO;

import com.example.Java_11.model.OrderItem;
import com.example.Java_11.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PositionDaoImpl implements PositionDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<Position> positionRowMapper;

    @Override
    public void addPositionByName(String name)
    {
        String sql = "INSERT INTO public.Position (name) VALUES (?)";

        try
        {
            jdbcTemplate.update(sql, name);
        }
        catch(DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Position position) {
        String sql = "INSERT INTO public.Position (name) VALUES (?)";

        try
        {
            jdbcTemplate.update(sql, position.getName());
        }
        catch(DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Position> positions) {
        String sql = "INSERT INTO public.Position (name) VALUES (?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Position position = positions.get(i);
                    ps.setString(1, position.getName());
                }

                @Override
                public int getBatchSize() {
                    return positions.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Position position) {
        String sql = "UPDATE public.Position SET name = ? WHERE id = ?";

        try
        {
            jdbcTemplate.update(sql, position.getName(), position.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Position position) {
        String sql = "DELETE FROM public.Position WHERE id = ?";

        try
        {
            jdbcTemplate.update(sql, position.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<Position> findAll() {
        String sql = "SELECT * FROM public.Position";

        try
        {
            return jdbcTemplate.query(sql, positionRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.Position";

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
