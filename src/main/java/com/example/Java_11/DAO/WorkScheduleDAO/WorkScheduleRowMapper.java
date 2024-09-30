package com.example.Java_11.DAO.WorkScheduleDAO;

import com.example.Java_11.model.Staff;
import com.example.Java_11.model.WorkSchedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class WorkScheduleRowMapper implements RowMapper<WorkSchedule> {
    public WorkSchedule mapRow(ResultSet result, int rowNum) throws SQLException {
        WorkSchedule workSchedule = new WorkSchedule(
                result.getLong("schedule_id"),
                result.getLong("staff_id"),
                result.getDate("work_date").toLocalDate(),
                result.getTime("start_time").toLocalTime(),
                result.getTime("end_time").toLocalTime()
        );

        return workSchedule;
    }
}
