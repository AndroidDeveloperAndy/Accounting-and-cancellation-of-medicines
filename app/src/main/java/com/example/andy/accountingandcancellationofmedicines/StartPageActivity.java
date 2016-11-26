package com.example.andy.accountingandcancellationofmedicines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class StartPageActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner district;
    EditText searchMedicine;
    ListView listMedicine;
    Button btAdd,btOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        district = (Spinner) findViewById(R.id.spinnerDistrict);

        searchMedicine = (EditText) findViewById(R.id.editTextSearchMedicine);

        listMedicine = (ListView) findViewById(R.id.listViewMedicine);

        btAdd = (Button) findViewById(R.id.buttonAdd);
        btOut = (Button) findViewById(R.id.buttonOut);

        btAdd.setOnClickListener(this);
        btOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.buttonAdd:
                intent = new Intent(this, AddMedicineActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonOut:
                intent = new Intent(this,OutAllMedicineActivity.class);
                startActivity(intent);
                break;
        }
    }
}

