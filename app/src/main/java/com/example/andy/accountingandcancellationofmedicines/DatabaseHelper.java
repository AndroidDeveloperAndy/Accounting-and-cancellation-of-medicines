package com.example.andy.accountingandcancellationofmedicines;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Andy on 15.11.16.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "new_upgrade_2-3.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}


