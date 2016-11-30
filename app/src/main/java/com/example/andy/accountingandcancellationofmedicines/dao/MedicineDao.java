package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andy on 28.11.16.
 */

public interface MedicineDao extends Serializable {

    public ArrayList<MedicineEntity> queryAllMedicine() throws Exception;
}
