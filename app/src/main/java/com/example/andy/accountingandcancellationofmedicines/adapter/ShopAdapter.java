package com.example.andy.accountingandcancellationofmedicines.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;

import java.util.List;

/**
 * Created by Andy on 30.11.16.
 */

public class ShopAdapter extends BaseAdapter {

    List<ShopEntity> entityShop;
    LayoutInflater lInflater;

    private Activity activity;

    public ShopAdapter(Activity activity, List<ShopEntity> entity) {

        this.activity = activity;
        this.entityShop = entity;
        lInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    CompoundButton.OnCheckedChangeListener myCheckChangeList = (buttonView, isChecked) -> {
        if(isChecked){

        }
        else{

        }
    };

    @Override
    public int getCount() {
        return ((null != entityShop) ? entityShop.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ShopEntity getItem(int position) {
        return ((null != entityShop) ? entityShop.get(position) : null);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View view = convertView;

        if (null == view) {
            view = lInflater.inflate(R.layout.shop_item_list, parent, false);
        }

        ShopEntity data = entityShop.get(position);

        if (null != data) {

            CheckBox cbShop = (CheckBox) view.findViewById(R.id.cbShop);
            cbShop.setOnCheckedChangeListener(myCheckChangeList);
            cbShop.setTag(position);
            cbShop.setChecked(false);

            TextView nameShop = (TextView) view.findViewById(R.id.txNameShop);
            nameShop.setText(data.getNameShop());

            TextView Adress = (TextView) view.findViewById(R.id.txAdress);
            Adress.setText(data.getAdressShop());

        }

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
