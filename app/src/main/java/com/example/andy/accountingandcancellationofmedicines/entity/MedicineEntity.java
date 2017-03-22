package com.example.andy.accountingandcancellationofmedicines.entity;

import java.io.Serializable;

public class MedicineEntity implements Serializable {
    private Integer id;
    private String nameMedicine;
    private Integer lotNumber;
    private String note;
    private Integer amount;
    private String arrivalDate;
    private String dateOfManufacture;
    private String shelfLife;
    private Integer idMeasure;

    public MedicineEntity(){}
    public MedicineEntity(Integer id, String nameMedicine, Integer lotNumber, String note, Integer amount, String arrivalDate, String dateOfManufacture, String shelfLife,Integer idMeasure) {
        this.id = id;
        this.nameMedicine = nameMedicine;
        this.lotNumber = lotNumber;
        this.note = note;
        this.amount = amount;
        this.arrivalDate = arrivalDate;
        this.dateOfManufacture = dateOfManufacture;
        this.shelfLife = shelfLife;
        this.idMeasure = idMeasure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public void setNameMedicine(String nameMedicine) {
        this.nameMedicine = nameMedicine;
    }

    public Integer getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(Integer lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(String dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Integer getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(Integer idMeasure) {
        this.idMeasure = idMeasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineEntity that = (MedicineEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nameMedicine != null ? !nameMedicine.equals(that.nameMedicine) : that.nameMedicine != null) return false;
        if (lotNumber != null ? !lotNumber.equals(that.lotNumber) : that.lotNumber != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (arrivalDate != null ? !arrivalDate.equals(that.arrivalDate) : that.arrivalDate != null) return false;
        if (dateOfManufacture != null ? !dateOfManufacture.equals(that.dateOfManufacture) : that.dateOfManufacture != null)
            return false;
        if (shelfLife != null ? !shelfLife.equals(that.shelfLife) : that.shelfLife != null) return false;
        if (idMeasure != null ? !idMeasure.equals(that.idMeasure) : that.idMeasure != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nameMedicine != null ? nameMedicine.hashCode() : 0);
        result = 31 * result + (lotNumber != null ? lotNumber.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (arrivalDate != null ? arrivalDate.hashCode() : 0);
        result = 31 * result + (dateOfManufacture != null ? dateOfManufacture.hashCode() : 0);
        result = 31 * result + (shelfLife != null ? shelfLife.hashCode() : 0);
        result = 31 * result + (idMeasure != null ? idMeasure.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MedicineEntity{" +
                "id=" + id +
                ", nameMedicine='" + nameMedicine + '\'' +
                ", lotNumber=" + lotNumber +
                ", note='" + note + '\'' +
                ", amount=" + amount +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", dateOfManufacture='" + dateOfManufacture + '\'' +
                ", shelfLife='" + shelfLife + '\'' +
                ", idMeasure='" + idMeasure + '\'' +
                '}';
    }
}
