package vn.misa.nadat.cucuclite;

import android.app.Application;

public class MvpApp extends Application {
    private static MvpApp mInstance;

    public static MvpApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
