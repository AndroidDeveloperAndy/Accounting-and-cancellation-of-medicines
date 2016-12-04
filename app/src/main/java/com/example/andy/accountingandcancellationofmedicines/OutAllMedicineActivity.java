package com.example.andy.accountingandcancellationofmedicines;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.andy.accountingandcancellationofmedicines.adapter.MedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;
import java.util.Date;


public class OutAllMedicineActivity extends AppCompatActivity {

    ListView lvMain;
    Button deleteMedicine,editMedicine;
    ArrayList<MedicineEntity> medicineEntityArrayList;
    MedicineAdapter adapterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_all_medicine);

            medicineEntityArrayList = new MedicineDaoImpl().queryAllMedicine();

            long date = new Date().getTime();

            deleteMedicine = (Button) findViewById(R.id.DeleteButton);
            deleteMedicine.setBackgroundColor(Color.rgb(98,99,155));

            View.OnClickListener deleteMedicine = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    }
                };

            adapterListView = new MedicineAdapter(this, medicineEntityArrayList);
            lvMain = (ListView) findViewById(R.id.listAllMedicine);
            lvMain.setAdapter(adapterListView);

            editMedicine = (Button) findViewById(R.id.buttonEditMedicine);
            editMedicine.setBackgroundColor(Color.rgb(98,99,155));
            editMedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
