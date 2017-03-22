package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;

import java.io.Serializable;
import java.util.ArrayList;

public interface ShopDao extends Serializable{
    ArrayList<ShopEntity> queryAllShop() throws Exception;
}
