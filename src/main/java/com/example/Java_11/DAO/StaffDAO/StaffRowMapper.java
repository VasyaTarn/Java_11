package com.example.Java_11.DAO.StaffDAO;

import com.example.Java_11.model.Position;
import com.example.Java_11.model.Staff;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class StaffRowMapper implements RowMapper<Staff> {
    public Staff mapRow(ResultSet result, int rowNum) throws SQLException {
        Staff staff = new Staff(
                result.getLong("staff_id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("patronymic"),
                result.getString("phone"),
                result.getString("email"),
                result.getLong("position_id")
        );

        return staff;
    }
}
