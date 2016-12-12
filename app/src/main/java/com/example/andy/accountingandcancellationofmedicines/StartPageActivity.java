package com.example.andy.accountingandcancellationofmedicines;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.andy.accountingandcancellationofmedicines.adapter.MedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;
import java.util.List;

public class StartPageActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = StartPageActivity.class.getName();
    Spinner district,country;
    EditText searchMedicine;
    ListView listMedicine;
    Button btAdd,btOut,btSearch;

    MedicineAdapter adapterList;
    ArrayList<MedicineEntity> medicineEntityArrayList;
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

            ArrayAdapter<String> adapterCountry;
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

            btAdd = (Button) findViewById(R.id.buttonAdd);
            btAdd.setBackgroundColor(Color.rgb(98,99,155));

            btOut = (Button) findViewById(R.id.buttonOut);
            btOut.setBackgroundColor(Color.rgb(98,99,155));

            btSearch = (Button) findViewById(R.id.button_search_sotrud);
            btSearch.setBackgroundColor(Color.rgb(98,99,155));

            btSearch.setOnClickListener(view -> {
                if(checkInputField())
                {
                    FindAtTheBase();
                    adapterList = new MedicineAdapter(StartPageActivity.this, medicineEntityArrayList);
                    listMedicine = (ListView) findViewById(R.id.listViewMedicine);
                    listMedicine.setAdapter(adapterList);
                    adapterList.notifyDataSetChanged();
                }
                else {
                    searchMedicine.setError(getString(R.string.error_field_required));
                    searchMedicine.requestFocus();
                }
            });

            btAdd.setOnClickListener(this);
        btOut.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error ", e);
        }
    }

    private void FindAtTheBase() {
        try {
            medicineEntityArrayList = new MedicineDaoImpl().findByNameMedicine(searchMedicine.getText().toString());
            if(medicineEntityArrayList.size() == 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartPageActivity.this);
                builder.setTitle("Repeat the find.")
                        .setMessage("Nothing was found.")
                        .setIcon(R.drawable.notfound);
                builder.setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
                builder.setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkInputField() {
        return searchMedicine.getText().toString().length() > 0;
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

