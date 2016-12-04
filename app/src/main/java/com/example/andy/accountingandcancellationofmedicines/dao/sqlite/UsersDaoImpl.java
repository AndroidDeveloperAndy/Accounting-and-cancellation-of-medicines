package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.accountingandcancellationofmedicines.dao.UsersDao;
import com.example.andy.accountingandcancellationofmedicines.database.Singl;
import com.example.andy.accountingandcancellationofmedicines.database.UsersTable;
import com.example.andy.accountingandcancellationofmedicines.entity.CityEntity;
import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;

/**
 * Created by Andy on 28.11.16.
 */

public class UsersDaoImpl implements UsersDao{

    private final SQLiteDatabase db;

    public UsersDaoImpl(){
        db = Singl.getInstance();
    }

    @Override
    public UsersEntity read(String login) {
        Cursor c = db.query(UsersTable.NameUsersTable, null, UsersTable.ColumnUsersTable.LoginUser + " = ?", new String[]{login}, null, null, null);
        UsersEntity usersEntity = null;

        if(c.getCount() !=0){
            c.moveToFirst();

            usersEntity = new UsersEntity();

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

    @Override
    public long addUser(UsersEntity entity) throws Exception {

        ContentValues newValues = new ContentValues();
        newValues.put(UsersTable.ColumnUsersTable.LoginUser, entity.getLogin());
        newValues.put(UsersTable.ColumnUsersTable.PasswordUser, entity.getPassword());
        newValues.put(UsersTable.ColumnUsersTable.SurnameUser, entity.getSurnameUser());
        newValues.put(UsersTable.ColumnUsersTable.NameUser, entity.getNameUser());
        newValues.put(UsersTable.ColumnUsersTable.PatronymicUser, entity.getPatronymicUser());
        newValues.put(UsersTable.ColumnUsersTable.TypeUser, entity.getTypeUser());

        return db.insertOrThrow(UsersTable.NameUsersTable, null,newValues);
    }
}
