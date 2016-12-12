package com.example.andy.accountingandcancellationofmedicines;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity {

    private static final String TAG = AddMedicineActivity.class.getName();
    private static final String ARG_MEDICINE = MedicineEntity.class.getCanonicalName();

    private List<ShopEntity> shopEntityArrayList;
    private ShopAdapter adapterListView;
    private ListView lvMain;
    private MedicineEntity entity;

    private Spinner measureSpinner,lotNumberSpinner;
    private Button add;

    private EditText nameMedicine,note,arrivalDate,amount,dateOfManufacture,shelfLife;
    private List <MeasurEntity> measureEntityList;
    private List <String> stringList = new ArrayList<>();
    private List <LotEntity> lotEntityList;
    private List <String> stringListLot = new ArrayList<>();

    public static Intent newInstanceUpdate(Context context, MedicineEntity entity){
        Intent intent = new Intent(context, AddMedicineActivity.class);

        intent.putExtra(ARG_MEDICINE, entity);

        return intent;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_medicine);

            //Init view components
            lotNumberSpinner = (Spinner) findViewById(R.id.spinnerLotNumber);
            measureSpinner = (Spinner) findViewById(R.id.MeasurAmount);
            lvMain = (ListView) findViewById(R.id.listShops);
            nameMedicine = (EditText)findViewById(R.id.editTextNameMedicine);
            note = (EditText)findViewById(R.id.editTextNote);
            arrivalDate = (EditText)findViewById(R.id.editTextAD);
            amount = (EditText)findViewById(R.id.Amount);
            dateOfManufacture = (EditText)findViewById(R.id.DateOfManufacture);
            shelfLife = (EditText)findViewById(R.id.shelfLife);
            add = (Button)findViewById(R.id.add_medicine_pageAdd);

            initComponent();

            if(getIntent() != null)
                if(getIntent().getSerializableExtra(ARG_MEDICINE) != null) {
                    entity = (MedicineEntity) getIntent().getSerializableExtra(ARG_MEDICINE);
                    add.setText(R.string.update_button);
                    add.setOnClickListener(view -> {
                        try {
                            ExportDataToEntity();
                            new MedicineDaoImpl().update(entity);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                } else {
                    entity = new MedicineEntity();
                }
            else
                entity = new MedicineEntity();

            setDataForUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "errore! ", e);
        }
    }

    private void setDataForUpdate() {

        nameMedicine.setText(entity.getNameMedicine());
        note.setText(entity.getNote());
        arrivalDate.setText(entity.getArrivalDate());
        amount.setText(String.valueOf(entity.getAmount()));
        dateOfManufacture.setText(entity.getDateOfManufacture());
        shelfLife.setText(entity.getShelfLife());

        for(int i = 0; i < measureEntityList.size(); i++){
            if(measureEntityList.get(i).getIdMeasure().equals(entity.getIdMeasure())) {
                measureSpinner.setSelection(i);
                break;
            } else
                measureSpinner.setSelection(0);
        }

    }

    private void initComponent() throws Exception {
        lotEntityList = new LotDaoImpl().queryLot();
        for(LotEntity o: lotEntityList)
            stringListLot.add(String.valueOf(o.getlNumber()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringListLot.toArray(new String[stringListLot.size()]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lotNumberSpinner.setAdapter(adapter);

        shopEntityArrayList = new ShopDaoImpl().queryAllShop();
        measureEntityList = new MeasureDaoImpl().queryMeasureName();

        for(MeasurEntity o: measureEntityList)
            stringList.add(o.getName());

        ArrayAdapter<String> adapterMeasure = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringList.toArray(new String[stringList.size()]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measureSpinner.setAdapter(adapterMeasure);

        adapterListView = new ShopAdapter(this, shopEntityArrayList);

        lvMain.setAdapter(adapterListView);


        add.setBackgroundColor(Color.rgb(98,99,155));
        add.setOnClickListener(view -> {
            if(checkInputField())
                addToBase();
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastAdd),Toast.LENGTH_LONG);
            AddMedicineActivity.this.startActivity(new Intent(AddMedicineActivity.this, StartPageActivity.class));
        });
    }

    private void addToBase() {
        ExportDataToEntity();
        try {
            Log.d("ADD", entity.toString());
            new MedicineDaoImpl().addMedicine(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ExportDataToEntity() {
        entity.setNameMedicine(nameMedicine.getText().toString());
        entity.setNote(note.getText().toString());
        entity.setArrivalDate(arrivalDate.getText().toString());
        entity.setAmount(Integer.valueOf(amount.getText().toString()));
        entity.setDateOfManufacture(dateOfManufacture.getText().toString());
        entity.setShelfLife(shelfLife.getText().toString());
        entity.setIdMeasure(measureEntityList.get(measureSpinner.getSelectedItemPosition()).getIdMeasure());
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
