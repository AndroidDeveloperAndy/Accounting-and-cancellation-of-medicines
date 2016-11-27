package com.example.andy.accountingandcancellationofmedicines.dao;

import android.content.ContentValues;

import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 27.11.16.
 */

public interface CityDao extends Serializable {

    List<CityEntity> queryCitysName() throws Exception;
    List<CityEntity> queryCitys(int idCountry) throws Exception;

}
