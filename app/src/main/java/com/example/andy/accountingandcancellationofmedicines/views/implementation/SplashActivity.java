package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

import static com.example.andy.accountingandcancellationofmedicines.utils.StringsUtil.LENGTH;

@EActivity
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            LoginActivity_.intent(this).start();
            finish();
        }, LENGTH);
    }
}
