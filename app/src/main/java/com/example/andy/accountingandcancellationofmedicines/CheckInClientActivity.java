package com.example.andy.accountingandcancellationofmedicines;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class CheckInClientActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_client);

        btn = (Button)findViewById(R.id.check_in_button_form);
        btn.setBackgroundColor(Color.rgb(98,99,155));

    }
}
