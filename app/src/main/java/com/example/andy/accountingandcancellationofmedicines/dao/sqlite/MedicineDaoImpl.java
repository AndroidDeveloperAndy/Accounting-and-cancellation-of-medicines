package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.andy.accountingandcancellationofmedicines.dao.MedicineDao;
import com.example.andy.accountingandcancellationofmedicines.database.MedicineTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singleton;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;

public class MedicineDaoImpl implements MedicineDao {

    private final SQLiteDatabase fDb;

    public MedicineDaoImpl(){
        fDb = Singleton.getInstance();
    }

    @Override
    public ArrayList<MedicineEntity> queryAllMedicine() throws Exception {
        Cursor c = fDb.query(MedicineTable.NameMedicineTable, null, null, null, null, null, null);
        ArrayList<MedicineEntity> list = new ArrayList<>();
        while(c.moveToNext()){
            MedicineEntity medicineEntity = getMedicineEntity(c);
            list.add(medicineEntity);
        }
        c.close();
        return list;
    }

    @Override
    public ArrayList<MedicineEntity> findByNameMedicine(String name) throws Exception {
        Cursor c = fDb.query(MedicineTable.NameMedicineTable, null, MedicineTable.ColumnMedicineTable.NameMedicine + " LIKE '%"+ name +"%'", null, null, null, null);
        ArrayList<MedicineEntity> list = new ArrayList<>();
        if(c.moveToFirst()){
            do {
                MedicineEntity medicineEntity = getMedicineEntity(c);
                list.add(medicineEntity);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    @NonNull
    private MedicineEntity getMedicineEntity(Cursor c) {
        MedicineEntity medicineEntity = new MedicineEntity();
        medicineEntity.setId(c.getInt(c.getColumnIndex(MedicineTable.ColumnMedicineTable.ID)));
        medicineEntity.setNameMedicine(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.NameMedicine)));
        medicineEntity.setLotNumber(c.getInt(c.getColumnIndex(MedicineTable.ColumnMedicineTable.LotNumber)));
        medicineEntity.setNote(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.Note)));
        medicineEntity.setAmount(c.getInt(c.getColumnIndex(MedicineTable.ColumnMedicineTable.Amount)));
        medicineEntity.setArrivalDate(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.ArrivalDate)));
        medicineEntity.setDateOfManufacture(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.DateOfManufacture)));
        medicineEntity.setShelfLife(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.ShelfLife)));
        medicineEntity.setIdMeasure(c.getInt(c.getColumnIndex(MedicineTable.ColumnMedicineTable.idMeasure)));
        return medicineEntity;
    }

    @Override
    public long addMedicine(MedicineEntity entity) throws Exception {
        ContentValues newValues = getContentValues(entity);
        return fDb.insertOrThrow(MedicineTable.NameMedicineTable, null,newValues);
    }

    @NonNull
    private ContentValues getContentValues(MedicineEntity entity) {
        ContentValues newValues = new ContentValues();
        newValues.put(MedicineTable.ColumnMedicineTable.NameMedicine, entity.getNameMedicine());
        newValues.put(MedicineTable.ColumnMedicineTable.LotNumber, entity.getLotNumber());
        newValues.put(MedicineTable.ColumnMedicineTable.Amount, entity.getAmount());
        newValues.put(MedicineTable.ColumnMedicineTable.ArrivalDate, entity.getArrivalDate());
        newValues.put(MedicineTable.ColumnMedicineTable.DateOfManufacture, entity.getDateOfManufacture());
        newValues.put(MedicineTable.ColumnMedicineTable.Note, entity.getNote());
        newValues.put(MedicineTable.ColumnMedicineTable.ShelfLife, entity.getShelfLife());
        newValues.put(MedicineTable.ColumnMedicineTable.idMeasure,entity.getIdMeasure());
        return newValues;
    }

    @Override
    public void update(MedicineEntity entity){
        fDb.update(MedicineTable.NameMedicineTable, getContentValues(entity),
                MedicineTable.ColumnMedicineTable.ID + "= ?" ,
                new String[]{String.valueOf(entity.getId())});
    }

    @Override
    public int deleteMedicine(long id) {
        return fDb.delete(MedicineTable.NameMedicineTable, MedicineTable.ColumnMedicineTable.ID + "= ?" , new String[]{String.valueOf(id)});
    }
}
