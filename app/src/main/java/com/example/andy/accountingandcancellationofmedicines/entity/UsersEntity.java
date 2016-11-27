package com.example.andy.accountingandcancellationofmedicines.entity;

/**
 * Created by Andy on 27.11.16.
 */
public class UsersEntity {
    private Integer idUser;
    private String surnameUser;
    private String nameUser;
    private String patronymicUser;
    private String typeUser;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getSurnameUser() {
        return surnameUser;
    }

    public void setSurnameUser(String surnameUser) {
        this.surnameUser = surnameUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPatronymicUser() {
        return patronymicUser;
    }

    public void setPatronymicUser(String patronymicUser) {
        this.patronymicUser = patronymicUser;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (idUser != null ? !idUser.equals(that.idUser) : that.idUser != null) return false;
        if (surnameUser != null ? !surnameUser.equals(that.surnameUser) : that.surnameUser != null) return false;
        if (nameUser != null ? !nameUser.equals(that.nameUser) : that.nameUser != null) return false;
        if (patronymicUser != null ? !patronymicUser.equals(that.patronymicUser) : that.patronymicUser != null)
            return false;
        if (typeUser != null ? !typeUser.equals(that.typeUser) : that.typeUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser != null ? idUser.hashCode() : 0;
        result = 31 * result + (surnameUser != null ? surnameUser.hashCode() : 0);
        result = 31 * result + (nameUser != null ? nameUser.hashCode() : 0);
        result = 31 * result + (patronymicUser != null ? patronymicUser.hashCode() : 0);
        result = 31 * result + (typeUser != null ? typeUser.hashCode() : 0);
        return result;
    }
}
