package com.example.andy.accountingandcancellationofmedicines;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.adapter.MedicineAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CityDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.CountryDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.CountryEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = ClientActivity.class.getName();
    Spinner districtClient,countryClient;
    List<CountryEntity> entityListCountryClient;
    List<CityEntity> entityListCityClient;
    Button order,search;
    EditText txFindText;
    ListView listMedicine;

    MedicineAdapter adapterList;
    ArrayList<MedicineEntity> medicineEntityArrayList;

    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                    adapterCity = new ArrayAdapter<>(ClientActivity.this, android.R.layout.simple_spinner_item, listCity);

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
    txFindText = (EditText) findViewById(R.id.find_client_medicine);
    order = (Button)findViewById(R.id.OrderButton);
    order.setBackgroundColor(Color.rgb(98,99,155));
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveText();
                loadText();
            }
        });
    search = (Button)findViewById(R.id.SearchButton);
    search.setBackgroundColor(Color.rgb(98,99,155));
    search.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(checkInputField())
            {
                FindAtTheBase();
                adapterList = new MedicineAdapter(ClientActivity.this, medicineEntityArrayList);
                listMedicine = (ListView) findViewById(R.id.listMedicineClient);
                listMedicine.setAdapter(adapterList);
                adapterList.notifyDataSetChanged();
            }
            else {
                txFindText.setError(getString(R.string.error_field_required));
                txFindText.requestFocus();
            }
        }
    });

}
    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, listMedicine.getCheckedItemPositions().toString());
        ed.commit();
        Toast.makeText(this, "The medicine is placed in the cart.", Toast.LENGTH_SHORT).show();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        //String savedText = sPref.getString(SAVED_TEXT, "");
        //etText.setText(savedText);
        Toast.makeText(this, "In the cart.", Toast.LENGTH_SHORT).show();
    }
    private void FindAtTheBase() {
        try {
            medicineEntityArrayList = new MedicineDaoImpl().findByNameMedicine(txFindText.getText().toString());
            if(medicineEntityArrayList.size() == 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
                builder.setTitle("Repeat the find.")
                        .setMessage("Nothing was found.")
                        .setIcon(R.drawable.notfound);
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkInputField() {
        return txFindText.getText().toString().length() > 0;
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
