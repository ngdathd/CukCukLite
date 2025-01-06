package com.ngdat.cukcuklite.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Lớp thao tác với SharePreferences
 * Created at 27/03/2019
 */
public class SharedPrefsHelper {
    public static SharedPrefsHelper sInstance;
    private SharedPreferences mSharedPreferences;

    /**
     * Phương thức khởi tạo đối tượng SharedPrefsHelper
     * Created at 27/03/2019
     *
     * @param context - context
     */
    SharedPrefsHelper(Context context) {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    /**
     * Phương thức lấy dữ liệu từ key của file SharePreferences
     * Created at 20/03/2019
     *
     * @param key   - Key của giá trị
     * @param clazz - Lớp/kiểu giá trị của value của key
     * @param <T>   - Kiểu dữ liệu Genneric
     * @return - Dữ liệu kiểu T
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        if (clazz == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (clazz == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (clazz == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (clazz == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        }
        return null;
    }

    /**
     * Phương thức truyền/đặt dữ liệu với key và value vào SharePreferences
     * Created at 20/03/2019
     *
     * @param key  - Key của giá trị
     * @param data - Giá trị của key
     * @param <T>  - Kiểu giá trị
     */
    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    /**
     * Phương thức xóa dữ liệu của SharePreferences File
     * Created at 20/03/2019
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
