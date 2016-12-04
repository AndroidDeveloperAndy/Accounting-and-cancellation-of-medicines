package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.LotEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 28.11.16.
 */

public interface LotDao extends Serializable{
    List<LotEntity> queryLot() throws Exception;
}
