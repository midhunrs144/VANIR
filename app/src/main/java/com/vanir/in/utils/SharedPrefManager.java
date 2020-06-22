package com.vanir.in.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String PREFERENCES = "app_preferences";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void removePreference(Context context, String preferenceName) {
        getEditor(context).remove(preferenceName);
    }



    public static String loadString(Context context, String preferenceName, String defValue) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getString(preferenceName, defValue);
    }

    public static void saveString(Context context, String preferenceName, String value) {
        getEditor(context).putString(preferenceName, value).apply();
    }

    public static int loadInt(Context context, String preferenceName, int defValue) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getInt(preferenceName, defValue);
    }

    public static void saveInt(Context context, String preferenceName, int value) {
        getEditor(context).putInt(preferenceName, value).apply();
    }

    public static long loadLong(Context context, String preferenceName, long defValue) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getLong(preferenceName, defValue);
    }

    public static void saveLong(Context context, String preferenceName, long value) {
        getEditor(context).putLong(preferenceName, value).apply();
    }

    public static boolean loadBoolean(Context context, String preferenceName, boolean defValue) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getBoolean(preferenceName, defValue);
    }

    public static void saveBoolean(Context context, String preferenceName, boolean value) {
        getEditor(context).putBoolean(preferenceName, value).apply();
    }

/////////
   public static void saveToken(Context context, String token) {
    SharedPreferences sharedPreferences = getPreferences(context);
    saveString(context, "token", token);
}

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getString("token", "");
    }

}

