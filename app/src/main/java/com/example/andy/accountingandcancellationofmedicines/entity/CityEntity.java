package com.example.andy.accountingandcancellationofmedicines.entity;

/**
 * Created by Andy on 27.11.16.
 */
public class CityEntity {
    private Integer idCity;
    private String name;
    private Integer postCode;
    private Integer idCountry;


    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityEntity that = (CityEntity) o;

        if (idCity != null ? !idCity.equals(that.idCity) : that.idCity != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (postCode != null ? !postCode.equals(that.postCode) : that.postCode != null) return false;
        if (idCountry != null ? !idCountry.equals(that.idCountry) : that.idCountry != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCity != null ? idCity.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (idCountry != null ? idCountry.hashCode() : 0);
        return result;
    }
}
