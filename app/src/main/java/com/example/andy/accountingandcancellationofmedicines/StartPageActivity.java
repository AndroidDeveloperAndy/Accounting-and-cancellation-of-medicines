package com.example.andy.accountingandcancellationofmedicines;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;

import java.util.ArrayList;
import java.util.List;

public class StartPageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = StartPageActivity.class.getName();
    Spinner district,country;
    EditText searchMedicine;
    ListView listMedicine;
    Button btAdd,btOut;


    List<CountryEntity> entityListCountry;
    List<CityEntity> entityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        district = (Spinner) findViewById(R.id.spinnerDistrict);
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view,
                                                                          int position, long id) {

                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> adapterView) {

                                               }
                                           });

            ArrayAdapter<String> adapterCountry = null;
            final List<String> listCountry = new ArrayList<>();

            entityListCountry = new CountryDaoImpl().queryCountryName();
            for (CountryEntity o: entityListCountry)
                listCountry.add(o.getName());
            adapterCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCountry);

            adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            country = (Spinner) findViewById(R.id.spinnerCountry);
            country.setAdapter(adapterCountry);
            country.setSelection(0);
            country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    try {
                        ArrayAdapter<String> adapterCity;
                        List<String> listCity = new ArrayList<>();

                        entityList = new CityDaoImpl().queryCitys(entityListCountry.get(position).getIdCountry());
                        for (CityEntity o : entityList)
                            listCity.add(o.getName());
                        adapterCity = new ArrayAdapter<>(StartPageActivity.this, android.R.layout.simple_spinner_item, listCity);

                        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        district.setAdapter(adapterCity);

                        district.setSelection(0);

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        searchMedicine = (EditText) findViewById(R.id.editTextSearchMedicine);

        listMedicine = (ListView) findViewById(R.id.listViewMedicine);

            btAdd = (Button) findViewById(R.id.buttonAdd);
            btAdd.setBackgroundColor(Color.rgb(98,99,155));

            btOut = (Button) findViewById(R.id.buttonOut);
            btOut.setBackgroundColor(Color.rgb(98,99,155));


            btAdd.setOnClickListener(this);
        btOut.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error ", e);
        }
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

