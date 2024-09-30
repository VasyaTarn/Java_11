package com.example.Java_11.DAO.PositionDAO;

import com.example.Java_11.DAO.CRUDInterface;
import com.example.Java_11.model.Position;

public interface PositionDao extends CRUDInterface<Position> {
    public void addPositionByName(String name);
}
