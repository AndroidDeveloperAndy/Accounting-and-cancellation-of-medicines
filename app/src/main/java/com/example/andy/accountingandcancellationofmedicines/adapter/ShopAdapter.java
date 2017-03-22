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
import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;

import java.util.List;

public class ShopAdapter extends BaseAdapter {

    private List<ShopEntity> mShopEntities;
    private LayoutInflater mInflater;
    private Activity mActivity;

    public ShopAdapter(Activity activity, List<ShopEntity> entity) {
        this.mActivity = activity;
        this.mShopEntities = entity;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ((null != mShopEntities) ? mShopEntities.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ShopEntity getItem(int position) {
        return ((null != mShopEntities) ? mShopEntities.get(position) : null);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = mInflater.inflate(R.layout.shop_item_list, parent, false);
        }
        ShopEntity data = mShopEntities.get(position);
        if (null != data) {
            CheckBox cbShop = (CheckBox) view.findViewById(R.id.cbShop);
            cbShop.setTag(position);
            cbShop.setChecked(false);
            TextView nameShop = (TextView) view.findViewById(R.id.txNameShop);
            nameShop.setText(data.getNameShop());
            TextView adress = (TextView) view.findViewById(R.id.txAdress);
            adress.setText(data.getAdressShop());
        }
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
