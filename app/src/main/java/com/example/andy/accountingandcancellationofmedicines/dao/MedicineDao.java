package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.io.Serializable;
import java.util.ArrayList;

public interface MedicineDao extends Serializable {
    ArrayList<MedicineEntity> queryAllMedicine() throws Exception;
    ArrayList<MedicineEntity> findByNameMedicine(String name) throws Exception;
    long addMedicine(MedicineEntity entity) throws Exception;
    void update(MedicineEntity entity);
    int deleteMedicine(long id);
}
