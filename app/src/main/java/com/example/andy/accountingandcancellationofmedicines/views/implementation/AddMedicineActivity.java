package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.adapter.ShopAdapter;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.LotDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MeasureDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.MedicineDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.ShopDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.LotEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MeasureEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.ShopEntity;
import com.example.andy.accountingandcancellationofmedicines.utils.DialogFactory;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.AddMedicineImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_add_medicine)
public class AddMedicineActivity extends AppCompatActivity implements AddMedicineImpl{

    private static final String ARG_MEDICINE = MedicineEntity.class.getCanonicalName();

    @ViewById(R.id.listShops)            ListView mListView;
    @ViewById(R.id.editTextNameMedicine) EditText mNameMedicine;
    @ViewById(R.id.editTextNote)         EditText mNote;
    @ViewById(R.id.editTextAD)           EditText mArrivalDate;
    @ViewById(R.id.Amount)               EditText mAmount;
    @ViewById(R.id.DateOfManufacture)    EditText mDateOfManufacture;
    @ViewById(R.id.shelfLife)            EditText mShelfLife;
    @ViewById(R.id.MeasurAmount)         Spinner mMeasureSpinner;
    @ViewById(R.id.spinnerLotNumber)     Spinner mLotNumberSpinner;
    @ViewById(R.id.add_medicine_pageAdd) Button mAddButton;

    private MedicineEntity mMedicineEntity;
    private List <MeasureEntity> mMeasureEntityList;
    private List <String> mStringList = new ArrayList<>();
    private List <String> mStringListLot = new ArrayList<>();

    public static Intent newInstanceUpdate(Context context, MedicineEntity entity){
        return new Intent(context, AddMedicineActivity.class).putExtra(ARG_MEDICINE, entity);
    }

    @AfterViews
    public void initAMA() {
        try {
            initComponent();
        if(getIntent() != null)
            if(getIntent().getSerializableExtra(ARG_MEDICINE) != null) {
                mMedicineEntity = (MedicineEntity) getIntent().getSerializableExtra(ARG_MEDICINE);
                mAddButton.setText(R.string.update_button);
                mAddButton.setOnClickListener(view -> setDataFromDatabase());
            } else {
                mMedicineEntity = new MedicineEntity();
            }
        else
            mMedicineEntity = new MedicineEntity();
            setTextForUpdate();
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    @Override
    public void setTextForUpdate() {
        mNameMedicine.setText(mMedicineEntity.getNameMedicine());
        mNote.setText(mMedicineEntity.getNote());
        mArrivalDate.setText(mMedicineEntity.getArrivalDate());
        mAmount.setText(String.valueOf(mMedicineEntity.getAmount()));
        mDateOfManufacture.setText(mMedicineEntity.getDateOfManufacture());
        mShelfLife.setText(mMedicineEntity.getShelfLife());
        for(int i = 0; i < mMeasureEntityList.size(); i++){
            if(mMeasureEntityList.get(i).getIdMeasure().equals(mMedicineEntity.getIdMeasure())) {
                mMeasureSpinner.setSelection(i);
                break;
            } else
                mMeasureSpinner.setSelection(0);
        }
    }

    @Override
    public void initComponent() {
        try {
            List<LotEntity> lotEntityList = new LotDaoImpl().queryLot();
            for(LotEntity o: lotEntityList)
                mStringListLot.add(String.valueOf(o.getlNumber()));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStringListLot.toArray(new String[mStringListLot.size()]));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mLotNumberSpinner.setAdapter(adapter);
            List<ShopEntity> shopEntityArrayList = new ShopDaoImpl().queryAllShop();
            mMeasureEntityList = new MeasureDaoImpl().queryMeasureName();
            for(MeasureEntity o: mMeasureEntityList)
                mStringList.add(o.getName());
            ArrayAdapter<String> adapterMeasure = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStringList.toArray(new String[mStringList.size()]));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mMeasureSpinner.setAdapter(adapterMeasure);
            ShopAdapter adapterListView = new ShopAdapter(this, shopEntityArrayList);
            mListView.setAdapter(adapterListView);
            mAddButton.setOnClickListener(view -> addMedicine());
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    @Override
    public void addMedicine(){
        try {
            if(checkInputField())
                setDataFromDatabase();
        new MedicineDaoImpl().addMedicine(mMedicineEntity);
        Toast.makeText(this,getResources().getString(R.string.toastAdd),Toast.LENGTH_LONG);
        AddMedicineActivity.this.startActivity(new Intent(AddMedicineActivity.this, AdminActivity.class));
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }


    @Override
    public void setDataFromDatabase() {
        mMedicineEntity.setNameMedicine(mNameMedicine.getText().toString());
        mMedicineEntity.setNote(mNote.getText().toString());
        mMedicineEntity.setArrivalDate(mArrivalDate.getText().toString());
        mMedicineEntity.setAmount(Integer.valueOf(mAmount.getText().toString()));
        mMedicineEntity.setDateOfManufacture(mDateOfManufacture.getText().toString());
        mMedicineEntity.setShelfLife(mShelfLife.getText().toString());
        mMedicineEntity.setIdMeasure(mMeasureEntityList.get(mMeasureSpinner.getSelectedItemPosition()).getIdMeasure());
        new MedicineDaoImpl().update(mMedicineEntity);
        finish();
    }

    @Override
    public boolean checkInputField() {
        return mNameMedicine.getText().toString().length() > 0 &&
                mNote.getText().toString().length() > 0 &&
                mArrivalDate.getText().toString().length() > 0 &&
                mAmount.getText().toString().length() > 0 &&
                mDateOfManufacture.getText().toString().length() > 0 &&
                mShelfLife.getText().toString().length() > 0;
    }

    public void showError(){
        DialogFactory.createGenericErrorDialog(this,"Sorry,an error occurred.").show();
    }
}
