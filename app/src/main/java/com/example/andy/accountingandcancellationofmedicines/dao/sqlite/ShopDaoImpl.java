package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.accountingandcancellationofmedicines.dao.ShopDao;
import com.example.andy.accountingandcancellationofmedicines.database.ShopTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singl;
import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;

import java.util.ArrayList;

/**
 * Created by Andy on 28.11.16.
 */

public class ShopDaoImpl implements ShopDao {

    private final SQLiteDatabase db;

    public ShopDaoImpl(){
        db = Singl.getInstance();
    }

    @Override
    public ArrayList<ShopEntity> queryAllShop() throws Exception {
        Cursor c = db.query(ShopTable.NameShopTable, null, null, null, null, null, null);
        ArrayList<ShopEntity> list = new ArrayList<>();

        while(c.moveToNext()){
            ShopEntity shopEntity = new ShopEntity();
            shopEntity.setIdShop(c.getInt(c.getColumnIndex(ShopTable.ColumnShopTable.IdShop)));
            shopEntity.setNameShop(c.getString(c.getColumnIndex(ShopTable.ColumnShopTable.NameShop)));
            shopEntity.setAdressShop(c.getString(c.getColumnIndex(ShopTable.ColumnShopTable.AdressShop)));
            shopEntity.setNameMedicineAtShop(c.getString(c.getColumnIndex(ShopTable.ColumnShopTable.NameMedicineAtShop)));
            shopEntity.setPriceAtShop(c.getInt(c.getColumnIndex(ShopTable.ColumnShopTable.PriceAtShop)));

            list.add(shopEntity);
        }
        c.close();
        return list;
    }
}

