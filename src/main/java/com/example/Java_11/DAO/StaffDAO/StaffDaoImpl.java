package com.example.Java_11.DAO.StaffDAO;

import com.example.Java_11.model.Position;
import com.example.Java_11.model.Staff;
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
public class StaffDaoImpl implements StaffDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<Staff> staffRowMapper;

    @Override
    public void updateEmailByConfectioner(String email)
    {
        String sql = "UPDATE public.Staff SET email = ? WHERE position_id = 3";

        try
        {
            jdbcTemplate.update(sql, email);
        }
        catch (DataAccessException e)
        {

        }
    }

    @Override
    public void updatePhoneByBarista(String phone)
    {
        String sql = "UPDATE public.Staff SET phone = ? WHERE position_id = 1";

        try
        {
            jdbcTemplate.update(sql, phone);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    public void deleteStaffByWaiter(long staffId)
    {
        String sql = "DELETE FROM public.Staff WHERE staff_id = ? AND position_id = 2";

        try
        {
            jdbcTemplate.update(sql, staffId);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteStaffByBarista(long staffId)
    {
        String sql = "DELETE FROM public.Staff WHERE staff_id = ? AND position_id = 1";

        try
        {
            jdbcTemplate.update(sql, staffId);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Staff> getStuffByPosition(long positionId)
    {
        String sql = "SELECT staff_id, first_name, last_name, patronymic, phone, email, position_id FROM public.Staff WHERE position_id = ?";

        try
        {
            return jdbcTemplate.query(sql, staffRowMapper, positionId);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void save(Staff staff) {
        String sql =  "INSERT INTO public.Staff (first_name, last_name, patronymic, phone, email, position_id) " + "VALUES (?, ?, ?, ?, ?, ?)";

        try
        {
            jdbcTemplate.update(sql,
                    staff.getFirstName(),
                    staff.getLastName(),
                    staff.getPatronymic(),
                    staff.getPhone(),
                    staff.getEmail(),
                    staff.getPosition());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Staff> staffList) {
        String sql = "INSERT INTO public.Staff (first_name, last_name, patronymic, phone, email, position_id) VALUES (?, ?, ?, ?, ?, ?)";

        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Staff staff = staffList.get(i);
                    ps.setString(1, staff.getFirstName());
                    ps.setString(2, staff.getLastName());
                    ps.setString(3, staff.getPatronymic());
                    ps.setString(4, staff.getPhone());
                    ps.setString(5, staff.getEmail());
                    ps.setLong(6, staff.getPosition());
                }

                @Override
                public int getBatchSize() {
                    return staffList.size();
                }
            });
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Staff staff) {
        String sql = "UPDATE public.Staff SET first_name = ?, last_name = ?, patronymic = ?, phone = ?, email = ?, position_id = ? WHERE staff_id = ?";

        try
        {
            jdbcTemplate.update(sql,
                    staff.getFirstName(),
                    staff.getLastName(),
                    staff.getPatronymic(),
                    staff.getPhone(),
                    staff.getEmail(),
                    staff.getPosition(),
                    staff.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Staff staff) {
        String sql = "DELETE FROM public.Staff WHERE staff_id = ?";

        try
        {
            jdbcTemplate.update(sql, staff.getId());
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<Staff> findAll() {
        String sql = "SELECT * FROM public.Staff";

        try
        {
            return jdbcTemplate.query(sql, staffRowMapper);
        }
        catch (DataAccessException e)
        {
            System.err.println(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.Staff";

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
