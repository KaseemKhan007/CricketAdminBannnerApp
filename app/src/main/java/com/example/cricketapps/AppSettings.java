package com.example.cricketapps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

public class AppSettings {

    private static SharedPreferences getPrefs(Context paramContext) {
        return PreferenceManager.getDefaultSharedPreferences(paramContext);
    }

    public static void clearPrefs(Context paramContext) {
        SharedPreferences preferences = getPrefs(paramContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean isLogin(Context paramContext) {
        return getPrefs(paramContext).getBoolean("isLogin", false);
    }

    public static void setLogin(Context paramContext, boolean islogin) {
        getPrefs(paramContext).edit().putBoolean("isLogin", islogin).commit();
    }

    public static boolean isSetProfile(Context paramContext) {
        return getPrefs(paramContext).getBoolean("isSetProfile", false);
    }

    public static String getPassword(Context paramContext) {
        return getPrefs(paramContext).getString("password", null);
    }

    public static void setPassword(Context paramContext, String paramString) {
        getPrefs(paramContext).edit().putString("password", paramString).commit();
    }

    public static String getUId(Context context) {
        return getPrefs(context).getString("UId", "");
    }

    public static void setUId(Context context, String paramString) {
        getPrefs(context).edit().putString("UId", paramString).commit();
    }
}
