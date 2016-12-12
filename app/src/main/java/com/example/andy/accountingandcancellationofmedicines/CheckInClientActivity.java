package com.example.andy.accountingandcancellationofmedicines;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.UsersDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;

public class CheckInClientActivity extends AppCompatActivity {

    Button btn;
    EditText txLogin,txPass,txSurname,txName,txPatronymic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_client);

        txLogin = (EditText)findViewById(R.id.login_checkIN);
        txPass = (EditText)findViewById(R.id.password_client);
        txSurname = (EditText)findViewById(R.id.surname_client);
        txName = (EditText)findViewById(R.id.name_client);
        txPatronymic = (EditText)findViewById(R.id.patronymic_client);

        btn = (Button)findViewById(R.id.check_in_button_form);
        btn.setBackgroundColor(Color.rgb(98,99,155));
        btn.setOnClickListener(view -> {
            if(checkInputField())
                addClientToBase();
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.user_add),Toast.LENGTH_LONG);
            CheckInClientActivity.this.startActivity(new Intent(CheckInClientActivity.this, LoginActivity.class));
        });

    }
    private void addClientToBase() {
        UsersEntity entity = new UsersEntity();
        entity.setLogin(txLogin.getText().toString());
        entity.setPassword(txPass.getText().toString());
        entity.setSurnameUser(txSurname.getText().toString());
        entity.setNameUser(txName.getText().toString());
        entity.setPatronymicUser(txPatronymic.getText().toString());
        entity.setTypeUser("Client");
        try {
            Log.d("Add User", entity.toString());
            new UsersDaoImpl().addUser(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkInputField() {
        return txLogin.getText().toString().length() > 0 &&
                txPass.getText().toString().length() > 0 &&
                txSurname.getText().toString().length() > 0 &&
                txName.getText().toString().length() > 0 &&
                txPatronymic.getText().toString().length() > 0;
    }
}
