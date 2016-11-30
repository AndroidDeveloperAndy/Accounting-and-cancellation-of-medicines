package com.example.andy.accountingandcancellationofmedicines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.andy.accountingandcancellationofmedicines.adapter.MedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;
import java.util.Date;


public class OutAllMedicineActivity extends AppCompatActivity {

    ListView lvMain;
    Button deleteMedicine;
    ArrayList<MedicineEntity> medicineEntityArrayList;
    MedicineAdapter adapterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_all_medicine);

            medicineEntityArrayList = new MedicineDaoImpl().queryAllMedicine();

            long date = new Date().getTime();

            for(int i = 0; i < 10; i++)
                medicineEntityArrayList.add(new MedicineEntity(i, "name " + i, i, "note - " + i, i*10, new Date(date*i*60).toString(), new Date(date*i*60*60*60).toString(), "shelf life!!!"));

            deleteMedicine = (Button) findViewById(R.id.DeleteButton);
            View.OnClickListener deleteMedicine = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    }
                };

            adapterListView = new MedicineAdapter(this, medicineEntityArrayList);
            lvMain = (ListView) findViewById(R.id.listAllMedicine);
            lvMain.setAdapter(adapterListView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
