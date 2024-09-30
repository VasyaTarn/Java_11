package com.example.Java_11.DAO.WorkScheduleDAO;

import com.example.Java_11.model.Staff;
import com.example.Java_11.model.WorkSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkScheduleDaoImpl implements WorkScheduleDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<WorkSchedule> workScheduleRowMapper;

    @Override
    public void save(WorkSchedule workSchedule) {
        String sql = "INSERT INTO public.WorkSchedule (staff_id, work_date, start_time, end_time) VALUES (?, ?, ?, ?)";

        try
        {
            jdbcTemplate.update(sql,
                    workSchedule.getStaffId(),
                    Date.valueOf(workSchedule.getWorkDate()),
                    Time.valueOf(workSchedule.getStartTime()),
                    Time.valueOf(workSchedule.getEndTime()));
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<WorkSchedule> workSchedules) {
        String sql = "INSERT INTO public.WorkSchedule (staff_id, work_date, start_time, end_time) VALUES (?, ?, ?, ?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    WorkSchedule workSchedule = workSchedules.get(i);
                    ps.setLong(1, workSchedule.getStaffId());
                    ps.setDate(2, Date.valueOf(workSchedule.getWorkDate()));
                    ps.setTime(3, Time.valueOf(workSchedule.getStartTime()));
                    ps.setTime(4, Time.valueOf(workSchedule.getEndTime()));
                }

                @Override
                public int getBatchSize() {
                    return workSchedules.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(WorkSchedule workSchedule) {
        String sql = "UPDATE public.WorkSchedule SET staff_id = ?, work_date = ?, start_time = ?, end_time = ? WHERE schedule_id = ?";

        try
        {
            jdbcTemplate.update(sql,
                    workSchedule.getStaffId(),
                    Date.valueOf(workSchedule.getWorkDate()),
                    Time.valueOf(workSchedule.getStartTime()),
                    Time.valueOf(workSchedule.getEndTime()),
                    workSchedule.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(WorkSchedule workSchedule) {
        String sql = "DELETE FROM public.WorkSchedule WHERE schedule_id = ?";

        try
        {
            jdbcTemplate.update(sql, workSchedule.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<WorkSchedule> findAll() {
        String sql = "SELECT * FROM public.WorkSchedule";

        try
        {
            return jdbcTemplate.query(sql, workScheduleRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM WorkSchedule";

        try
        {
            jdbcTemplate.update(sql);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkSchedule> findScheduleForStaff(Long staffId, LocalDate startDate) {
        String sql = "SELECT * FROM public.WorkSchedule " +
                "WHERE staff_id = ? AND work_date BETWEEN ? AND ?";

        try
        {
            return jdbcTemplate.query(sql, new Object[]{staffId, startDate, startDate.plusDays(6)}, workScheduleRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkSchedule> findScheduleForAllBaristas(LocalDate startDate) {
        String sql = "SELECT * FROM public.WorkSchedule " +
                "WHERE staff_id IN (SELECT staff_id FROM public.Staff WHERE position_id = 1) " +
                "AND work_date BETWEEN ? AND ?";

        try
        {
            return jdbcTemplate.query(sql, new Object[]{startDate, startDate.plusDays(6)}, workScheduleRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkSchedule> findScheduleForAllStaff(LocalDate startDate) {
        String sql = "SELECT * FROM public.WorkSchedule " +
                "WHERE work_date BETWEEN ? AND ?";

        try
        {
            return jdbcTemplate.query(sql, new Object[]{startDate, startDate.plusDays(6)}, workScheduleRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
