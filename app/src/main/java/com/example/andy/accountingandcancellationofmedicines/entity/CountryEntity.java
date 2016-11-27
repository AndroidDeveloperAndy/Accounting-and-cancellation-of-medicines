package com.example.andy.accountingandcancellationofmedicines.entity;

/**
 * Created by Andy on 27.11.16.
 */
public class CountryEntity {
    private Integer idCountry;
    private String name;
    private Integer codeCountry;
    private Integer codePhone;
    private String nameCity;

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCodeCountry() {
        return codeCountry;
    }

    public void setCodeCountry(Integer codeCountry) {
        this.codeCountry = codeCountry;
    }

    public Integer getCodePhone() {
        return codePhone;
    }

    public void setCodePhone(Integer codePhone) {
        this.codePhone = codePhone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (idCountry != null ? !idCountry.equals(that.idCountry) : that.idCountry != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (codeCountry != null ? !codeCountry.equals(that.codeCountry) : that.codeCountry != null) return false;
        if (codePhone != null ? !codePhone.equals(that.codePhone) : that.codePhone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCountry != null ? idCountry.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (codeCountry != null ? codeCountry.hashCode() : 0);
        result = 31 * result + (codePhone != null ? codePhone.hashCode() : 0);
        return result;
    }
}
