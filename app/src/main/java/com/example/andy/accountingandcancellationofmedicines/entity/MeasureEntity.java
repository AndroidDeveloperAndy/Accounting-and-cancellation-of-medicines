package com.example.andy.accountingandcancellationofmedicines.entity;

public class MeasureEntity {
    private Integer idMeasure;
    private String name;

    public Integer getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(Integer idMeasure) {
        this.idMeasure = idMeasure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasureEntity that = (MeasureEntity) o;
        if (idMeasure != null ? !idMeasure.equals(that.idMeasure) : that.idMeasure != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMeasure != null ? idMeasure.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
