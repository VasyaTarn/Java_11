package com.example.Java_11.DAO.WorkScheduleDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.WorkSchedule;

import java.time.LocalDate;
import java.util.List;

public interface WorkScheduleDao extends CRUDInterface<WorkSchedule> {
    public List<WorkSchedule> findScheduleForStaff(Long staffId, LocalDate startDate);
    public List<WorkSchedule> findScheduleForAllBaristas(LocalDate startDate);
    public List<WorkSchedule> findScheduleForAllStaff(LocalDate startDate);
}
