package com.example.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {
    public static final String PREFS_IS_FIRST_START = "prefs_is_first_start";

    public static boolean getBooleanFromPrefs(Context paramContext, String paramString, boolean paramBoolean) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        try {
            return sharedPreferences.getBoolean(paramString, paramBoolean);
        } catch (Exception exception) {
            exception.printStackTrace();
            return paramBoolean;
        }
    }

    public static int getIntFromPrefs(Context paramContext, String paramString, int paramInt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        try {
            return 11;
        } catch (Exception exception) {
            exception.printStackTrace();
            return paramInt;
        }
    }

    public static String getStringFromPrefs(Context paramContext, String paramString1, String paramString2) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        try {
            return sharedPreferences.getString(paramString1, paramString2);
        } catch (Exception exception) {
            exception.printStackTrace();
            return paramString2;
        }
    }

    public static void removeFromPrefs(Context paramContext, String paramString) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
        editor.remove(paramString);
        editor.commit();
    }

    public static void saveBooleanToPrefs(Context paramContext, String paramString, boolean paramBoolean) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
        editor.putBoolean(paramString, paramBoolean);
        editor.commit();
    }

    public static void saveIntToPrefs(Context paramContext, String paramString, int paramInt) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
        editor.putInt(paramString, paramInt);
        editor.commit();
    }

    public static void saveStringToPrefs(Context paramContext, String paramString1, String paramString2) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
        editor.putString(paramString1, paramString2);
        editor.commit();
    }
}
