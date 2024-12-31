package com.ngdat.cukcuklite.data.local.report;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ngdat.cukcuklite.data.cukcukenum.ParamReportEnum;
import com.ngdat.cukcuklite.data.cukcukenum.ReportTotalEnum;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.data.local.database.SQLiteDBManager;
import com.ngdat.cukcuklite.data.models.ReportCurrent;
import com.ngdat.cukcuklite.data.models.ReportDetail;
import com.ngdat.cukcuklite.data.models.ReportTotal;
import com.ngdat.cukcuklite.utils.DateUtil;

/**
 * Class xử lý các truy vấn lấy báo cáo thống kê
 * <p>
 * Created at 18/04/2019
 */
public class ReportDataSource implements IDBUtils.ITableBillDetailUtils, IDBUtils.ITableBillUtils, IReportDataSource {
    private static final String TAG = "ReportDataSource";

    /**
     * Phương thức xử lý: Bóc dữ liệu báo cáo theo từng ngày
     * Created at 18/04/2019
     *
     * @param cursor - giá trị đối tượng lấy từ sqlite
     * @return đối tượng báo cáo từng ngày
     */
    private static ReportTotal parseCursorToReportTotal(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            return new ReportTotal.Builder()
                    .setAmount(cursor.getColumnIndex("totalMoney") > -1 ? (long) cursor.getInt(cursor.getColumnIndex("totalMoney")) : 0)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Bóc dữ liệu báo cáo theo từng ngày
     * Created at 18/04/2019
     *
     * @param cursor - giá trị đối tượng lấy từ sqlite
     * @return đối tượng báo cáo từng ngày
     */
    private static ReportDetail parseCursorToReportDetail(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            return new ReportDetail.Builder()
                    .setDishId(cursor.getColumnIndex(COLUMN_DISH_ID) > -1 ? cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID)) : "")
                    .setDateCreated(cursor.getColumnIndex(COLUMN_DATE_CREATED) > -1 ? cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CREATED)) : "")
                    .setQuantity(cursor.getColumnIndex("quantity") > -1 ? cursor.getInt(cursor.getColumnIndex("quantity")) : 0)
                    .setAmount(cursor.getColumnIndex("totalMoney") > -1 ? (long) cursor.getInt(cursor.getColumnIndex("totalMoney")) : 0)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong năm nay
     * Created at 18/04/2019
     *
     * @return Giá trị tổng doanh thu trong năm nay
     */
    private static int getReportTotalMoneyThisYear() {
        try {
            Date[] dates = DateUtil.getThisYear();
            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + DateUtil.getDateFormat(dates[0]) + "'" + " AND " + "'" + DateUtil.getDateFormat(dates[1]) + "'";
            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong tháng này
     * Created at 18/04/2019
     *
     * @return Giá trị tổng doanh thu trong tháng này
     */
    private static int getReportTotalMoneyThisMonth() {
        try {
            Date[] dates = DateUtil.getThisMonth();
            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'"
                    + DateUtil.getDateFormat(dates[0]) + "'" + " AND " + "'" + DateUtil.getDateFormat(dates[1]) + "'";
            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong tuần này
     * Created at 18/04/2019
     *
     * @return Giá trị tổng doanh thu trong tuần này
     */
    private static int getReportTotalMoneyThisWeek() {
        try {
            Date[] dates = DateUtil.getThisWeek();
            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'"
                    + DateUtil.getDateFormat(dates[0]) + "'" + " AND " + "'" + DateUtil.getDateFormat(dates[1]) + "'";
            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong hôm nay
     * Created at 18/04/2019
     *
     * @return Giá trị tổng doanh thu trong hôm nay
     */
    private static int getReportTotalMoneyToday() {
        try {
            Date[] dates = DateUtil.getToday();
            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " = " + "'"
                    + DateUtil.getDateFormat(dates[0]) + "'";
            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong hôm qua
     * Created at 18/04/2019
     *
     * @return Giá trị tổng doanh thu trong hôm qua
     */
    private static int getReportTotalMoneyYesterday() {
        try {
            Date[] dates = DateUtil.getYesterday();
            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " = " +
                    "'" + DateUtil.getDateFormat(dates[0]) + "'";
            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết hôm nay
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public ArrayList<ReportDetail> getReportToday() {
        Date[] dates = DateUtil.getToday();
        try {
            return getReportDetailDate(DateUtil.getDateFormat(dates[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết hôm qua
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public ArrayList<ReportDetail> getReportYeaterday() {
        Date[] dates = DateUtil.getYesterday();
        try {
            return getReportDetailDate(DateUtil.getDateFormat(dates[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tuần này
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public List<ReportTotal> getReportThisWeek() {
        Date[] dates = DateUtil.getThisWeek();
        try {
            Calendar calendar = Calendar.getInstance();
            List<ReportTotal> reportTotals = new ArrayList<>();
            calendar.setTime(dates[0]);
            while (calendar.getTime().compareTo(dates[1]) < 0) {
                ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.WEEK);
                Date from = calendar.getTime();
                if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                    reportTotal.setTitleReportDetail("CN");
                } else {
                    reportTotal.setTitleReportDetail("Thứ " + calendar.get(Calendar.DAY_OF_WEEK));
                }
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.SECOND, -1);

                Date to = calendar.getTime();
                List<ReportDetail> reportDetails = getReportDetailDate(DateUtil.getDateFormat(from));
                if (reportDetails != null && reportDetails.size() > 0) {
                    reportTotal.setAmount(this.getAmountDetail(reportDetails));
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                } else {
                    reportTotal.setAmount(0);
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                }
                calendar.add(Calendar.SECOND, 1);
            }
            return reportTotals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tuần trước
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public List<ReportTotal> getReportLastWeek() {
        Log.d(TAG, "getReportLastWeek: ");
        Date[] dates = DateUtil.getLastWeek();
        try {
            Calendar calendar = Calendar.getInstance();
            List<ReportTotal> reportTotals = new ArrayList<>();
            calendar.setTime(dates[0]);
            while (calendar.getTime().compareTo(dates[1]) < 0) {
                ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.WEEK);
                Date from = calendar.getTime();
                if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                    reportTotal.setTitleReportDetail("CN");
                } else {
                    reportTotal.setTitleReportDetail("Thứ " + calendar.get(Calendar.DAY_OF_WEEK));
                }
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.SECOND, -1);

                Date to = calendar.getTime();
                List<ReportDetail> reportDetails = getReportDetailDate(DateUtil.getDateFormat(from));
                if (reportDetails != null && reportDetails.size() > 0) {
                    reportTotal.setAmount(this.getAmountDetail(reportDetails));
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                } else {
                    reportTotal.setAmount(0);
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                }
                calendar.add(Calendar.SECOND, 1);
            }
            Log.d(TAG, "getReportLastWeek: " + reportTotals.size());
            return reportTotals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tháng này
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public List<ReportTotal> getReportThisMonth() {
        Date[] dates = DateUtil.getThisMonth();
        try {
            Calendar calendar = Calendar.getInstance();
            List<ReportTotal> reportTotals = new ArrayList<>();
            calendar.setTime(dates[0]);

            while (calendar.getTime().compareTo(dates[1]) < 0) {
                ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.MONTH);
                Date from = calendar.getTime();
                reportTotal.setTitleReportDetail("Ngày " + calendar.get(Calendar.DAY_OF_MONTH));

                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.SECOND, -1);

                Date to = calendar.getTime();

                List<ReportDetail> reportDetails = getReportDetailDate(DateUtil.getDateFormat(from));
                if (reportDetails != null && reportDetails.size() > 0) {
                    reportTotal.setAmount(this.getAmountDetail(reportDetails));
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                } else {
                    reportTotal.setAmount(0);
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                }
                calendar.add(Calendar.SECOND, 1);
            }
            return reportTotals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tháng trước
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public List<ReportTotal> getReportLastMonth() {
        Date[] dates = DateUtil.getLastMonth();
        try {
            Calendar calendar = Calendar.getInstance();
            List<ReportTotal> reportTotals = new ArrayList<>();
            calendar.setTime(dates[0]);

            while (calendar.getTime().compareTo(dates[1]) < 0) {
                ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.MONTH);
                Date from = calendar.getTime();
                reportTotal.setTitleReportDetail("Ngày " + calendar.get(Calendar.DAY_OF_MONTH));

                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.SECOND, -1);

                Date to = calendar.getTime();

                List<ReportDetail> reportDetails = getReportDetailDate(DateUtil.getDateFormat(from));
                if (reportDetails != null && reportDetails.size() > 0) {
                    reportTotal.setAmount(this.getAmountDetail(reportDetails));
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                } else {
                    reportTotal.setAmount(0);
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                }
                calendar.add(Calendar.SECOND, 1);
            }
            return reportTotals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết năm nay
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public List<ReportTotal> getReportThisYear() {
        Date[] dates = DateUtil.getThisYear();
        try {
            Calendar calendar = Calendar.getInstance();
            List<ReportTotal> reportTotals = new ArrayList<>();
            calendar.setTime(dates[0]);

            while (calendar.getTime().compareTo(dates[1]) < 0) {
                ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.YEAR);
                Date from = calendar.getTime();

                reportTotal.setTitleReportDetail("Tháng " + (calendar.get(Calendar.MONTH) + 1));
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.SECOND, -1);

                Date to = calendar.getTime();

                List<ReportTotal> reportTotals1 = getReportByDate(DateUtil.getDateFormat(from), DateUtil.getDateFormat(to));

                if (reportTotals1 != null && reportTotals1.size() > 0) {
                    reportTotal.setAmount(getAmountTotal(reportTotals1));
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                } else {
                    reportTotal.setAmount(0);
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                }
                calendar.add(Calendar.SECOND, 1);
            }
            return reportTotals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức tính tổng tiền của báo cáo tổng thể
     * Created at 18/04/2019
     *
     * @param reporTotals - danh sách báo cáo chi tiết
     * @return - tổng tiền
     */
    private long getAmountTotal(List<ReportTotal> reporTotals) {
        try {
            long total = 0;
            for (ReportTotal report : reporTotals) {
                total += report.getAmount();
            }
            return total;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Phương thức tính tổng tiền của báo cáo chi tiết
     * Created at 18/04/2019
     *
     * @param reportDetails - danh sách báo cáo tổng thể
     * @return - tổng tiền
     */
    private long getAmountDetail(List<ReportDetail> reportDetails) {
        try {
            long total = 0;
            for (ReportDetail report : reportDetails) {
                total += report.getAmount();
            }
            return total;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết năm trước
     * Created at 18/04/2019
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     */
    @Override
    public List<ReportTotal> getReportLastYear() {
        Date[] dates = DateUtil.getLastYear();
        try {
            Calendar calendar = Calendar.getInstance();
            List<ReportTotal> reportTotals = new ArrayList<>();
            calendar.setTime(dates[0]);

            while (calendar.getTime().compareTo(dates[1]) < 0) {
                ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.YEAR);
                Date from = calendar.getTime();

                reportTotal.setTitleReportDetail("Tháng " + (calendar.get(Calendar.MONTH) + 1));
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.SECOND, -1);

                Date to = calendar.getTime();

                List<ReportTotal> reportTotals1 = getReportByDate(DateUtil.getDateFormat(from), DateUtil.getDateFormat(to));

                if (reportTotals1 != null && reportTotals1.size() > 0) {
                    reportTotal.setAmount(getAmountTotal(reportTotals1));
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                } else {
                    reportTotal.setAmount(0);
                    reportTotal.setFromDate(from);
                    reportTotal.setToDate(to);
                    reportTotals.add(reportTotal);
                }
                calendar.add(Calendar.SECOND, 1);
            }
            return reportTotals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo theo khoảng thời gian
     * Created at 18/04/2019
     *
     * @param startDate - ngày giờ bắt đầu
     * @param endDate   - ngày giờ kết thúc
     * @return - danh sách báo cáo thống kê theo từng ngày
     */
    @Override
    public List<ReportTotal> getReportByDate(String startDate, String endDate) {
        try {
            List<ReportTotal> list = new ArrayList<>();

            String sql = String.format("SELECT %s,%s,sum(%s) as quantity,sum(%s.%s) as totalMoney FROM " + BILL_TBL_NAME + "," + BILL_DETAIL_TBL_NAME + " WHERE " +
                            " %s.%s = %s.%s " + " AND " + COLUMN_STATE + " = '1' " + " AND " +
                            " date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + startDate + "'" + " AND " + "'" + endDate + "'" +
                            " GROUP BY " + COLUMN_DISH_ID + "," + COLUMN_DATE_CREATED,
                    COLUMN_DATE_CREATED, COLUMN_DISH_ID, COLUMN_QUANTITY, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_TOTAL_MONEY,
                    BILL_TBL_NAME, IDBUtils.ITableBillUtils.COLUMN_BILL_ID, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_BILL_ID
            );

            Log.d("sql", "getReportByDate: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(parseCursorToReportTotal(cursor));
                    cursor.moveToNext();
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ReportDetail> getReportDetailByDate(String startDate, String endDate) {
        try {
            ArrayList<ReportDetail> list = new ArrayList<>();

            String sql = String.format("SELECT %s,%s,sum(%s) as quantity,sum(%s.%s) as totalMoney FROM " + BILL_TBL_NAME + "," + BILL_DETAIL_TBL_NAME + " WHERE " +
                            " %s.%s = %s.%s " + " AND " + COLUMN_STATE + " = '1' " + " AND " +
                            " date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + startDate + "'" + " AND " + "'" + endDate + "'" +
                            " GROUP BY " + COLUMN_DISH_ID + "," + COLUMN_DATE_CREATED,
                    COLUMN_DATE_CREATED, COLUMN_DISH_ID, COLUMN_QUANTITY, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_TOTAL_MONEY,
                    BILL_TBL_NAME, IDBUtils.ITableBillUtils.COLUMN_BILL_ID, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_BILL_ID
            );

            Log.d("sql", "getReportByDate: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(parseCursorToReportDetail(cursor));
                    cursor.moveToNext();
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo theo khoảng thời gian
     * Created at 18/04/2019
     *
     * @param date - ngày muốn lấy báo cáo - Định dạng là yyyy-MM-dd
     * @return - danh sách báo cáo thống kê theo từng ngày
     */
    @Override
    public ArrayList<ReportDetail> getReportDetailDate(String date) {
        try {
            ArrayList<ReportDetail> list = new ArrayList<>();

            String sql = String.format("SELECT %s,%s,sum(%s) as quantity,sum(%s.%s) as totalMoney FROM " + BILL_TBL_NAME + "," + BILL_DETAIL_TBL_NAME + " WHERE " +
                            " %s.%s = %s.%s " + " AND " + COLUMN_STATE + " = '1' " + " AND " +
                            " date(" + COLUMN_DATE_CREATED + ")" + " = " + "'" + date + "'" +
                            " GROUP BY " + COLUMN_DISH_ID + "," + COLUMN_DATE_CREATED,
                    COLUMN_DATE_CREATED, COLUMN_DISH_ID, COLUMN_QUANTITY, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_TOTAL_MONEY,
                    BILL_TBL_NAME, IDBUtils.ITableBillUtils.COLUMN_BILL_ID, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_BILL_ID
            );

            Log.d("sql", "getReportByDate: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(parseCursorToReportDetail(cursor));
                    cursor.moveToNext();
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo tổng quan
     * Created at 18/04/2019
     *
     * @return - báo cáo tổng quan doanh thu
     */
    @Override
    public ArrayList<ReportCurrent> getOverviewReport() {
        try {
            ArrayList<ReportCurrent> list = new ArrayList<>();
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.YESTERDAY)
                    .setAmount((long) getReportTotalMoneyYesterday())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.TODAY)
                    .setAmount((long) getReportTotalMoneyToday())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.THIS_WEEK)
                    .setAmount((long) getReportTotalMoneyThisWeek())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.THIS_MONTH)
                    .setAmount((long) getReportTotalMoneyThisMonth())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.THIS_YEAR)
                    .setAmount((long) getReportTotalMoneyThisYear())
                    .build());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
