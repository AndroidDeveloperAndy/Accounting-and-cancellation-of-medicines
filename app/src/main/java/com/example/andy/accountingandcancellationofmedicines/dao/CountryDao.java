package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 27.11.16.
 */

public interface CountryDao extends Serializable {

    public List<CountryEntity> queryCountryName() throws Exception;

}
