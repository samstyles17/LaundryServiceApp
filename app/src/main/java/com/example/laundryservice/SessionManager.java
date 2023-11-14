package com.example.laundryservice;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager
{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "MySession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

//    public SessionManager(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
//        this.sharedPreferences = sharedPreferences;
//        this.editor = editor;
//    }

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
