package com.example.andy.accountingandcancellationofmedicines.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.dao.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.database.MedicineTable;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;

/**
 * Created by Andy on 29.11.16.
 */

public class AdapterListView extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<MedicineEntity> entityMedicine;
    Toast toast;

    public AdapterListView(Context context, ArrayList<MedicineEntity> entity) {
        ctx = context;
        entityMedicine = entity;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return entityMedicine.size();
    }

    @Override
    public Object getItem(int position) {
        return entityMedicine.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.medicine, parent, false);
        }
        try {
            entityMedicine = new MedicineDaoImpl().queryAllMedicine();
            ((TextView) view.findViewById(R.id.txNameMedicine)).setText(MedicineTable.ColumnMedicineTable.NameMedicine);
            ((TextView) view.findViewById(R.id.txAmount)).setText(MedicineTable.ColumnMedicineTable.Amount);
            ((TextView) view.findViewById(R.id.txShelfLife)).setText(MedicineTable.ColumnMedicineTable.ShelfLife);

            CheckBox cb = (CheckBox) view.findViewById(R.id.cbMedicine);
            cb.setOnCheckedChangeListener(myCheckChangeList);
            cb.setTag(position);
            cb.setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    MedicineEntity getMedicine(int position) {
        return ((MedicineEntity) getItem(position));
    }

    ArrayList<MedicineEntity> getBox() {
        ArrayList<MedicineEntity> box = new ArrayList<MedicineEntity>();
        for (MedicineEntity p : entityMedicine) {
            if (true)
                box.add(p);
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangeList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
                if(isChecked){
                    toast= Toast.makeText(ctx,
                            MedicineTable.ColumnMedicineTable.NameMedicine +"помещено в корзину.", Toast.LENGTH_LONG);
                    toast.show();
            }
            else{
                    toast= Toast.makeText(ctx,
                            MedicineTable.ColumnMedicineTable.NameMedicine +"убран из корзины.", Toast.LENGTH_LONG);
                    toast.show();
                }
        }
    };
}
