package vn.misa.nadat.cukcuklite.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @created_by nadat on 17/04/2019
 */
public class PriceUtils {
    /**
     * Thêm dấu . vào giá tiền
     *
     * @param price: giá tiền có dạng số nguyên
     * @return giá tiền có dấu .
     * @created_by nadat on 24/04/2019
     */
    public static String formatPrice(String price) {
        try {
            if (!TextUtils.isEmpty(price)) {
                StringBuilder result = new StringBuilder();
                String first = price.substring(0, 1);
                int check = 0;

                if (first.equals("-")) {
                    check = 1;
                    price = price.replace("-", "");
                }

                List<String> listPrice = new ArrayList<>();

                String[] prices = price.split("");
                Collections.addAll(listPrice, prices);
                listPrice.remove(0);
                Collections.reverse(listPrice);

                for (int i = 3; i < listPrice.size(); i += 4) {
                    listPrice.add(i, ".");
                }

                for (int i = listPrice.size() - 1; i >= 0; i--) {
                    result.append(listPrice.get(i));
                }

                if (check == 1) {
                    result.insert(0, "-");
                }

                return result.toString();
            }

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Xóa dấu . khỏi giá
     *
     * @param price: giá có dấu chấm
     * @return giá không có dấu chấm
     * @created_by nadat on 24/04/2019
     */
    public static String formatNumber(String price) {
        return price.replace(".", "");
    }

    /**
     * Đưa giá từ String sang int
     *
     * @param price: giá dạng String
     * @return giá là số
     * @created_by nadat on 24/04/2019
     */
    public static int formatPriceToInt(String price) {
        return Integer.parseInt(formatNumber(price));
    }
}
