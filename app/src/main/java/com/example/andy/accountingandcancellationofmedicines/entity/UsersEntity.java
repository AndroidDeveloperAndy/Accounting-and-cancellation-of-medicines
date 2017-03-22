package com.example.andy.accountingandcancellationofmedicines.entity;

public class UsersEntity {
    private Integer idUser;
    private String surnameUser;
    private String nameUser;
    private String patronymicUser;
    private String typeUser;
    private String Login;
    private String Password;

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

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
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
        if (Login != null ? !Login.equals(that.Login) : that.Login != null) return false;
        if (Password != null ? !Password.equals(that.Password) : that.Password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser != null ? idUser.hashCode() : 0;
        result = 31 * result + (surnameUser != null ? surnameUser.hashCode() : 0);
        result = 31 * result + (nameUser != null ? nameUser.hashCode() : 0);
        result = 31 * result + (patronymicUser != null ? patronymicUser.hashCode() : 0);
        result = 31 * result + (typeUser != null ? typeUser.hashCode() : 0);
        result = 31 * result + (Login != null ? Login.hashCode() : 0);
        result = 31 * result + (Password != null ? Password.hashCode() : 0);
        return result;
    }
}
