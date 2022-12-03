package vn.misa.nadat.cukcuklite.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import vn.misa.nadat.cukcuklite.CukCukLiteApp;

/**
 * Class chứa các phương thức dùng chung
 *
 * @created_by nadat on 4/5/2019
 */
public class Utils {
    /**
     * Lấy ra ảnh bitmap theo tên file ảnh
     *
     * @param fileName tên file trong asset
     * @return ảnh bitmap
     * @created_by nadat on 4/5/2019
     */
    public static Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = CukCukLiteApp.getInstance().getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(Constant.THUMBNAIL_ASSETS + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    /**
     * Phương thức dùng để đọc file Json từ thư mục assets
     *
     * @param context  - Context
     * @param fileName - Tên file
     * @created_by nadat on 15/04/2019
     */
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
