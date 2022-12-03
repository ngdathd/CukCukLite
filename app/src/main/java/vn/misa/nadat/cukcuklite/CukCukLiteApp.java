package vn.misa.nadat.cukcuklite;

import android.app.Application;

import java.io.File;

import vn.misa.nadat.cukcuklite.database.DBHelper;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Class extends Application được chạy vào khi bắt đầu ứng dụng.
 *
 * @created_by nadat on 15/04/2019
 */
public class CukCukLiteApp extends Application {
    private static CukCukLiteApp mInstance;

    public static CukCukLiteApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    private void initDatabaseStructure() {
        try {
            File file = this.getDatabasePath(Constant.DB_NAME);
            if (!file.exists()) {
                DBHelper manager = DBHelper.getInstance();
                manager.getWritableDatabase();
                manager.close();
                manager.copyDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
