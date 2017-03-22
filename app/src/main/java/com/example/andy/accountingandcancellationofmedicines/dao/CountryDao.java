package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;

import java.io.Serializable;
import java.util.List;

public interface CountryDao extends Serializable {
    List<CountryEntity> queryCountryName() throws Exception;
}
