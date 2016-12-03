package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.accountingandcancellationofmedicines.dao.MedicineDao;
import com.example.andy.accountingandcancellationofmedicines.database.MedicineTable;
import com.example.andy.accountingandcancellationofmedicines.database.Singl;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;

/**
 * Created by Andy on 28.11.16.
 */

public class MedicineDaoImpl implements MedicineDao {

    private final SQLiteDatabase db;
    private static final String LOG_TAG = MedicineDaoImpl.class.getName();

    public MedicineDaoImpl(){
        db = Singl.getInstance();
    }
    @Override
    public ArrayList<MedicineEntity> queryAllMedicine() throws Exception {
        Cursor c = db.query(MedicineTable.NameMedicineTable, null, null, null, null, null, null);
        ArrayList<MedicineEntity> list = new ArrayList<>();

        while(c.moveToNext()){
            MedicineEntity medicineEntity = new MedicineEntity();
            medicineEntity.setId(c.getInt(c.getColumnIndex(MedicineTable.ColumnMedicineTable.ID)));
            medicineEntity.setNameMedicine(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.NameMedicine)));
            medicineEntity.setLotNumber(c.getInt(c.getColumnIndex(MedicineTable.ColumnMedicineTable.LotNumber)));
            medicineEntity.setNote(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.Note)));
            medicineEntity.setAmount(c.getInt(c.getColumnIndex(MedicineTable.ColumnMedicineTable.Amount)));
            medicineEntity.setArrivalDate(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.ArrivalDate)));
            medicineEntity.setDateOfManufacture(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.DateOfManufacture)));
            medicineEntity.setShelfLife(c.getString(c.getColumnIndex(MedicineTable.ColumnMedicineTable.ShelfLife)));

            list.add(medicineEntity);
        }
        c.close();
        return list;
    }
}
