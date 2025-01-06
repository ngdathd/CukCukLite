package com.ngdat.cukcuklite.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lớp tiện ích cho ứng dụng
 * Created at 27/03/2019
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";

    /**
     * Phương thức lấy danh sách tên file ảnh từ thư mục assets
     * Created at 27/03/2019
     *
     * @param context - context
     * @return - danh sách tên file ảnh
     */
    public static List<String> getAllImage(Context context) {
        String[] images;
        try {
            images = context.getAssets().list("icondefault");
            return new ArrayList<>(Arrays.asList(images));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức lấy 1 drawabel từ tên file ảnh trong thư mục assets
     * Created at 20/03/2019
     *
     * @param context  - context
     * @param fileName - tên file ảnh
     * @return - drawable
     */
    public static Drawable getDrawableFromImageAssets(Context context, String fileName) {
        try {
            InputStream inputstream = context.getAssets().open("icondefault/"
                    + fileName);
            return Drawable.createFromStream(inputstream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
