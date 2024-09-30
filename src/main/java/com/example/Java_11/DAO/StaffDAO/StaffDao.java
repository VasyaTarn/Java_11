package com.example.Java_11.DAO.StaffDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.Staff;

import java.util.List;

public interface StaffDao extends CRUDInterface<Staff> {
    public void updateEmailByConfectioner(String email);
    public void updatePhoneByBarista(String phone);
    public void deleteStaffByWaiter(long staffId);
    public void deleteStaffByBarista(long staffId);
    public List<Staff> getStuffByPosition(long positionId);
}
