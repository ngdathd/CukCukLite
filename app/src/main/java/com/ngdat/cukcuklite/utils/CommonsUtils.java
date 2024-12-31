package com.ngdat.cukcuklite.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lớp chứa các phương thức tiện ích cho ứng dụng
 * Created at 02/04/2019
 */
public final class CommonsUtils {

    public static boolean isNotEmpty(EditText editText) {
        if (0 < editText.getText().toString().trim().length()) {
            return true;
        } else {
            editText.requestFocus();
            editText.setError("Vui lòng điền thông tin!");
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "[a-zA-Z0-9._-]+@[a-z]+(\\.+[a-z]+)+";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    private static final String[] DAY_OF_WEEK = {"Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"};

    /**
     * Phương thức xử lý: Lấy định dạng ngày trong tuần từ số
     * Created at 25/04/2019
     *
     * @return Ngày trong tuần đã đổi sang dạng chuỗi
     */
    public static String getDayOfWeekString(int day) {
        if (day < 0 || day > DAY_OF_WEEK.length) {
            return "";
        }
        return DAY_OF_WEEK[day];
    }

    /**
     * Phương thức sinh hash key cho ứng dụng
     *
     * @param context - context ứng dụng
     * @return - hash key
     */
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    /**
     * Phương thức lấy json string từ đường dẫn file
     * Created at 05/04/2019
     *
     * @param context  - context ứng dụng
     * @param filePath - đường dẫn file
     * @return - chuỗi json
     */
    public static String loadJSONFromAsset(Context context, String filePath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    /**
     * Phương thức lấy danh sách đối tượng từ chuỗi json
     * Created at 05/04/2019
     *
     * @param context - context ứng dụng
     * @param json    - chuỗi json
     * @return - danh sách đối tượng
     */
    public static List<Object> getListObjectFromJsonString(Context context, String json) {
        String jsonRestaurantType = CommonsUtils.loadJSONFromAsset(context, json);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Object>>() {
        }.getType();
        return gson.fromJson(jsonRestaurantType, collectionType);
    }

    /**
     * Phương thức kiểm tra tra có kết nối mạng không
     * Created at 25/04/2019
     *
     * @return - thiết bị đang có kết nối mạng hay không
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
