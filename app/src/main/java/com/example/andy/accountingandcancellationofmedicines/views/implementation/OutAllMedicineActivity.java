package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.OutAllMedicineImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

@EActivity(R.layout.activity_out_all_medicine)
public class OutAllMedicineActivity extends AppCompatActivity implements OutAllMedicineImpl{

    private MedicineAdapter mAdapter;
    private List<WrapperMedicineAdapter> mWrapperMedicineAdapters = new ArrayList<>();

    @ViewById(R.id.listAllMedicine) RecyclerView mTaskRecyclerView;
    @ViewById(R.id.DeleteButton) Button mDeleteMedicine;

    @AfterViews
    public void initOAMA() {
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDeleteMedicine.setOnClickListener(v -> deleteItem());
    }

    @Override
    public void deleteItem(){
        try {
            mWrapperMedicineAdapters.stream().filter(WrapperMedicineAdapter::isChecked).forEach(o ->
            new MedicineDaoImpl().deleteMedicine(o.getEntity().getId()));
            updateUI();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUI() {
        try {
            List<MedicineEntity> entities = new MedicineDaoImpl().queryAllMedicine();
            mWrapperMedicineAdapters.clear();
            mWrapperMedicineAdapters.addAll(entities.stream().map(WrapperMedicineAdapter::new).collect(Collectors.toList()));
            if (mAdapter == null) {
                mAdapter = new MedicineAdapter(mWrapperMedicineAdapters);
                mTaskRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setTaskEntities(mWrapperMedicineAdapters);
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    class MedicineHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cbMedicine) CheckBox cbMedicine;
        @BindView(R.id.txNameMedicine) TextView nameMedicine;
        @BindView(R.id.txAmount) TextView txAmount;
        @BindView(R.id.txShelfLife) TextView txShelfLife;

        private WrapperMedicineAdapter data;

        MedicineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            cbMedicine.setOnCheckedChangeListener((buttonView, isChecked) -> data.setChecked(isChecked));
            nameMedicine.setOnClickListener(view -> startActivity(AddMedicineActivity.newInstanceUpdate(OutAllMedicineActivity.this, data.getEntity())));
        }

        void bindWrapperMedicine(WrapperMedicineAdapter entity) {
            data = entity;
            nameMedicine.setText(data.getEntity().getNameMedicine());
            txAmount.setText( String.valueOf( data.getEntity().getAmount() ) );
            txShelfLife.setText(data.getEntity().getShelfLife());
            cbMedicine.setChecked(data.isChecked());
        }
    }

    private class MedicineAdapter extends RecyclerView.Adapter<OutAllMedicineActivity.MedicineHolder> {

        private List<WrapperMedicineAdapter> wrapperMedicineAdapters;

        MedicineAdapter(List<WrapperMedicineAdapter> wrapperMedicineAdapters) {
            this.wrapperMedicineAdapters = wrapperMedicineAdapters;
        }

        @Override
        public OutAllMedicineActivity.MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OutAllMedicineActivity.MedicineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item_list, parent, false));
        }

        @Override
        public void onBindViewHolder(OutAllMedicineActivity.MedicineHolder holder, int position) {
            holder.bindWrapperMedicine(wrapperMedicineAdapters.get(position));
        }

        @Override
        public int getItemCount() {
            return wrapperMedicineAdapters.size();
        }

        void setTaskEntities(List<WrapperMedicineAdapter> wrapperMedicineAdapters) {
            this.wrapperMedicineAdapters = wrapperMedicineAdapters;
        }
    }
}
