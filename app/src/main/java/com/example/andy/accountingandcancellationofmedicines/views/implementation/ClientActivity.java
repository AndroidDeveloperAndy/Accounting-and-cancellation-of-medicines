package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EActivity(R.layout.activity_client)
public class ClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.client_district)     Spinner mDistrictClient;
    @ViewById(R.id.client_city)         Spinner mCountryClient;
    @ViewById(R.id.OrderButton)         Button mOrder;
    @ViewById(R.id.SearchButton)        Button mSearch;
    @ViewById(R.id.find_client_medicine)EditText mTxFindText;
    @ViewById(R.id.listMedicineClient)  ListView mListMedicine;
    @ViewById(R.id.drawer_layout)       DrawerLayout mDrawer;
    @ViewById(R.id.toolbar)             Toolbar mToolbar;
    @ViewById(R.id.nav_view)             NavigationView mNavigationView;

    private List<CountryEntity> mEntityListCountryClient;
    private List<CityEntity> mEntityListCityClient;
    private MedicineAdapter mAdapterList;
    private ArrayList<MedicineEntity> mMedicineEntityArrayList;
    private SharedPreferences mPref;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mDistrictClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        final List<String> listCountry = new ArrayList<>();
        listCountry.addAll(new CountryDaoImpl().queryCountryName().stream().map(CountryEntity::getName).collect(Collectors.toList()));
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
                    ArrayAdapter<String> adapterCity;
                    List<String> listCity = new ArrayList<>();
                    mEntityListCityClient = new CityDaoImpl().queryCitys(mEntityListCountryClient.get(position).getIdCountry());
                    listCity.addAll(new CityDaoImpl().queryCitys(mEntityListCountryClient.get(position).getIdCountry()).stream().map(CityEntity::getName).collect(Collectors.toList()));
                    adapterCity = new ArrayAdapter<>(ClientActivity.this, android.R.layout.simple_spinner_item, listCity);

                    adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    mDistrictClient.setAdapter(adapterCity);

                    mDistrictClient.setSelection(1);

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
    }
    mTxFindText = (EditText) findViewById(R.id.find_client_medicine);
    mOrder = (Button)findViewById(R.id.OrderButton);
    mOrder.setBackgroundColor(Color.rgb(98,99,155));
        mOrder.setOnClickListener(view -> {
            saveText();
            loadText();
        });
    mSearch = (Button)findViewById(R.id.SearchButton);
    mSearch.setBackgroundColor(Color.rgb(98,99,155));
    mSearch.setOnClickListener(view -> {
        if(checkInputField())
        {
            FindAtTheBase();
            mAdapterList = new MedicineAdapter(ClientActivity.this, mMedicineEntityArrayList);
            mListMedicine = (ListView) findViewById(R.id.listMedicineClient);
            mListMedicine.setAdapter(mAdapterList);
            mAdapterList.notifyDataSetChanged();
        }
        else {
            mTxFindText.setError(getString(R.string.error_field_required));
            mTxFindText.requestFocus();
        }
    });

}
    void saveText() {
        mPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(SAVED_TEXT, mListMedicine.getCheckedItemPositions().toString());
        ed.commit();
        Toast.makeText(this, "The medicine is placed in the cart.", Toast.LENGTH_SHORT).show();
    }

    void loadText() {
        mPref = getPreferences(MODE_PRIVATE);
        Toast.makeText(this, "In the cart.", Toast.LENGTH_SHORT).show();
    }
    private void FindAtTheBase() {
        try {
            mMedicineEntityArrayList = new MedicineDaoImpl().findByNameMedicine(mTxFindText.getText().toString());
            if(mMedicineEntityArrayList.size() == 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
