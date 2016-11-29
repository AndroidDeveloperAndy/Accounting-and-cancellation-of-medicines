package com.example.andy.accountingandcancellationofmedicines;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class OutAllMedicineActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";

    ListView lvMain;
    Button deleteMedicine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_all_medicine);

            deleteMedicine = (Button) findViewById(R.id.DeleteButton);
            View.OnClickListener deleteMedicine = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    }
                };

        lvMain = (ListView) findViewById(R.id.listAllMedicine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
