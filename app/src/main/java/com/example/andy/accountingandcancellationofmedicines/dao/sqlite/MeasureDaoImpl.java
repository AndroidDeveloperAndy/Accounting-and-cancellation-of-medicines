package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.andy.accountingandcancellationofmedicines.dao.MeasureDao;
import com.example.andy.accountingandcancellationofmedicines.database.MeasureTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singleton;
import com.example.andy.accountingandcancellationofmedicines.entity.MeasureEntity;

import java.util.ArrayList;
import java.util.List;

public class MeasureDaoImpl implements MeasureDao {

    private final SQLiteDatabase fDb;

    public MeasureDaoImpl(){
        fDb = Singleton.getInstance();
    }

    @Override
    public List<MeasureEntity> queryMeasureName() throws Exception {
        Cursor c = fDb.query(MeasureTable.NameMeasureTable, null, null, null, null, null, null);
        ArrayList<MeasureEntity> list = getMeasureList(c);
        c.close();
        return list;
    }

    @NonNull
    private ArrayList<MeasureEntity> getMeasureList(Cursor c) {
        ArrayList<MeasureEntity> list = new ArrayList<>();
        while(c.moveToNext()){
            MeasureEntity measurEntity = new MeasureEntity();
            measurEntity.setIdMeasure(c.getInt(c.getColumnIndex(MeasureTable.ColumnMeasureTable.IdMeasure)));
            measurEntity.setName(c.getString(c.getColumnIndex(MeasureTable.ColumnMeasureTable.Name)));

            list.add(measurEntity);
        }
        return list;
    }
}
