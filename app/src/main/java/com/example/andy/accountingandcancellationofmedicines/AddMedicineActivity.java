package com.example.andy.accountingandcancellationofmedicines;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.andy.accountingandcancellationofmedicines.adapter.ShopAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.ShopDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity {

    ArrayList<ShopEntity> shopEntityArrayList;
    ShopAdapter adapterListView;
    ListView lvMain;

    Spinner spCity,spCountry;
    List<CountryEntity> ListCountry;
    List<CityEntity> ListCity;
    Spinner measure;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

            spCity = (Spinner) findViewById(R.id.spinnerCityAdd);
            spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter<String> adCountry = null;
            final List<String> lsCountry = new ArrayList<>();

            ListCountry = new CountryDaoImpl().queryCountryName();
            for (CountryEntity o: ListCountry)
                lsCountry.add(o.getName());
            adCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lsCountry);

            adCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCountry = (Spinner) findViewById(R.id.SpinnerCountryAdd);
            spCountry.setAdapter(adCountry);
            spCountry.setSelection(0);
            spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    try {
                        ArrayAdapter<String> adCity;
                        List<String> lsCity = new ArrayList<>();

                        ListCity = new CityDaoImpl().queryCitys(ListCountry.get(position).getIdCountry());
                        for (CityEntity o : ListCity)
                            lsCity.add(o.getName());
                        adCity = new ArrayAdapter<>(AddMedicineActivity.this, android.R.layout.simple_spinner_item, lsCity);

                        adCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spCity.setAdapter(adCity);

                        spCity.setSelection(0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            shopEntityArrayList = new ShopDaoImpl().queryAllShop();

            long date = new Date().getTime();

            measure = (Spinner) findViewById(R.id.MeasurAmount);

            adapterListView = new ShopAdapter(this, shopEntityArrayList);
            lvMain = (ListView) findViewById(R.id.listShops);
            lvMain.setAdapter(adapterListView);

            add = (Button)findViewById(R.id.add_medicine_pageAdd);
            add.setBackgroundColor(Color.rgb(98,99,155));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
