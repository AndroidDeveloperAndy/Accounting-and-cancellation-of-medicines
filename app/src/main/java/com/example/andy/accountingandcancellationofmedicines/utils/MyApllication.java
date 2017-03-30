package com.example.andy.accountingandcancellationofmedicines.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;

public class MyApllication extends Application{

    private SharedPreferences mPreferences;
    private Locale mLocale;
    private String mLang;

    @Override
    public void onCreate() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLang = mPreferences.getString("lang", "default");
        if (mLang.equals("default")) {
            mLang = getResources().getConfiguration().locale.getCountry();}
        mLocale = new Locale(mLang);
        Locale.setDefault(mLocale);
        Configuration config = new Configuration();
        config.locale = mLocale;
        getBaseContext().getResources().updateConfiguration(config, null);
        Log.d("LOL","mLocale"+ mLocale);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mLocale = new Locale(mLang);
        Locale.setDefault(mLocale);
        Configuration config = new Configuration();
        config.locale = mLocale;
        getBaseContext().getResources().updateConfiguration(config, null);
        Log.d("LOLU","mLocale"+ mLocale);
    }
}
