package com.example.andy.accountingandcancellationofmedicines.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import com.example.andy.accountingandcancellationofmedicines.R;

public final class DialogFactory {

    public static void dialogSearch(Activity activity)
    {
       AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Repeat the find.")
                .setMessage("Nothing was found.")
                .setIcon(R.mipmap.notfound)
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel())
                .setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static Dialog createGenericErrorDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle("Sorry")
                .setMessage(message)
                .setNeutralButton("Okey", null);
        return alertDialog.create();
    }

    public static Dialog restoreAccountDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Restore password.")
                .setMessage("New password sent to your e-mail.")
                .setIcon(R.mipmap.atancher)
                .setPositiveButton("I agree", (dialog, id) -> dialog.cancel())
                .setNegativeButton("I not agree", (dialog, id) -> dialog.cancel())
                .setCancelable(false);
        return builder.create();
    }
}
