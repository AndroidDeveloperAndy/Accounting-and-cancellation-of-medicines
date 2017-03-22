package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.adapter.WrapperMedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;
import java.util.List;


public class OutAllMedicineActivity extends AppCompatActivity {

    private static final String TAG = OutAllMedicineActivity.class.getName();
    private Button deleteMedicine;
    private List<WrapperMedicineAdapter> wrapperMedicineAdapters;

    private RecyclerView mTaskRecyclerView;
    private MedicineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_all_medicine);

        wrapperMedicineAdapters = new ArrayList<>();

        mTaskRecyclerView = (RecyclerView) findViewById(R.id.listAllMedicine);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        deleteMedicine = (Button) findViewById(R.id.DeleteButton);
        deleteMedicine.setBackgroundColor(Color.rgb(98,99,155));

        deleteMedicine.setOnClickListener(v -> {
            try {
                for(WrapperMedicineAdapter o : wrapperMedicineAdapters) {
                    if (o.isChecked()) {
                        new MedicineDaoImpl().deleteMedicine(o.getEntity().getId());
                    }
                }

                updateUI();

            }catch (Exception e) {
                e.printStackTrace();
            }
        });

        updateUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        try {
            List<MedicineEntity> entities = new MedicineDaoImpl().queryAllMedicine();

            wrapperMedicineAdapters.clear();

            for (MedicineEntity o: entities){
                wrapperMedicineAdapters.add(new WrapperMedicineAdapter(o));
            }

            if (mAdapter == null) {

                mAdapter = new MedicineAdapter(wrapperMedicineAdapters);
                mTaskRecyclerView.setAdapter(mAdapter);

            } else {
                mAdapter.setTaskEntities(wrapperMedicineAdapters);
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error out all", e);
        }

    }

    private class MedicineHolder extends RecyclerView.ViewHolder{

        CheckBox cbMedicine;
        TextView nameMedicine;
        TextView txAmount;
        TextView txShelfLife;

        private WrapperMedicineAdapter data;

        public MedicineHolder(View itemView) {
            super(itemView);

            cbMedicine = (CheckBox) itemView.findViewById(R.id.cbMedicine);
            cbMedicine.setOnCheckedChangeListener((buttonView, isChecked) -> data.setChecked(isChecked));

            nameMedicine = (TextView) itemView.findViewById(R.id.txNameMedicine);
            nameMedicine.setOnClickListener(view -> startActivity(AddMedicineActivity.newInstanceUpdate(OutAllMedicineActivity.this, data.getEntity())));
            txAmount = (TextView) itemView.findViewById(R.id.txAmount);
            txShelfLife = (TextView) itemView.findViewById(R.id.txShelfLife);

        }

        public void bindWrapperMedicine(WrapperMedicineAdapter entity) {
            data = entity;

            nameMedicine.setText(data.getEntity().getNameMedicine());
            txAmount.setText( String.valueOf( data.getEntity().getAmount() ) );
            txShelfLife.setText(data.getEntity().getShelfLife());
            cbMedicine.setChecked(data.isChecked());
        }
    }

    private class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> {

        private List<WrapperMedicineAdapter> wrapperMedicineAdapters;

        public MedicineAdapter(List<WrapperMedicineAdapter> wrapperMedicineAdapters) {

            this.wrapperMedicineAdapters = wrapperMedicineAdapters;

        }

        @Override
        public MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.medicine_item_list, parent, false);

            return new MedicineHolder(view);
        }

        @Override
        public void onBindViewHolder(MedicineHolder holder, int position) {

            WrapperMedicineAdapter wrapperMedicineAdapter = wrapperMedicineAdapters.get(position);

            holder.bindWrapperMedicine(wrapperMedicineAdapter);
        }

        @Override
        public int getItemCount() {

            return wrapperMedicineAdapters.size();
        }

        public void setTaskEntities(List<WrapperMedicineAdapter> wrapperMedicineAdapters) {
            this.wrapperMedicineAdapters = wrapperMedicineAdapters;

        }

    }
}
