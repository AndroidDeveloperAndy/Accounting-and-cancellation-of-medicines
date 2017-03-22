package com.example.andy.accountingandcancellationofmedicines.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Singleton {

    private SQLiteDatabase fDb;
    private static Singleton ourInstance = null;

    public static Singleton getInstance(Context context) {
        if(ourInstance == null)
            ourInstance = new Singleton(context);
        return ourInstance;
    }

    private Singleton(Context context){
        fDb = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public static SQLiteDatabase getInstance() {
        if(ourInstance == null)
            throw new RuntimeException("not initialized!!!");
        return ourInstance.fDb;
    }

}
