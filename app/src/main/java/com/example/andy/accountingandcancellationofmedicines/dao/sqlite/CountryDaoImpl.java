package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.andy.accountingandcancellationofmedicines.dao.CountryDao;
import com.example.andy.accountingandcancellationofmedicines.database.CountryTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singl;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 27.11.16.
 */

public class CountryDaoImpl implements CountryDao {

    private final SQLiteDatabase db;

    public CountryDaoImpl(){
        db = Singl.getInstance();
    }

    @Override
    public List<CountryEntity> queryCountryName() throws Exception {
        Cursor c = db.query(CountryTable.NameCountryTable, null, null, null, null, null, null);
        ArrayList<CountryEntity> list = getCountryList(c);
        c.close();
        return list;
    }

    @NonNull
    private ArrayList<CountryEntity> getCountryList(Cursor c) {
        ArrayList<CountryEntity> list = new ArrayList<>();

        while(c.moveToNext()){
            CountryEntity cityEntity = new CountryEntity();
            cityEntity.setIdCountry(c.getInt(c.getColumnIndex(CountryTable.ColumnCountryTable.IdCountry)));
            cityEntity.setName(c.getString(c.getColumnIndex(CountryTable.ColumnCountryTable.Name)));
            cityEntity.setCodeCountry(c.getInt(c.getColumnIndex(CountryTable.ColumnCountryTable.CodeCountry)));
            cityEntity.setCodePhone(c.getInt(c.getColumnIndex(CountryTable.ColumnCountryTable.CodePhone)));

            list.add(cityEntity);
        }
        return list;
    }
}
