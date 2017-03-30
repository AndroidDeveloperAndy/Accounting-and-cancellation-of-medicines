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
import com.example.andy.accountingandcancellationofmedicines.utils.DialogFactory;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.OutAllMedicineImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_out_all_medicine)
public class ListMedicineActivity extends AppCompatActivity implements OutAllMedicineImpl{

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
    public void deleteItem() {
        try {
            for (WrapperMedicineAdapter o : mWrapperMedicineAdapters) {
                if (o.isChecked()) {
                    new MedicineDaoImpl().deleteMedicine(o.getEntity().getId());
                }
            }
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
            for (MedicineEntity o: entities){
                mWrapperMedicineAdapters.add(new WrapperMedicineAdapter(o));
            }
            if (mAdapter == null) {
                mAdapter = new MedicineAdapter(mWrapperMedicineAdapters);
                mTaskRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setTaskEntities(mWrapperMedicineAdapters);
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void showError(){
        DialogFactory.createGenericErrorDialog(this,"Sorry,an error occurred.").show();
    }

    class MedicineHolder extends RecyclerView.ViewHolder{

        private CheckBox cbMedicine;
        private TextView nameMedicine;
        private TextView txAmount;
        private TextView txShelfLife;

        private WrapperMedicineAdapter data;

        MedicineHolder(View itemView) {
            super(itemView);
            cbMedicine = (CheckBox) itemView.findViewById(R.id.cbMedicine);
            nameMedicine = (TextView) itemView.findViewById(R.id.txNameMedicine);
            txAmount = (TextView) itemView.findViewById(R.id.txAmount);
            txShelfLife = (TextView) itemView.findViewById(R.id.txShelfLife);
            cbMedicine.setOnCheckedChangeListener((buttonView, isChecked) -> data.setChecked(isChecked));
            nameMedicine.setOnClickListener(view -> startActivity(AddMedicineActivity.newInstanceUpdate(ListMedicineActivity.this, data.getEntity())));
        }

        void bindWrapperMedicine(WrapperMedicineAdapter entity) {
            data = entity;
            nameMedicine.setText(data.getEntity().getNameMedicine());
            txAmount.setText( String.valueOf( data.getEntity().getAmount() ) );
            txShelfLife.setText(data.getEntity().getShelfLife());
            cbMedicine.setChecked(data.isChecked());
        }
    }

    private class MedicineAdapter extends RecyclerView.Adapter<ListMedicineActivity.MedicineHolder> {

        private List<WrapperMedicineAdapter> wrapperMedicineAdapters;

        MedicineAdapter(List<WrapperMedicineAdapter> wrapperMedicineAdapters) {
            this.wrapperMedicineAdapters = wrapperMedicineAdapters;
        }

        @Override
        public ListMedicineActivity.MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListMedicineActivity.MedicineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ListMedicineActivity.MedicineHolder holder, int position) {
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
