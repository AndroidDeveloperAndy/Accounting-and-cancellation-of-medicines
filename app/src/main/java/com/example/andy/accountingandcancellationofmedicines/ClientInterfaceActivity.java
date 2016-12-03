package com.example.andy.accountingandcancellationofmedicines;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;

import java.util.ArrayList;
import java.util.List;

public class ClientInterfaceActivity extends AppCompatActivity {

    private static final String TAG = ClientInterfaceActivity.class.getName();
    Spinner districtClient,countryClient;
    List<CountryEntity> entityListCountryClient;
    List<CityEntity> entityListCityClient;
    Button order,search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_interface);

        districtClient = (Spinner) findViewById(R.id.client_district);
        districtClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


            entityListCountryClient = new CountryDaoImpl().queryCountryName();

        for (CountryEntity o: entityListCountryClient)
            listCountry.add(o.getName());
        adapterCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCountry);

        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryClient = (Spinner) findViewById(R.id.client_city);
        countryClient.setAdapter(adapterCountry);
            countryClient.setPrompt("Страна");
        countryClient.setSelection(1);
        countryClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                try {
                    ArrayAdapter<String> adapterCity;
                    List<String> listCity = new ArrayList<>();

                    entityListCityClient = new CityDaoImpl().queryCitys(entityListCountryClient.get(position).getIdCountry());
                    for (CityEntity o : entityListCityClient)
                        listCity.add(o.getName());
                    adapterCity = new ArrayAdapter<>(ClientInterfaceActivity.this, android.R.layout.simple_spinner_item, listCity);

                    adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    districtClient.setAdapter(adapterCity);

                    districtClient.setSelection(1);

                } catch (Throwable e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error ", e);

        }
        order = (Button)findViewById(R.id.OrderButton);
        order.setBackgroundColor(Color.rgb(98,99,155));
        search = (Button)findViewById(R.id.SearchButton);
        search.setBackgroundColor(Color.rgb(98,99,155));

    }

}
