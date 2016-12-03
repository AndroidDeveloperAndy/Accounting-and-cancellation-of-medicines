package com.example.andy.accountingandcancellationofmedicines.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.andy.accountingandcancellationofmedicines.R;

import java.util.Locale;

/**
 * Created by Andy on 03.12.16.
 */

public class MyApllication extends Application{

    private SharedPreferences preferences;
    private Locale locale;
    private String lang;

    @Override
    public void onCreate() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        lang = preferences.getString("lang", "default");
        if (lang.equals("default")) {lang=getResources().getConfiguration().locale.getCountry();}
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        Log.d("LOL","locale"+locale);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        Log.d("LOLU","locale"+locale);
    }
}
