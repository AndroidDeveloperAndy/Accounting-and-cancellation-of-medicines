package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.adapter.MedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;
import com.example.andy.accountingandcancellationofmedicines.settings.Preferences;
import com.example.andy.accountingandcancellationofmedicines.utils.DialogFactory;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.ClientActivityImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.client_page)
public class ClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ClientActivityImpl {

    @ViewById(R.id.client_district)     Spinner mDistrictClient;
    @ViewById(R.id.client_city)         Spinner mCountryClient;
    @ViewById(R.id.OrderButton)         Button mOrder;
    @ViewById(R.id.SearchButton)        Button mSearch;
    @ViewById(R.id.find_client_medicine)EditText mTxFindText;
    @ViewById(R.id.listMedicineClient)  ListView mListMedicine;
    @ViewById(R.id.drawer_layout)       DrawerLayout mDrawer;
    @ViewById(R.id.toolbar)             Toolbar mToolbar;
    @ViewById(R.id.nav_view)            NavigationView mNavigationView;

    private List<CityEntity> mEntityListCityClient;
    private List<CountryEntity> mEntityListCountry;
    final String SAVED_TEXT = "saved_text";

    @AfterViews
    public void initCA() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        createDataForSpinner();
        mOrder.setOnClickListener(view -> saveText());
        mSearch.setOnClickListener(view -> findAtListMedicine());
    }

    @Override
    public void findAtListMedicine(){
        try {
        if(checkInputField())
        {
            ArrayList<MedicineEntity> mMedicineEntityArrayList = new MedicineDaoImpl().findByNameMedicine(mTxFindText.getText().toString());
            if(mMedicineEntityArrayList.size() == 0)
            {
                DialogFactory.dialogSearch(this);
            }
            MedicineAdapter mAdapterList = new MedicineAdapter(ClientActivity.this, mMedicineEntityArrayList);
            mListMedicine.setAdapter(mAdapterList);
            mAdapterList.notifyDataSetChanged();
        }
        else {
            mTxFindText.setError(getString(R.string.error_field_required));
            mTxFindText.requestFocus();
        }
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    @Override
    public void createDataForSpinner(){
        try {
        mDistrictClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        mCountryClient.setAdapter(adapterCountry);
        mCountryClient.setPrompt("Страна");
        mCountryClient.setSelection(1);
        mCountryClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                try {
                    List<String> listCity = new ArrayList<>();
                    mEntityListCityClient = new CityDaoImpl().queryCitys(mEntityListCountry.get(position).getIdCountry());
                    for (CityEntity o : mEntityListCityClient)
                        listCity.add(o.getName());
                    ArrayAdapter<String> adapterCity = new ArrayAdapter<>(ClientActivity.this, android.R.layout.simple_spinner_item, listCity);
                    adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mDistrictClient.setAdapter(adapterCity);
                    mDistrictClient.setSelection(1);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    @Override
    public void saveText() {
        SharedPreferences mPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(SAVED_TEXT, mListMedicine.getCheckedItemPositions().toString());
        ed.apply();
        Toast.makeText(this, "The medicine is placed in the cart.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean checkInputField() {
        return mTxFindText.getText().toString().length() > 0;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(this, Preferences.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showError(){
        DialogFactory.createGenericErrorDialog(this,"Sorry,an error occurred.").show();
    }
}
