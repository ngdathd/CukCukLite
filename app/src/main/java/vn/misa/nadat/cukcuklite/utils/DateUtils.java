package vn.misa.nadat.cukcuklite.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Tiện ích xử lý date
 *
 * @created_by nadat on 24/04/2019
 */
public class DateUtils {
    private static DateUtils sInstance;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;

    /**
     * Khởi tạo DateUtils
     *
     * @created_by nadat on 24/04/2019
     */
    @SuppressLint("SimpleDateFormat")
    private DateUtils() {
        try {
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy ra đối tượng DateUtils
     *
     * @return DateUtils
     * @created_by nadat on 24/04/2019
     */
    public static DateUtils getInstance() {
        if (sInstance == null) {
            sInstance = new DateUtils();
        }

        return sInstance;
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của ngày hôm qua
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getYesterday() {
        try {
            String[] date = new String[2];

            mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DATE, -1);

            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            date[0] = mDateFormat.format(mCalendar.getTime());

            mCalendar.set(Calendar.HOUR_OF_DAY, 23);
            mCalendar.set(Calendar.MINUTE, 59);
            mCalendar.set(Calendar.SECOND, 59);
            date[1] = mDateFormat.format(mCalendar.getTime());

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của ngày hôm nay
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getToday() {
        try {
            String[] date = new String[2];

            mCalendar = Calendar.getInstance();
            date[1] = mDateFormat.format(mCalendar.getTime());

            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            date[0] = mDateFormat.format(mCalendar.getTime());

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của tuần này
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getThisWeek() {
        try {
            String[] date = new String[2];

            mCalendar = Calendar.getInstance();
            date[1] = mDateFormat.format(mCalendar.getTime());

            mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            date[0] = mDateFormat.format(mCalendar.getTime());

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của tuần trước
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getLastWeek() {
        try {
            String[] date = new String[2];
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date fromDate = calendar.getTime();
            date[0] = mDateFormat.format(fromDate);
            calendar.add(Calendar.DATE, 6);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date toDate = calendar.getTime();
            date[1] = mDateFormat.format(toDate);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của tháng này
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getThisMonth() {
        try {
            String[] date = new String[2];

            mCalendar = Calendar.getInstance();
            date[1] = mDateFormat.format(mCalendar.getTime());

            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            date[0] = mDateFormat.format(mCalendar.getTime());

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của tháng trước
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getLastMonth() {
        try {
            String[] date = new String[2];

            mCalendar = Calendar.getInstance();

            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.add(Calendar.DAY_OF_YEAR, -1);
            date[1] = mDateFormat.format(mCalendar.getTime());

            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            date[0] = mDateFormat.format(mCalendar.getTime());

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của năm nay
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getThisYear() {
        try {
            String[] date = new String[2];

            mCalendar = Calendar.getInstance();
            date[1] = mDateFormat.format(mCalendar.getTime());

            mCalendar.set(Calendar.DAY_OF_YEAR, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            date[0] = mDateFormat.format(mCalendar.getTime());

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thời điểm đầu tiên và cuối cùng của năm trước
     *
     * @return mảng chứa thời điểm
     * @created_by nadat on 24/04/2019
     */
    public String[] getLastYear() {
        try {
            String[] date = new String[2];

            mCalendar = Calendar.getInstance();

            mCalendar.set(Calendar.DAY_OF_YEAR, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.add(Calendar.DAY_OF_YEAR, -1);
            date[1] = mDateFormat.format(mCalendar.getTime());

            mCalendar.set(Calendar.DAY_OF_YEAR, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            date[0] = mDateFormat.format(mCalendar.getTime());

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Chuyển đổi ngày dạng String sang Date
     *
     * @param date ngày dạng String
     * @return ngày dạng Date
     * @created_by nadat on 24/04/2019
     */

    public Date getDate(String date) {
        try {
            return mDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ngày tiếp theo
     *
     * @param date ngày đã xác định
     * @return ngày tiếp theo dạng Date
     * @created_by nadat on 24/04/2019
     */
    public Date nextDay(Date date) {
        try {
            mCalendar.setTime(date);
            mCalendar.add(Calendar.DAY_OF_YEAR, 1);
            return mCalendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy tháng tiếp theo
     *
     * @param date ngày đã xác định
     * @return tháng tiếp theo dạng Date
     * @created_by nadat on 24/04/2019
     */
    public Date nextMonth(Date date) {
        try {
            mCalendar.setTime(date);
            mCalendar.add(Calendar.MONTH, 1);
            return mCalendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ngày đầu tiên của tháng hiện tại
     *
     * @return ngày đầu tháng
     * @created_by nadat on 24/04/2019
     */
    public Date getStartMonth() {
        try {
            mCalendar = Calendar.getInstance();

            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);

            return mCalendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ngày đầu tiên của tháng trong mốc thời gian
     *
     * @return ngày đầu tháng
     * @created_by nadat on 24/04/2019
     */
    public Date getStartMonth(String date) {
        try {
            Date day = mDateFormat.parse(date);
            mCalendar = Calendar.getInstance();
            mCalendar.setTime(day);

            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);

            return mCalendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ngày cuối cùng của tháng hiện tại
     *
     * @return ngày cuối tháng
     * @created_by nadat on 24/04/2019
     */
    public Date getEndMonth() {
        try {
            mCalendar = Calendar.getInstance();

            mCalendar.add(Calendar.MONTH, 1);
            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 23);
            mCalendar.set(Calendar.MINUTE, 59);
            mCalendar.set(Calendar.SECOND, 59);
            mCalendar.add(Calendar.DAY_OF_YEAR, -1);

            return mCalendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ngày cuối cùng của tháng trong mốc thời gian
     *
     * @return ngày cuối tháng
     * @created_by nadat on 24/04/2019
     */
    public Date getEndMonth(String date) {
        try {
            Date day = mDateFormat.parse(date);
            mCalendar = Calendar.getInstance();
            mCalendar.setTime(day);

            mCalendar.add(Calendar.MONTH, 1);
            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            mCalendar.set(Calendar.HOUR_OF_DAY, 23);
            mCalendar.set(Calendar.MINUTE, 59);
            mCalendar.set(Calendar.SECOND, 59);
            mCalendar.add(Calendar.DAY_OF_YEAR, -1);

            return mCalendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ngày trong mốc thời gian
     *
     * @param date mốc thời gian
     * @return ngày
     * @created_by nadat on 24/04/2019
     */
    public int getDayOfMonth(String date) {
        try {
            mCalendar = Calendar.getInstance();

            Date day = mDateFormat.parse(date);
            mCalendar.setTime(day);

            return mCalendar.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Lấy tháng trong mốc thời gian
     *
     * @param date mốc thời gian
     * @return tháng
     * @created_by nadat on 24/04/2019
     */
    public int getMonth(String date) {
        try {
            mCalendar = Calendar.getInstance();

            Date day = mDateFormat.parse(date);
            mCalendar.setTime(day);

            return mCalendar.get(Calendar.MONTH);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Lấy năm trong mốc thời gian
     *
     * @param date mốc thời gian
     * @return năm
     * @created_by nadat on 24/04/2019
     */
    public int getYear(String date) {
        try {
            mCalendar = Calendar.getInstance();

            Date day = mDateFormat.parse(date);
            mCalendar.setTime(day);

            return mCalendar.get(Calendar.YEAR);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Thiết lập lại môc thời gian
     *
     * @param date       mốc thời gian muốn đổi
     * @param year       năm sau khi đổi
     * @param month      tháng sau khi đổi
     * @param dayOfMonth ngày sau khi đổi
     * @return mốc thời gian sau khi đổi
     * @created_by nadat on 24/04/2019
     */
    public String setDate(String date, int year, int month, int dayOfMonth) {
        try {
            mCalendar = Calendar.getInstance();

            Date day = mDateFormat.parse(date);
            mCalendar.setTime(day);

            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            return mDateFormat.format(mCalendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
