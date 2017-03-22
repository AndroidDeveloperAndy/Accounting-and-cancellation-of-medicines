package com.example.andy.accountingandcancellationofmedicines.adapter;

import com.example.andy.accountingandcancellationofmedicines.entity.MedicineEntity;

public class WrapperMedicineAdapter {

    private final MedicineEntity entity;
    private boolean isChecked;

    public WrapperMedicineAdapter(MedicineEntity entity) {
        this.entity = entity;
        isChecked = false;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public MedicineEntity getEntity() {
        return entity;
    }
}
