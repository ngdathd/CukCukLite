package com.ngdat.cukcuklite.data.local.prefs;

import android.content.Context;

public class SharedPrefersManager {

    public static SharedPrefersManager sInstance;
    private SharedPrefsHelper mSharedPrefsHelper;

    /**
     * Phương thức khởi tạo đối tượng SharedPrefsHelper
     * Created at 27/03/2019
     *
     * @param context - context
     */
    private SharedPrefersManager(Context context) {
        mSharedPrefsHelper = new SharedPrefsHelper(context);
    }

    /**
     * Phương thức khởi tạo và nhận đối tượng SharedPrefersManager
     *
     * @param context
     * @return
     */
    public static synchronized SharedPrefersManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefersManager(context);
        }
        return sInstance;
    }

    /**
     * Phương thức kiểm tra trạng thái đăng nhập của ứng dụng
     * Created at 04/04/2019
     */
    public boolean getIsLoginSuccess() {
        return mSharedPrefsHelper.get(SharedPrefsKey.PREF_IS_LOGIN_SUCCESS, Boolean.class);
    }

    /**
     * Phương thức câp nhật trạng thái đăng nhập của ứng dụng
     * Created at 04/04/2019
     *
     * @param isLoginSuccess - trạng thái đăng nhập
     */
    public void setIsLoginSuccess(Boolean isLoginSuccess) {
        mSharedPrefsHelper.put(SharedPrefsKey.PREF_IS_LOGIN_SUCCESS, isLoginSuccess);
    }

    /**
     * Phương thức câp nhật user đã có dữ liệu hay chưa
     * Created at 04/04/2019
     *
     * @param isAlreadyData - user đã có dữ liệu hay chưa
     */
    public void setAlreadyHasData(boolean isAlreadyData) {
        mSharedPrefsHelper.put(SharedPrefsKey.PREF_IS_ALREADY_HAS_DATA, isAlreadyData);
    }

    /**
     * Phương thức kiểm tra người dùng đã có dữ liệu hay chưa
     * Created at 04/04/2019
     */
    public boolean getIsAlreadyHasData() {
        return mSharedPrefsHelper.get(SharedPrefsKey.PREF_IS_ALREADY_HAS_DATA, Boolean.class);
    }
}
