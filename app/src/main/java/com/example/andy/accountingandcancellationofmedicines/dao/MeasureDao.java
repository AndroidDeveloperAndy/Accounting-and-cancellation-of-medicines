package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.MeasureEntity;

import java.io.Serializable;
import java.util.List;

public interface MeasureDao extends Serializable {
    List<MeasureEntity> queryMeasureName() throws Exception;
}
