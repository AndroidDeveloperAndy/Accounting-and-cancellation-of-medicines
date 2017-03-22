package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;

import java.io.Serializable;
import java.util.List;

public interface CityDao extends Serializable {
    List<CityEntity> queryCitysName() throws Exception;
    List<CityEntity> queryCitys(int idCountry) throws Exception;
}
