package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.andy.accountingandcancellationofmedicines.dao.LotDao;
import com.example.andy.accountingandcancellationofmedicines.database.LotTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singleton;
import com.example.andy.accountingandcancellationofmedicines.entity.LotEntity;

import java.util.ArrayList;
import java.util.List;

public class LotDaoImpl implements LotDao{

    private final SQLiteDatabase fDb;

    public LotDaoImpl() {
        fDb = Singleton.getInstance();
    }

    @Override
    public List<LotEntity> queryLot() throws Exception {
        Cursor c = fDb.query(LotTable.NameLotTable, null, null, null, null, null, null);
        ArrayList<LotEntity> list = getLotList(c);
        c.close();
        return list;
    }

    @NonNull
    private ArrayList<LotEntity> getLotList(Cursor c) {
        ArrayList<LotEntity> list = new ArrayList<>();
        while(c.moveToNext()){
            LotEntity lotEntity = new LotEntity();
            lotEntity.setIdLot(c.getInt(c.getColumnIndex(LotTable.ColumnLotTable.IdLot)));
            lotEntity.setlNumber(c.getInt(c.getColumnIndex(LotTable.ColumnLotTable.LNumber)));
            lotEntity.setAdress(c.getString(c.getColumnIndex(LotTable.ColumnLotTable.Adress)));
            lotEntity.setDepartureDate(c.getString(c.getColumnIndex(LotTable.ColumnLotTable.DepartureDate)));
            lotEntity.setNameCity(c.getString(c.getColumnIndex(LotTable.ColumnLotTable.NameCity)));
            lotEntity.setNameCountry(c.getString(c.getColumnIndex(LotTable.ColumnLotTable.NameCountry)));
            lotEntity.setPriceOne(c.getInt(c.getColumnIndex(LotTable.ColumnLotTable.PriceOne)));

            list.add(lotEntity);
        }
        return list;
    }
}
