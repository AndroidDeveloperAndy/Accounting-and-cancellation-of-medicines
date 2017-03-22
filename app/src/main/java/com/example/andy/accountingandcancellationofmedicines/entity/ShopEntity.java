package com.example.andy.accountingandcancellationofmedicines.entity;

public class ShopEntity {
    private String nameMedicineAtShop;
    private Integer idShop;
    private String nameShop;
    private String adressShop;
    private Integer priceAtShop;

    public String getNameMedicineAtShop() {
        return nameMedicineAtShop;
    }

    public void setNameMedicineAtShop(String nameMedicineAtShop) {
        this.nameMedicineAtShop = nameMedicineAtShop;
    }

    public Integer getIdShop() {
        return idShop;
    }

    public void setIdShop(Integer idShop) {
        this.idShop = idShop;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getAdressShop() {
        return adressShop;
    }

    public void setAdressShop(String adressShop) {
        this.adressShop = adressShop;
    }

    public Integer getPriceAtShop() {
        return priceAtShop;
    }

    public void setPriceAtShop(Integer priceAtShop) {
        this.priceAtShop = priceAtShop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopEntity that = (ShopEntity) o;
        if (nameMedicineAtShop != null ? !nameMedicineAtShop.equals(that.nameMedicineAtShop) : that.nameMedicineAtShop != null)
            return false;
        if (idShop != null ? !idShop.equals(that.idShop) : that.idShop != null) return false;
        if (nameShop != null ? !nameShop.equals(that.nameShop) : that.nameShop != null) return false;
        if (adressShop != null ? !adressShop.equals(that.adressShop) : that.adressShop != null) return false;
        if (priceAtShop != null ? !priceAtShop.equals(that.priceAtShop) : that.priceAtShop != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = nameMedicineAtShop != null ? nameMedicineAtShop.hashCode() : 0;
        result = 31 * result + (idShop != null ? idShop.hashCode() : 0);
        result = 31 * result + (nameShop != null ? nameShop.hashCode() : 0);
        result = 31 * result + (adressShop != null ? adressShop.hashCode() : 0);
        result = 31 * result + (priceAtShop != null ? priceAtShop.hashCode() : 0);
        return result;
    }
}
