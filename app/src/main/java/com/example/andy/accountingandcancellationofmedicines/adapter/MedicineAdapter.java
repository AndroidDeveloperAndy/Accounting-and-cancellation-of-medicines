package com.example.andy.accountingandcancellationofmedicines.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.database.MedicineTable;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 29.11.16.
 */

public class MedicineAdapter extends BaseAdapter {

    List<MedicineEntity> entityMedicine;
    LayoutInflater lInflater;
    Toast toast;

    private Activity activity;

    public MedicineAdapter(Activity activity, List<MedicineEntity> entityMedicine) {

        this.activity = activity;
        this.entityMedicine = entityMedicine;
        lInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    toast= Toast.makeText(activity,
                            MedicineTable.ColumnMedicineTable.NameMedicine +"помещено в корзину.", Toast.LENGTH_LONG);
                    toast.show();
            }
            else{
                    toast= Toast.makeText(activity,
                            MedicineTable.ColumnMedicineTable.NameMedicine +"убран из корзины.", Toast.LENGTH_LONG);
                    toast.show();
                }
        }
    };

    @Override
    public int getCount() {
        return ((null != entityMedicine) ? entityMedicine.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MedicineEntity getItem(int position) {
        return ((null != entityMedicine) ? entityMedicine.get(position) : null);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View view = convertView;

        if (null == view) {
            view = lInflater.inflate(R.layout.medicine_item_list, parent, false);
        }

        MedicineEntity data = entityMedicine.get(position);

        if (null != data) {

            CheckBox cbMedicine = (CheckBox) view.findViewById(R.id.cbMedicine);
            cbMedicine.setOnCheckedChangeListener(myCheckChangeList);
            cbMedicine.setTag(position);
            cbMedicine.setChecked(true);

            TextView nameMedicine = (TextView) view.findViewById(R.id.txNameMedicine);
            nameMedicine.setText(data.getNameMedicine());

            TextView txAmount = (TextView) view.findViewById(R.id.txAmount);
            txAmount.setText( String.valueOf( data.getAmount() ) );

            TextView txShelfLife = (TextView) view.findViewById(R.id.txShelfLife);
            txShelfLife.setText(data.getShelfLife());

        }

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
