package com.example.andy.accountingandcancellationofmedicines.settings;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import com.example.andy.accountingandcancellationofmedicines.R;

import java.util.Locale;

public class Preferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        ListPreference listPreference = (ListPreference) findPreference("lang");
        listPreference.setOnPreferenceChangeListener((preference, o) -> {
            Locale locale = new Locale((String) o);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        });
        PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getIntent()), 0);
    }
}

