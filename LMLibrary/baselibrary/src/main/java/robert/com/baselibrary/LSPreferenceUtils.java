package robert.com.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class LSPreferenceUtils {

    public static String getPrefString(Context context, String key,
                                       final String defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key,
                                     final String value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        // settings.edit().putString(key, value).commit();
        Editor editor = settings.edit();
        editor.putString(key, value);
        LSSharedPreferencesCompat.apply(editor);
    }

    public static boolean getPrefBoolean(Context context, final String key,
                                         final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(
                key);
    }

    public static void setPrefBoolean(Context context, final String key,
                                      final boolean value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        // settings.edit().putBoolean(key, value).commit();
        Editor editor = settings.edit();
        editor.putBoolean(key, value);
        LSSharedPreferencesCompat.apply(editor);
    }

    public static void setPrefInt(Context context, final String key,
                                  final int value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        //	settings.edit().putInt(key, value).commit();
        Editor editor = settings.edit();
        editor.putInt(key, value);
        LSSharedPreferencesCompat.apply(editor);
    }

    public static int getPrefInt(Context context, final String key,
                                 final int defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }


    public static void setPrefFloat(Context context, final String key,
                                    final float value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        //	settings.edit().putFloat(key, value).commit();
        Editor editor = settings.edit();
        editor.putFloat(key, value);
        LSSharedPreferencesCompat.apply(editor);
    }

    public static float getPrefFloat(Context context, final String key,
                                     final float defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setPreLong(Context context, final String key,
                                  final long value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        //	settings.edit().putLong(key, value).commit();
        Editor editor = settings.edit();
        editor.putLong(key, value);
        LSSharedPreferencesCompat.apply(editor);
    }

    public static long getPrefLong(Context context, final String key,
                                   final long defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context,
                                       final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear().commit();
//        	editor.commit();
//        LRCSharedPreferencesCompat.apply(editor);
    }

    //添加一个value
    public static void setStringList(Context context, final String key,
                                     final String value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        Set<String> list = new HashSet<>();
        list = settings.getStringSet(key, list);
        list.add(value);
        Editor editor = settings.edit();
        editor.putStringSet(key, list);
        LSSharedPreferencesCompat.apply(editor);

    }

    //获取StringSet
    public static Set<String> getStringList(Context context, final String key) {
        Set<String> list = new HashSet<>();
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getStringSet(key, list);
    }

    //清空StringSet
    public static void clearStringList(Context context, final String key) {
        Set<String> list = new HashSet<>();
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = settings.edit();
        editor.putStringSet(key, list);
        LSSharedPreferencesCompat.apply(editor);
    }
}
