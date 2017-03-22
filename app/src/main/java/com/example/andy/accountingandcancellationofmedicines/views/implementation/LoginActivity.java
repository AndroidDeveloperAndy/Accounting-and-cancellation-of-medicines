package com.example.andy.accountingandcancellationofmedicines.views.implementation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.andy.accountingandcancellationofmedicines.R;
import com.example.andy.accountingandcancellationofmedicines.dao.sqlite.UsersDaoImpl;
import com.example.andy.accountingandcancellationofmedicines.database.DatabaseHelper;
import com.example.andy.accountingandcancellationofmedicines.database.Singleton;
import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;
import com.example.andy.accountingandcancellationofmedicines.settings.Preferences;
import com.example.andy.accountingandcancellationofmedicines.views.interfaces.LoginActivityImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>,LoginActivityImpl {

    private static final int REQUEST_READ_CONTACTS = 0;
    public static final int CODE_ADMIN_SUCCESFUL = 1;
    public static final int CODE_CLIENT_SUCCESFUL = 2;
    public static final int CODE_SING_UP_EXEPTION = 2;

    private UserLoginTask mAuthTask = null;

    @ViewById(R.id.login)            AutoCompleteTextView mLoginView;
    @ViewById(R.id.password)         EditText mPasswordView;
    @ViewById(R.id.login_progress)   View mProgressView;
    @ViewById(R.id.login_form)       View mLoginFormView;
    @ViewById(R.id.imageView)        ImageView iv;
    @ViewById(R.id.sign_in_button)   Button mLoginSignInButton;
    @ViewById(R.id.registration)     Button mLoginCheckInButton;
    @ViewById(R.id.restore_password) Button mLoginRestorePassButton;

    @AfterViews
    public void initPage(){
        Singleton.getInstance(this);
        populateAutoComplete();
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
        mLoginSignInButton.setOnClickListener(view -> attemptLogin());
        mLoginCheckInButton.setOnClickListener(view -> addClientPage());
        mLoginRestorePassButton.setOnClickListener(view -> restoreDialog());
        new DatabaseHelper(this).getWritableDatabase();
    }

    @Override
    public void addClientPage(){
        startActivity(new Intent(LoginActivity.this, CheckInClientActivity.class));
    }

    @Override
    public void restoreDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Restore password.")
                .setMessage("New password sent to your e-mail.")
                .setIcon(R.drawable.atancher)
                .setPositiveButton("I agree", (dialog, id) -> dialog.cancel())
                .setNegativeButton("I not agree", (dialog, id) -> dialog.cancel())
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mLoginView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, v -> requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS));
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        mLoginView.setError(null);
        mPasswordView.setError(null);
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        } else if (!isLoginValid(login)) {
            mLoginView.setError(getString(R.string.error_invalid_login));
            focusView = mLoginView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(login, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isLoginValid(String login) {
        return login.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> logins = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            logins.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addLoginsToAutoComplete(logins);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {}

    private void addLoginsToAutoComplete(List<String> LoginsCollection) {
        mLoginView.setAdapter(new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, LoginsCollection));
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
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
            showProgress(false);
            switch (success) {
                case CODE_ADMIN_SUCCESFUL:{
                    Intent intent = new Intent(LoginActivity.this, StartPageActivity.class);
                    startActivity(intent);
                    break;
                }
                case CODE_CLIENT_SUCCESFUL:{
                    Intent intent = new Intent(LoginActivity.this, ClientActivity.class);
                    startActivity(intent);
                    break;
                } default:{
                    mPasswordView.setError(getString(R.string.error));
                    mPasswordView.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
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

