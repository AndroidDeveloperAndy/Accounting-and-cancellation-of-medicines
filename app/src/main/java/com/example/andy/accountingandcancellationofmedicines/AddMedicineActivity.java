package com.example.andy.accountingandcancellationofmedicines;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.adapter.ShopAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.LotDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MeasureDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.ShopDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.LotEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MeasurEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity {

    ArrayList<ShopEntity> shopEntityArrayList;
    ShopAdapter adapterListView;
    ListView lvMain;

    Spinner measureSpinner,lotNumberSpinner;
    Button add;

    EditText nameMedicine,note,arrivalDate,amount,dateOfManufacture,shelfLife;
    List <MeasurEntity> measureEntityList;
    List <String> stringList = new ArrayList<>();
    List <LotEntity> lotEntityList;
    List <String> stringListLot = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

            lotEntityList = new LotDaoImpl().queryLot();

            for(LotEntity o: lotEntityList)
                stringListLot.add(String.valueOf(o.getlNumber()));

            lotNumberSpinner = (Spinner) findViewById(R.id.spinnerLotNumber);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, stringListLot.toArray(new String[stringListLot.size()]));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            lotNumberSpinner.setAdapter(adapter);
            shopEntityArrayList = new ShopDaoImpl().queryAllShop();

            long date = new Date().getTime();

            measureEntityList = new MeasureDaoImpl().queryMeasureName();

            for(MeasurEntity o: measureEntityList)
                stringList.add(o.getName());

            measureSpinner = (Spinner) findViewById(R.id.MeasurAmount);
            ArrayAdapter<String> adapterMeasure = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, stringList.toArray(new String[stringList.size()]));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            measureSpinner.setAdapter(adapterMeasure);

            adapterListView = new ShopAdapter(this, shopEntityArrayList);
            lvMain = (ListView) findViewById(R.id.listShops);
            lvMain.setAdapter(adapterListView);

            nameMedicine = (EditText)findViewById(R.id.editTextNameMedicine);
            note = (EditText)findViewById(R.id.editTextNote);
            arrivalDate = (EditText)findViewById(R.id.editTextAD);
            amount = (EditText)findViewById(R.id.Amount);
            dateOfManufacture = (EditText)findViewById(R.id.DateOfManufacture);
            shelfLife = (EditText)findViewById(R.id.shelfLife);

            add = (Button)findViewById(R.id.add_medicine_pageAdd);
            add.setBackgroundColor(Color.rgb(98,99,155));
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkInputField())
                        addToBase();
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastAdd),Toast.LENGTH_LONG);
                    AddMedicineActivity.this.startActivity(new Intent(AddMedicineActivity.this, StartPageActivity.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToBase() {
        MedicineEntity entity = new MedicineEntity();
        entity.setNameMedicine(nameMedicine.getText().toString());
        entity.setNote(note.getText().toString());
        entity.setArrivalDate(arrivalDate.getText().toString());
        entity.setAmount(Integer.valueOf(amount.getText().toString()));
        entity.setDateOfManufacture(dateOfManufacture.getText().toString());
        entity.setShelfLife(shelfLife.getText().toString());
        entity.setIdMeasure(measureEntityList.get(measureSpinner.getSelectedItemPosition()).getIdMeasure());
        try {
            Log.d("ADD", entity.toString());
            new MedicineDaoImpl().addMedicine(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkInputField() {
        return nameMedicine.getText().toString().length() > 0 &&
                note.getText().toString().length() > 0 &&
                arrivalDate.getText().toString().length() > 0 &&
                amount.getText().toString().length() > 0 &&
                dateOfManufacture.getText().toString().length() > 0 &&
                shelfLife.getText().toString().length() > 0;
    }
}
