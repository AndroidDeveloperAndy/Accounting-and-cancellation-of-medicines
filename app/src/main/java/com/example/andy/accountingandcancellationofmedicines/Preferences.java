package com.example.andy.accountingandcancellationofmedicines;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Andy on 01.12.16.
 */

public class Preferences extends PreferenceActivity {

    private SharedPreferences preferences;
    private Locale locale;
    private String lang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        lang = preferences.getString("lang", "default");
        if (lang.equals("default")) {lang=getResources().getConfiguration().locale.getCountry();}
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
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
    }
}

