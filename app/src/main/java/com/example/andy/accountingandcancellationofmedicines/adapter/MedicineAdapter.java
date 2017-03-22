package com.example.andy.accountingandcancellationofmedicines.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.List;

public class MedicineAdapter extends BaseAdapter {

    private List<MedicineEntity> mMedicineEntities;
    private LayoutInflater mInflater;
    private Activity mActivity;

    public MedicineAdapter(Activity activity, List<MedicineEntity> entityMedicine) {
        this.mActivity = activity;
        this.mMedicineEntities = entityMedicine;
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ((null != mMedicineEntities) ? mMedicineEntities.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MedicineEntity getItem(int position) {
        return ((null != mMedicineEntities) ? mMedicineEntities.get(position) : null);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = mInflater.inflate(R.layout.medicine_item_list, parent, false);
        }
        MedicineEntity data = mMedicineEntities.get(position);
        if (null != data) {
            CheckBox cbMedicine = (CheckBox) view.findViewById(R.id.cbMedicine);
            cbMedicine.setTag(position);
            cbMedicine.setChecked(false);
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
