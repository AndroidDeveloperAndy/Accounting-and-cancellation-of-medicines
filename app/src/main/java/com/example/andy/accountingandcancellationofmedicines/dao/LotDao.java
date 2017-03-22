package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.LotEntity;

import java.io.Serializable;
import java.util.List;

public interface LotDao extends Serializable{
    List<LotEntity> queryLot() throws Exception;
}
