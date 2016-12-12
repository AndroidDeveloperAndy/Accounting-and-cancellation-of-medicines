package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.andy.accountingandcancellationofmedicines.dao.CityDao;
import com.example.andy.accountingandcancellationofmedicines.database.CityTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 27.11.16.
 */

public class CityDaoImpl implements CityDao {

    private final SQLiteDatabase db;

    public CityDaoImpl(){
        db = Singl.getInstance();
    }

    @Override
    public List<CityEntity> queryCitysName() throws Exception {
        Cursor c = db.query(CityTable.NameCityTable, null, null, null, null, null, null);
        ArrayList<CityEntity> list = getCityList(c);
        c.close();
        return list;
    }

    @Override
    public List<CityEntity> queryCitys(int idCountry) throws Exception {
        Cursor c = db.query(CityTable.NameCityTable, null, CityTable.ColumnCityTable.Country + " = ?", new String[]{String.valueOf(idCountry)}, null, null, null);
        ArrayList<CityEntity> list = getCityList(c);
        c.close();
        return list;
    }

    @NonNull
    private ArrayList<CityEntity> getCityList(Cursor c) {
        ArrayList<CityEntity> list = new ArrayList<>();

        while(c.moveToNext()){
            CityEntity cityEntity = new CityEntity();
            cityEntity.setIdCity(c.getInt(c.getColumnIndex(CityTable.ColumnCityTable.IdCity)));
            cityEntity.setName(c.getString(c.getColumnIndex(CityTable.ColumnCityTable.Name)));
            cityEntity.setPostCode(c.getInt(c.getColumnIndex(CityTable.ColumnCityTable.PostCode)));
            cityEntity.setIdCountry(c.getInt(c.getColumnIndex(CityTable.ColumnCityTable.Country)));

            list.add(cityEntity);
        }
        return list;
    }
}
