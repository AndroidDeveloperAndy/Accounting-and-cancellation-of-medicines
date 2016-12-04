package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.MeasurEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 28.11.16.
 */

public interface MeasureDao extends Serializable {

    List<MeasurEntity> queryMeasureName() throws Exception;
}
