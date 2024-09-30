package com.example.Java_11.DAO.PositionDAO;

import com.example.Java_11.model.OrderItem;
import com.example.Java_11.model.Position;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class PositionRowMapper implements RowMapper<Position> {
    public Position mapRow(ResultSet result, int rowNum) throws SQLException {
        Position position = new Position();
        position.setId(result.getLong("id"));
        position.setName(result.getString("name"));

        return position;
    }
}
