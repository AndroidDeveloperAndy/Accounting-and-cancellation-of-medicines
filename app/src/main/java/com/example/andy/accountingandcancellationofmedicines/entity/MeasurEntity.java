package com.example.andy.accountingandcancellationofmedicines.entity;

/**
 * Created by Andy on 27.11.16.
 */
public class MeasurEntity {
    private Integer idMedicine;
    private Integer kilo;
    private Integer pieces;
    private Integer gram;
    private Integer liters;
    private Integer mliters;

    public Integer getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(Integer idMedicine) {
        this.idMedicine = idMedicine;
    }

    public Integer getKilo() {
        return kilo;
    }

    public void setKilo(Integer kilo) {
        this.kilo = kilo;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Integer getGram() {
        return gram;
    }

    public void setGram(Integer gram) {
        this.gram = gram;
    }

    public Integer getLiters() {
        return liters;
    }

    public void setLiters(Integer liters) {
        this.liters = liters;
    }

    public Integer getMliters() {
        return mliters;
    }

    public void setMliters(Integer mliters) {
        this.mliters = mliters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurEntity that = (MeasurEntity) o;

        if (idMedicine != null ? !idMedicine.equals(that.idMedicine) : that.idMedicine != null) return false;
        if (kilo != null ? !kilo.equals(that.kilo) : that.kilo != null) return false;
        if (pieces != null ? !pieces.equals(that.pieces) : that.pieces != null) return false;
        if (gram != null ? !gram.equals(that.gram) : that.gram != null) return false;
        if (liters != null ? !liters.equals(that.liters) : that.liters != null) return false;
        if (mliters != null ? !mliters.equals(that.mliters) : that.mliters != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMedicine != null ? idMedicine.hashCode() : 0;
        result = 31 * result + (kilo != null ? kilo.hashCode() : 0);
        result = 31 * result + (pieces != null ? pieces.hashCode() : 0);
        result = 31 * result + (gram != null ? gram.hashCode() : 0);
        result = 31 * result + (liters != null ? liters.hashCode() : 0);
        result = 31 * result + (mliters != null ? mliters.hashCode() : 0);
        return result;
    }
}
