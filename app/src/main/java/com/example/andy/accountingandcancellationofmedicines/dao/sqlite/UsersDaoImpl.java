package com.example.andy.accountingandcancellationofmedicines.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.andy.accountingandcancellationofmedicines.dao.UsersDao;
import com.example.andy.accountingandcancellationofmedicines.database.Singleton;
import com.example.andy.accountingandcancellationofmedicines.database.UsersTable;
import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;

public class UsersDaoImpl implements UsersDao{

    private final SQLiteDatabase fDb;

    public UsersDaoImpl(){
        fDb = Singleton.getInstance();
    }

    @Override
    public UsersEntity read(String login) {
        Cursor c = fDb.query(UsersTable.NameUsersTable, null, UsersTable.ColumnUsersTable.LoginUser + " = ?", new String[]{login}, null, null, null);
        UsersEntity usersEntity = getUsersEntity(c);
        c.close();
        return usersEntity;
    }

    @Nullable
    private UsersEntity getUsersEntity(Cursor c) {
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
        return usersEntity;
    }

    @Override
    public long addUser(UsersEntity entity) throws Exception {
        ContentValues newValues = addContentValuesUser(entity);
        return fDb.insertOrThrow(UsersTable.NameUsersTable, null,newValues);
    }

    @NonNull
    private ContentValues addContentValuesUser(UsersEntity entity) {
        ContentValues newValues = new ContentValues();
        newValues.put(UsersTable.ColumnUsersTable.LoginUser, entity.getLogin());
        newValues.put(UsersTable.ColumnUsersTable.PasswordUser, entity.getPassword());
        newValues.put(UsersTable.ColumnUsersTable.SurnameUser, entity.getSurnameUser());
        newValues.put(UsersTable.ColumnUsersTable.NameUser, entity.getNameUser());
        newValues.put(UsersTable.ColumnUsersTable.PatronymicUser, entity.getPatronymicUser());
        newValues.put(UsersTable.ColumnUsersTable.TypeUser, entity.getTypeUser());
        return newValues;
    }
}
