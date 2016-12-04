package com.example.andy.accountingandcancellationofmedicines.entity;

/**
 * Created by Andy on 27.11.16.
 */
public class LotEntity {
    private Integer lNumber;
    private Integer idLot;
    private String nameCountry;
    private String nameCity;
    private String adress;
    private String departureDate;
    private Integer priceOne;

    public Integer getlNumber() {
        return lNumber;
    }

    public void setlNumber(Integer lNumber) {
        this.lNumber = lNumber;
    }

    public Integer getIdLot() {
        return idLot;
    }

    public void setIdLot(Integer idLot) {
        this.idLot = idLot;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(Integer priceOne) {
        this.priceOne = priceOne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotEntity lotEntity = (LotEntity) o;

        if (lNumber != null ? !lNumber.equals(lotEntity.lNumber) : lotEntity.lNumber != null) return false;
        if (idLot != null ? !idLot.equals(lotEntity.idLot) : lotEntity.idLot != null) return false;
        if (nameCountry != null ? !nameCountry.equals(lotEntity.nameCountry) : lotEntity.nameCountry != null)
            return false;
        if (nameCity != null ? !nameCity.equals(lotEntity.nameCity) : lotEntity.nameCity != null) return false;
        if (adress != null ? !adress.equals(lotEntity.adress) : lotEntity.adress != null) return false;
        if (departureDate != null ? !departureDate.equals(lotEntity.departureDate) : lotEntity.departureDate != null)
            return false;
        if (priceOne != null ? !priceOne.equals(lotEntity.priceOne) : lotEntity.priceOne != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lNumber != null ? lNumber.hashCode() : 0;
        result = 31 * result + (idLot != null ? idLot.hashCode() : 0);
        result = 31 * result + (nameCountry != null ? nameCountry.hashCode() : 0);
        result = 31 * result + (nameCity != null ? nameCity.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (departureDate != null ? departureDate.hashCode() : 0);
        result = 31 * result + (priceOne != null ? priceOne.hashCode() : 0);
        return result;
    }


}
