package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.adapter.MedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;
import com.example.andy.accountingandcancellationofmedicines.utils.DialogFactory;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.StartPageImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.admin_page)
public class AdminActivity extends AppCompatActivity implements View.OnClickListener, StartPageImpl {

    @ViewById(R.id.spinnerDistrict)
    NiceSpinner mDistrict;
    @ViewById(R.id.spinnerCountry)
    NiceSpinner mCountry;
    @ViewById(R.id.editTextSearchMedicine)
    EditText mSearchMedicine;
    @ViewById(R.id.listViewMedicine)
    ListView mListMedicine;
    @ViewById(R.id.buttonAdd)
    Button mBtnAdd;
    @ViewById(R.id.buttonOut)
    Button mBtnOut;
    @ViewById(R.id.button_search_sotrud)
    Button mBtnSearch;

    private List<CityEntity> mEntityList;
    private List<CountryEntity> mEntityListCountry;

    @AfterViews
    public void initAA() {
        setSpinner();
        mBtnSearch.setOnClickListener(view -> searchMedicine());
        mBtnAdd.setOnClickListener(this);
        mBtnOut.setOnClickListener(this);
    }

    @Override
    public void searchMedicine() {
        try {
            if (checkInputField()) {
                ArrayList<MedicineEntity> medicineEntityArrayList = new MedicineDaoImpl().findByNameMedicine(mSearchMedicine.getText().toString());
                if (medicineEntityArrayList.size() == 0) {
                    DialogFactory.dialogSearch(this);
                }
                MedicineAdapter adapterList = new MedicineAdapter(AdminActivity.this, medicineEntityArrayList);
                mListMedicine.setAdapter(adapterList);
                adapterList.notifyDataSetChanged();
            } else {
                mSearchMedicine.setError(getString(R.string.error_field_required));
                mSearchMedicine.requestFocus();
            }
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    @Override
    public void setSpinner() {
        try {
            mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            final List<String> listCountry = new ArrayList<>();
            mEntityListCountry = new CountryDaoImpl().queryCountryName();
            for (CountryEntity o : mEntityListCountry)
                listCountry.add(o.getName());
            mCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listCountry));
            mCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    try {
                        List<String> listCity = new ArrayList<>();
                        mEntityList = new CityDaoImpl().queryCitys(mEntityListCountry.get(position).getIdCountry());
                        for (CityEntity o : mEntityList)
                            listCity.add(o.getName());
                        mDistrict.setAdapter(new ArrayAdapter<>(AdminActivity.this, android.R.layout.simple_spinner_item, listCity));
                    } catch (Throwable e) {
                        showError();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            mDistrict.setTextColor(Color.BLACK);
            mCountry.setTextColor(Color.BLACK);
        } catch (Exception e) {
            showError();
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

        switch (view.getId()) {
            case R.id.buttonAdd:
                intent = new Intent(this, AddMedicineActivity_.class);
                startActivity(intent);
                break;
            case R.id.buttonOut:
                intent = new Intent(this, ListMedicineActivity_.class);
                startActivity(intent);
                break;
        }
    }

    public void showError() {
        DialogFactory.createGenericErrorDialog(this, "Sorry,an error occurred.").show();
    }
}

