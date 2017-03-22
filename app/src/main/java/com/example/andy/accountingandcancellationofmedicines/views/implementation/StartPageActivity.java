package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.adapter.MedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.StartPageImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_start_page)
public class StartPageActivity extends AppCompatActivity implements View.OnClickListener,StartPageImpl {

    @ViewById(R.id.spinnerDistrict)         Spinner mDistrict;
    @ViewById(R.id.spinnerCountry)          Spinner mCountry;
    @ViewById(R.id.editTextSearchMedicine)  EditText mSearchMedicine;
    @ViewById(R.id.listViewMedicine)        ListView mListMedicine;
    @ViewById(R.id.buttonAdd)               Button mBtnAdd;
    @ViewById(R.id.buttonOut)               Button mBtnOut;
    @ViewById(R.id.button_search_sotrud)    Button mBtnSearch;

    private List<CityEntity> mEntityList;
    private List<CountryEntity> mEntityListCountry;

    @AfterViews
    public void initSPA(){
        setSpinner();
        mBtnSearch.setOnClickListener(view -> searchMedicine());
        mBtnAdd.setOnClickListener(this);
        mBtnOut.setOnClickListener(this);
    }

    @Override
    public void searchMedicine(){
        try {
        if(checkInputField())
        {
            ArrayList<MedicineEntity> medicineEntityArrayList = new MedicineDaoImpl().findByNameMedicine(mSearchMedicine.getText().toString());
            if(medicineEntityArrayList.size() == 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartPageActivity.this);
                builder.setTitle("Repeat the find.")
                        .setMessage("Nothing was found.")
                        .setIcon(R.drawable.notfound)
                        .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel())
                        .setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();
            }
            MedicineAdapter adapterList = new MedicineAdapter(StartPageActivity.this, medicineEntityArrayList);
            mListMedicine.setAdapter(adapterList);
            adapterList.notifyDataSetChanged();
        }
        else {
            mSearchMedicine.setError(getString(R.string.error_field_required));
            mSearchMedicine.requestFocus();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSpinner(){
        try {
        mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        final List<String> listCountry = new ArrayList<>();
        mEntityListCountry = new CountryDaoImpl().queryCountryName();
        for (CountryEntity o: mEntityListCountry)
                listCountry.add(o.getName());
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCountry);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(adapterCountry);
        mCountry.setSelection(0);
        mCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                try {
                    List<String> listCity = new ArrayList<>();
                    mEntityList = new CityDaoImpl().queryCitys(mEntityListCountry.get(position).getIdCountry());
                    for (CityEntity o : mEntityList)
                        listCity.add(o.getName());
                    ArrayAdapter<String> adapterCity = new ArrayAdapter<>(StartPageActivity.this, android.R.layout.simple_spinner_item, listCity);
                    adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mDistrict.setAdapter(adapterCity);
                    mDistrict.setSelection(0);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkInputField() {
        return mSearchMedicine.getText().toString().length() > 0;
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

