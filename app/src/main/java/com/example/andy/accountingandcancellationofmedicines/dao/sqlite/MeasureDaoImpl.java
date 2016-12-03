package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.accountingandcancellationofmedicines.dao.MeasureDao;
import com.example.andy.accountingandcancellationofmedicines.database.MeasurTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singl;
import com.example.andy.accountingandcancellationofmedicines.entity.MeasurEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 28.11.16.
 */

public class MeasureDaoImpl implements MeasureDao {

    private final SQLiteDatabase db;

    public MeasureDaoImpl(){
        db = Singl.getInstance();
    }

    @Override
    public List<MeasurEntity> queryMeasureName() throws Exception {
        Cursor c = db.query(MeasurTable.NameMeasurTable, null, null, null, null, null, null);
        ArrayList<MeasurEntity> list = new ArrayList<>();

        while(c.moveToNext()){
            MeasurEntity measurEntity = new MeasurEntity();
            measurEntity.setIdMedicine(c.getInt(c.getColumnIndex(MeasurTable.ColumnMeasurTable.IdMedicine)));
            measurEntity.setGram(c.getInt(c.getColumnIndex(MeasurTable.ColumnMeasurTable.gram)));
            measurEntity.setKilo(c.getInt(c.getColumnIndex(MeasurTable.ColumnMeasurTable.kilo)));
            measurEntity.setLiters(c.getInt(c.getColumnIndex(MeasurTable.ColumnMeasurTable.liters)));
            measurEntity.setMliters(c.getInt(c.getColumnIndex(MeasurTable.ColumnMeasurTable.mliters)));
            measurEntity.setPieces(c.getInt(c.getColumnIndex(MeasurTable.ColumnMeasurTable.pieces)));

            list.add(measurEntity);
        }
        c.close();
        return list;
    }
}
