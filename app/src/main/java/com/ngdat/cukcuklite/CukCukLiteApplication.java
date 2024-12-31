package com.ngdat.cukcuklite;

import static com.ngdat.cukcuklite.data.local.database.IDBUtils.DB_NAME;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.ngdat.cukcuklite.data.local.database.SQLiteDBManager;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CukCukLiteApplication extends Application {

    private static CukCukLiteApplication sInstance;

    /**
     * Phương thức láy context app
     * Created at 15/04/2019
     *
     * @return - app context
     */
    public static CukCukLiteApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            sInstance = this;
            SQLiteDBManager.getInstance(this);
            initDatabaseStructure();
            //cho phép đặt nguồn ảnh là vector
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            //đặt font chữ mặc định cho thư viện
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(getString(R.string.nunito_regular))
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo các database cho ứng dụng
     * Created at 02/04/2019
     */
    private void initDatabaseStructure() {
        try {
            File file = this.getDatabasePath(DB_NAME);
            if (!file.exists()) {
                SQLiteDBManager manager = SQLiteDBManager.getInstance();
                manager.getWritableDatabase();
                manager.close();
                manager.copyDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
