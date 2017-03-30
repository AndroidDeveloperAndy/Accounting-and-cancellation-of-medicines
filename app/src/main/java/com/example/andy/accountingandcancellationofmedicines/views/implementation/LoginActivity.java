package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.UsersDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.database.DatabaseHelper;
import com.example.andy.accountingandcancellationofmedicines.database.Singleton;
import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;
import com.example.andy.accountingandcancellationofmedicines.settings.Preferences;
import com.example.andy.accountingandcancellationofmedicines.utils.DialogFactory;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.LoginActivityImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.example.andy.accountingandcancellationofmedicines.utils.StringsUtil.CODE_ADMIN_SUCCESFUL;
import static com.example.andy.accountingandcancellationofmedicines.utils.StringsUtil.CODE_CLIENT_SUCCESFUL;
import static com.example.andy.accountingandcancellationofmedicines.utils.StringsUtil.CODE_SING_UP_EXEPTION;
import static com.example.andy.accountingandcancellationofmedicines.utils.StringsUtil.isValidLogin;
import static com.example.andy.accountingandcancellationofmedicines.utils.StringsUtil.isValidPassword;

@EActivity(R.layout.loging)
public class LoginActivity extends AppCompatActivity implements LoginActivityImpl {

    private UserLoginTask mAuthTask = null;

    @ViewById(R.id.username) EditText txLogin;
    @ViewById(R.id.password) EditText txPassword;
    @ViewById(R.id.forgot)   TextView txForgotPass;

    @AfterViews
    public void initLA(){
        Singleton.getInstance(this);
        txForgotPass.setOnClickListener(v -> DialogFactory.restoreAccountDialog(LoginActivity.this).show());
        new DatabaseHelper(this).getWritableDatabase();
    }

    @Override
    public void checkLogin(View view) {
        final String email = txLogin.getText().toString();
        final String pass = txPassword.getText().toString();
        if (!isValidLogin(email)) {
            txLogin.setError(getString(R.string.error_invalid_login));
        }
        if (!isValidPassword(pass)) {
            txPassword.setError(getString(R.string.error_incorrect_password));
        }
        if(isValidLogin(email) && isValidPassword(pass))
        {
            mAuthTask = new UserLoginTask(email, pass);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void addClientPage(View view){
        startActivity(new Intent(LoginActivity.this, CheckInClientActivity_.class));
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Integer> {
        private final String mLogin;
        private final String mPassword;
        private UsersDaoImpl mUserDao;

        UserLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
            mUserDao = new UsersDaoImpl();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            int resultCode = 0;
            UsersEntity entity = mUserDao.read(mLogin);
            if(entity != null && entity.getLogin().equals(mLogin) && entity.getPassword().equals(mPassword)){
            switch (entity.getTypeUser())
            {
                case "Sotrud": {resultCode = CODE_ADMIN_SUCCESFUL;}
                break;
                case "Client": {resultCode = CODE_CLIENT_SUCCESFUL;}
                break;
                }
            }
            return resultCode;
            } catch (InterruptedException e) {
                return -CODE_SING_UP_EXEPTION;
            }
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            switch (success) {
                case CODE_ADMIN_SUCCESFUL:{
                    startActivity(new Intent(LoginActivity.this, AdminActivity_.class));
                    break;
                }
                case CODE_CLIENT_SUCCESFUL:{
                    startActivity(new Intent(LoginActivity.this, ClientActivity_.class));
                    break;
                } default:{
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.set, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(this, Preferences.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

