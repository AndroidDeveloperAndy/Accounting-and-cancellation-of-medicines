package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.UsersDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;
import com.example.andy.accountingandcancellationofmedicines.utils.DialogFactory;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.CheckInClientImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.registration)
public class CheckInClientActivity extends AppCompatActivity implements CheckInClientImpl {

    @ViewById(R.id.check_in_button_form)
    Button mButtonAdd;
    @ViewById(R.id.login_checkIN)
    EditText mTxLogin;
    @ViewById(R.id.password_client)
    EditText mTxPass;
    @ViewById(R.id.surname_client)
    EditText mTxSurname;
    @ViewById(R.id.name_client)
    EditText mTxName;
    @ViewById(R.id.patronymic_client)
    EditText mTxPatronymic;

    @AfterViews
    void initCIC() {
        mButtonAdd.setOnClickListener(view -> addUser());
    }

    @Override
    public void addUser() {
        if (checkInputField())
            addClientToBase();
        Toast.makeText(this, getResources().getString(R.string.user_add), Toast.LENGTH_LONG);
        CheckInClientActivity.this.startActivity(new Intent(CheckInClientActivity.this, LoginActivity.class));
    }

    @Override
    public void addClientToBase() {
        try {
            UsersEntity entity = new UsersEntity();
            entity.setLogin(mTxLogin.getText().toString());
            entity.setPassword(mTxPass.getText().toString());
            entity.setSurnameUser(mTxSurname.getText().toString());
            entity.setNameUser(mTxName.getText().toString());
            entity.setPatronymicUser(mTxPatronymic.getText().toString());
            entity.setTypeUser("Client");
            new UsersDaoImpl().addUser(entity);
        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkInputField() {
        return mTxLogin.getText().toString().length() > 0 &&
                mTxPass.getText().toString().length() > 0 &&
                mTxSurname.getText().toString().length() > 0 &&
                mTxName.getText().toString().length() > 0 &&
                mTxPatronymic.getText().toString().length() > 0;
    }

    public void showError() {
        DialogFactory.createGenericErrorDialog(this, "Sorry,an error occurred.").show();
    }
}
