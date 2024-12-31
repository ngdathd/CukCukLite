package com.ngdat.cukcuklite.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Lớp tiện ích xử lý ngày tháng
 * Created at 19/04/2019
 */
public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Phương thức lấy miliseconds từ 1 string datetime
     * Created at 19/04/2019
     *
     * @param dateString - string dạng datetime
     * @return - số miliseconds
     */
    public static long getTimeMilisecondsFromDateString(String dateString) {
        try {
            return dateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    /**
     * Phương thức format date
     * Created at 19/04/2019
     *
     * @param date - date
     * @return - string date
     */
    public static String getDateFormat(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Phương thức lấy ra 2 ngày. 1 ngày là đầu tuần, 1 ngày là thời điểm ngày hiện tại
     * Created at 19/04/2019
     *
     * @return - mảng 2 ngày
     */
    public static Date[] getThisWeek() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Phương thức lấy khoảng thời gian từ đầu ngày(0h:00 -> hiện tại)
     * Created at 19/04/2019
     *
     * @return - khoảng thời gian.
     */
    public static Date[] getToday() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Lấy khoảng thời gian từ đầu năm tới thời điểm hiện tại
     * Created at 19/04/2019
     *
     * @return - khoảng thời gian
     */
    public static Date[] getThisYear() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Lấy khoảng thời gian của ngày hôm qua
     * Created at 19/04/2019
     *
     * @return - khoảng thời gian
     */
    public static Date[] getYesterday() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Lấy khoảng thời gian từ đầu tháng tới ngày hiện tại
     * Created at 19/04/2019
     *
     * @return - khoảng thời gian
     */
    public static Date[] getThisMonth() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Lấy khoảng thời gian của 1 ngày cụ thẻ
     * Created at 19/04/2019
     *
     * @param dayOfMonth  - ngày trong tháng
     * @param monthOfYear - tháng trong năm
     * @param year        - năm
     * @return - khoảng thời gian
     */
    public static Date[] getDate(int dayOfMonth, int monthOfYear, int year) {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Lấy khoảng thời gian trong tuần trước
     * Created at 19/04/2019
     *
     * @return - khoảng thời gian
     */
    public static Date[] getLastWeek() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Lấy khoảng thời gian trong năm ngoái
     * Created at 19/04/2019
     *
     * @return - khoảng thời gian
     */
    public static Date[] getLastYear() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.add(Calendar.YEAR, +1);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }

    /**
     * Lấy khoảng thời gian trong tháng trước
     * Created at 19/04/2019
     *
     * @return - khoảng thời gian
     */
    public static Date[] getLastMonth() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        dateArr[0] = fromDate;
        dateArr[1] = toDate;
        return dateArr;
    }
}