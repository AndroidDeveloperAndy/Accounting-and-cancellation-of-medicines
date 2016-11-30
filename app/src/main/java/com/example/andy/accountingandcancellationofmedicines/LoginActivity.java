package com.example.andy.accountingandcancellationofmedicines;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.accountingandcancellationofmedicines.database.Singl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "admin", "admin"
    };
    private static final String[] CLIENT_SING = new String[]{
            "user", "users"
    };
    public static final int CODE_ADMIN_SUCCESFUL = 1;
    public static final int CODE_CLIENT_SUCCESFUL = 2;
    public static final int CODE_SING_UP_EXEPTION = 2;
    private static final String LOG = LoginActivity.class.getName();
    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Singl.getInstance(getApplicationContext());
        mLoginView = (AutoCompleteTextView) findViewById(R.id.login);
        populateAutoComplete();
        iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.welcome);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mLoginSignInButton = (Button) findViewById(R.id.sign_in_button);
        mLoginSignInButton.setBackgroundColor(Color.rgb(98,99,155));
        mLoginSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });
        Button mLoginCheckInButton = (Button) findViewById(R.id.registration);
        mLoginCheckInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CheckInClientActivity.class);
                startActivity(intent);
            }
        });
        Button mLoginRestorePassButton = (Button) findViewById(R.id.restore_password);
        mLoginRestorePassButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Restore password.")
                        .setMessage("New password sent to your e-mail.")
                        .setIcon(R.drawable.atancher)
                        .setPositiveButton("I agree",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                        builder.setNegativeButton("I not agree",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                builder.setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        DatabaseHelper database = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase data = database.getWritableDatabase();

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
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void attemptLogin() {
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
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
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
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addLoginsToAutoComplete(List<String> LoginsCollection) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, LoginsCollection);

        mLoginView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mLogin;
        private final String mPassword;

        UserLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return -CODE_SING_UP_EXEPTION;
            }

            if(DUMMY_CREDENTIALS[0].equals(mLogin) &&
                    DUMMY_CREDENTIALS[1].equals(mPassword)){
                return CODE_ADMIN_SUCCESFUL;
            }
            else if(CLIENT_SING[0].equals(mLogin) &&
                    CLIENT_SING[1].equals(mPassword)){
                return CODE_CLIENT_SUCCESFUL;
            }
            else
            return 0;
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
                    Intent intent = new Intent(LoginActivity.this, ClientInterfaceActivity.class);
                    startActivity(intent);
                    break;
                } default:{
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
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
}

