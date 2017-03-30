package com.example.andy.accountingandcancellationofmedicines.utils;

public class StringsUtil {
    public static final int LENGTH = 1500;

    public static final int REQUEST_READ_CONTACTS = 0;
    public static final int CODE_ADMIN_SUCCESFUL = 1;
    public static final int CODE_CLIENT_SUCCESFUL = 2;
    public static final int CODE_SING_UP_EXEPTION = 2;

    public static boolean isValidLogin(String login) {
        return login != null && login.length() >= 3;
    }

    public static boolean isValidPassword(String pass) {
        return pass != null && pass.length() >= 3;
    }
}
