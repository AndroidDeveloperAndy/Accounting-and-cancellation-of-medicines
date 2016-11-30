package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andy on 28.11.16.
 */

public interface ShopDao extends Serializable{

    public ArrayList<ShopEntity> queryAllShop() throws Exception;
}
