package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju;

import com.renyisjerunwetry.ewtfhweryy.RenYiHsdyjurnApp;

public class SPRenYiHsdyjurnUtil {

    public static void saveInt(String key, int value) {
        RenYiHsdyjurnApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return RenYiHsdyjurnApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        RenYiHsdyjurnApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        RenYiHsdyjurnApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return RenYiHsdyjurnApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return RenYiHsdyjurnApp.getPreferences().getBoolean(key, false);
    }

}
