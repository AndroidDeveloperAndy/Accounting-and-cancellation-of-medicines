package com.example.andy.accountingandcancellationofmedicines.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.accountingandcancellationofmedicines.DatabaseHelper;

/**
 * Created by Andy on 27.11.16.
 */
public class Singl {

    SQLiteDatabase db;

    private static Singl ourInstance = null;

    public static Singl getInstance(Context context) {

        if(ourInstance == null)
            ourInstance = new Singl(context);
        return ourInstance;
    }

    private Singl(Context context){
        db = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public static SQLiteDatabase getInstance() {

        if(ourInstance == null)
            throw new RuntimeException("not initialized!!!");
        return ourInstance.db;
    }

}
