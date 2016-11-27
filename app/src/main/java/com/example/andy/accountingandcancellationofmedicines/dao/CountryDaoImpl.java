package com.example.andy.accountingandcancellationofmedicines.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.accountingandcancellationofmedicines.database.CountryTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singl;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 27.11.16.
 */

public class CountryDaoImpl implements CountryDao{

    private final SQLiteDatabase db;
    private static final String LOG_TAG = CityDaoImpl.class.getName();

    public CountryDaoImpl(){
        db = Singl.getInstance();
    }

    @Override
    public List<CountryEntity> queryCountryName() throws Exception {
        Cursor c = db.query(CountryTable.NameCountryTable, null, null, null, null, null, null);

        ArrayList<CountryEntity> list = new ArrayList<>();

        while(c.moveToNext()){
            CountryEntity cityEntity = new CountryEntity();
            cityEntity.setIdCountry(c.getInt(c.getColumnIndex(CountryTable.ColumnCountryTable.IdCountry)));
            cityEntity.setName(c.getString(c.getColumnIndex(CountryTable.ColumnCountryTable.Name)));
            cityEntity.setCodeCountry(c.getInt(c.getColumnIndex(CountryTable.ColumnCountryTable.CodeCountry)));
            cityEntity.setCodePhone(c.getInt(c.getColumnIndex(CountryTable.ColumnCountryTable.CodePhone)));

            list.add(cityEntity);
        }
        c.close();
        return list;
    }
}
