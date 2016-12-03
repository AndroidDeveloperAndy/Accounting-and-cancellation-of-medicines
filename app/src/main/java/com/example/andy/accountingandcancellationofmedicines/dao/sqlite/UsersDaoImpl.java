package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.accountingandcancellationofmedicines.dao.UsersDao;
import com.example.andy.accountingandcancellationofmedicines.database.UsersTable;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;

/**
 * Created by Andy on 28.11.16.
 */

public class UsersDaoImpl implements UsersDao{

    private final SQLiteDatabase db;

    public UsersDaoImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public UsersEntity read(String login) {
        Cursor c = db.query(UsersTable.NameUsersTable, null, UsersTable.ColumnUsersTable.LoginUser + " = ?", new String[]{login}, null, null, null);
        UsersEntity usersEntity = new UsersEntity();
        if(c.getCount() !=0){
            c.moveToFirst();
            usersEntity.setIdUser(c.getInt(c.getColumnIndex(UsersTable.ColumnUsersTable.IdUser)));
            usersEntity.setLogin(c.getString(c.getColumnIndex(UsersTable.ColumnUsersTable.LoginUser)));
            usersEntity.setPassword(c.getString(c.getColumnIndex(UsersTable.ColumnUsersTable.PasswordUser)));
            usersEntity.setNameUser(c.getString(c.getColumnIndex(UsersTable.ColumnUsersTable.NameUser)));
            usersEntity.setSurnameUser(c.getString(c.getColumnIndex(UsersTable.ColumnUsersTable.SurnameUser)));
            usersEntity.setPatronymicUser(c.getString(c.getColumnIndex(UsersTable.ColumnUsersTable.PatronymicUser)));
            usersEntity.setTypeUser(c.getString(c.getColumnIndex(UsersTable.ColumnUsersTable.TypeUser)));
        }
        c.close();
        return usersEntity;
    }
}
