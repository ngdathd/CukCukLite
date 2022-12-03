package vn.misa.nadat.cukcuklite.database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemReportDetail;
import vn.misa.nadat.cukcuklite.items.ItemReportTime;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.ConstantDB;
import vn.misa.nadat.cukcuklite.utils.DateUtils;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Đối tượng quản lý report
 *
 * @created_by nadat on 24/04/2019
 */
public class DBItemReportManager {
    private static DBItemReportManager mDBItemReportManager = new DBItemReportManager();
    private SimpleDateFormat mDayFormat;
    private SimpleDateFormat mMonthFormat;

    @SuppressLint("SimpleDateFormat")
    private DBItemReportManager() {
        mDayFormat = new SimpleDateFormat("yyyy-MM-dd");
        mMonthFormat = new SimpleDateFormat("yyyy-MM");
    }

    public static DBItemReportManager getInstance() {
        return mDBItemReportManager;
    }

    /**
     * Lấy ra doanh thu trong khoảng thời gian
     *
     * @param startDate: ngày bắt đầu
     * @param endDate:   ngày kết thúc
     * @return doanh thu
     * @created_by nadat on 24/04/2019
     */
    public String getItemReportsRevenue(String startDate, String endDate) {
        try {
            if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
                SQLiteDatabase readableDatabase = DBHelper.getInstance().getReadableDatabase();
                String money = null;

                String query = "SELECT SUM(" + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.TOTAL_MONEY_DISHES + ") AS TOTAL FROM "
                        + ConstantDB.TABLE_ITEM_SALE
                        + " INNER JOIN " + ConstantDB.TABLE_ITEM_SALE_DETAIL
                        + " ON "
                        + ConstantDB.TABLE_ITEM_SALE + "." + ConstantDB.ITEM_SALE_ID
                        + " = "
                        + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.ITEM_SALE_ID
                        + " INNER JOIN " + ConstantDB.TABLE_ITEM_DISH
                        + " ON "
                        + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.ITEM_DISH_ID
                        + " = "
                        + ConstantDB.TABLE_ITEM_DISH + "." + ConstantDB.ITEM_DISH_ID
                        + " WHERE " + ConstantDB.TABLE_ITEM_SALE + "." + ConstantDB.PAYMENT_DATE
                        + " BETWEEN Datetime('" + startDate + "') AND Datetime('" + endDate + "')"
                        + " AND " + ConstantDB.TABLE_ITEM_SALE + "." + ConstantDB.PAYMENT_STATUS + " = 1";

                Cursor cursor = readableDatabase.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    money = cursor.getString(cursor.getColumnIndex("TOTAL"));
                }

                cursor.close();
                readableDatabase.close();
                return PriceUtils.formatPrice(money);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ra danh sách báo cáo theo tuần
     *
     * @param typeReport: loại báo cáo
     * @return danh sách báo cáo
     * @created_by nadat on 24/04/2019
     */
    public List<ItemReportTime> getDetailWeek(String typeReport) {
        try {
            List<ItemReportTime> listItemReportTime = new ArrayList<>();
            String[] thisWeek = new String[2];

            if (typeReport.equals(Constant.THISWEEK)) {
                thisWeek = DateUtils.getInstance().getThisWeek();
            } else if (typeReport.equals(Constant.LASTWEEK)) {
                thisWeek = DateUtils.getInstance().getLastWeek();
            }

            Date current = DateUtils.getInstance().getDate(thisWeek[0]);
            Date end = DateUtils.getInstance().getDate(thisWeek[1]);

            int dayOfWeek = 2;

            while (current.compareTo(end) <= 0) {
                String date = mDayFormat.format(current);
                ItemReportTime itemReportTime = queryItemReportTime(date);
                if (itemReportTime != null) {
                    if (dayOfWeek == 8) {
                        itemReportTime.setTitle(Constant.SUNDAY);
                    } else {
                        itemReportTime.setTitle(Constant.DAY_OF_WEEK + dayOfWeek);
                    }
                }
                listItemReportTime.add(itemReportTime);

                dayOfWeek++;
                current = DateUtils.getInstance().nextDay(current);
            }

            return listItemReportTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ra danh sách báo cáo theo tháng
     *
     * @param typeReport: loại báo cáo
     * @return danh sách báo cáo
     * @created_by nadat on 24/04/2019
     */
    public List<ItemReportTime> getDetailMonth(String typeReport) {
        try {
            List<ItemReportTime> listItemReportTime = new ArrayList<>();
            String[] thisMonth = new String[2];

            if (typeReport.equals(Constant.THISMONTH)) {
                thisMonth = DateUtils.getInstance().getThisMonth();
            } else if (typeReport.equals(Constant.LASTMONTH)) {
                thisMonth = DateUtils.getInstance().getLastMonth();
            }

            Date current = DateUtils.getInstance().getDate(thisMonth[0]);
            Date end = DateUtils.getInstance().getDate(thisMonth[1]);

            int day = 1;

            while (current.compareTo(end) <= 0) {
                String date = mDayFormat.format(current);
                ItemReportTime itemReportTime = queryItemReportTime(date);
                if (itemReportTime != null) {
                    itemReportTime.setTitle(Constant.DAY + day);
                }
                listItemReportTime.add(itemReportTime);

                day++;
                current = DateUtils.getInstance().nextDay(current);
            }

            return listItemReportTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ra danh sách báo cáo theo năm
     *
     * @param typeReport: loại báo cáo
     * @return danh sách báo cáo
     * @created_by nadat on 24/04/2019
     */
    public List<ItemReportTime> getDetailYear(String typeReport) {
        try {
            List<ItemReportTime> listItemReportTime = new ArrayList<>();
            String[] thisYear = new String[2];

            if (typeReport.equals(Constant.THISYEAR)) {
                thisYear = DateUtils.getInstance().getThisYear();
            } else if (typeReport.equals(Constant.LASTYEAR)) {
                thisYear = DateUtils.getInstance().getLastYear();
            }

            Date current = DateUtils.getInstance().getDate(thisYear[0]);
            Date end = DateUtils.getInstance().getDate(thisYear[1]);

            int month = 1;

            while (current.compareTo(end) <= 0) {
                String date = mMonthFormat.format(current);
                ItemReportTime itemReportTime = queryItemReportTime(date);
                if (itemReportTime != null) {
                    itemReportTime.setTitle(Constant.MONTH + month);
                }
                listItemReportTime.add(itemReportTime);

                month++;
                current = DateUtils.getInstance().nextMonth(current);
            }

            return listItemReportTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ra doanh thu trong ngày
     *
     * @param day: ngày lấy
     * @return ItemReportTime
     * @created_by nadat on 24/04/2019
     */
    private ItemReportTime queryItemReportTime(String day) {
        try {
            if (!TextUtils.isEmpty(day)) {
                SQLiteDatabase readableDatabase = DBHelper.getInstance().getReadableDatabase();
                ItemReportTime itemReportTime = new ItemReportTime();

                String query = "SELECT *, SUM(" + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.TOTAL_MONEY_DISHES + ") AS TOTAL FROM "
                        + ConstantDB.TABLE_ITEM_SALE
                        + " INNER JOIN " + ConstantDB.TABLE_ITEM_SALE_DETAIL
                        + " ON "
                        + ConstantDB.TABLE_ITEM_SALE + "." + ConstantDB.ITEM_SALE_ID
                        + " = "
                        + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.ITEM_SALE_ID
                        + " INNER JOIN " + ConstantDB.TABLE_ITEM_DISH
                        + " ON "
                        + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.ITEM_DISH_ID
                        + " = "
                        + ConstantDB.TABLE_ITEM_DISH + "." + ConstantDB.ITEM_DISH_ID
                        + " WHERE " + ConstantDB.TABLE_ITEM_SALE + "." + ConstantDB.PAYMENT_DATE
                        + " LIKE '%" + day + "%'"
                        + " AND " + ConstantDB.TABLE_ITEM_SALE + "." + ConstantDB.PAYMENT_STATUS + " = 1";

                Cursor cursor = readableDatabase.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    String money = cursor.getString(cursor.getColumnIndex("TOTAL"));
                    String time = cursor.getString(cursor.getColumnIndex(ConstantDB.PAYMENT_DATE));

                    itemReportTime.setMoney(money);
                    itemReportTime.setTime(time);
                }

                cursor.close();
                readableDatabase.close();
                return itemReportTime;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy danh sách chi tiết báo cáo
     *
     * @param day: ngày lấy
     * @return danh sách chi tiết báo cáo
     * @created_by nadat on 24/04/2019
     */
    public List<ItemReportDetail> queryListDetailReport(String day) {
        try {
            if (!TextUtils.isEmpty(day)) {
                SQLiteDatabase sDatabase = DBHelper.getInstance().getReadableDatabase();
                List<ItemReportDetail> listDetailReport = new ArrayList<>();

                String query = "SELECT id.ItemDishId, id.ItemDishName, iu.ItemUnitName, "
                        + "SUM(isd.NumberOfDish) AS TOTAL_NUMBER, "
                        + "SUM(isd.TotalMoneyDishes) AS TOTAL_MONEY "
                        + "FROM ITEM_DISH id "
                        + "JOIN ITEM_UNIT iu ON iu.ItemUnitId = id.ItemUnitId "
                        + "JOIN ITEM_SALE_DETAIL isd ON isd.ItemDishId = id.ItemDishId "
                        + "JOIN ITEM_SALE isa ON isa.itemSaleId = isd.ItemSaleId "
                        + "WHERE isa.PaymentStatus = 1 AND isa.PaymentDate LIKE '%" + day + "%'"
                        + "GROUP BY id.ItemDishId "
                        + "ORDER BY sum(isd.TotalMoneyDishes) DESC";
                Cursor cursor = sDatabase.rawQuery(query, null);

                while (cursor.moveToNext()) {
                    ItemReportDetail detailReport = new ItemReportDetail();
                    detailReport.setNameDish(cursor.getString(cursor.getColumnIndex("ItemDishName")));
                    detailReport.setUnitDish(cursor.getString(cursor.getColumnIndex("ItemUnitName")));
                    detailReport.setNumberDish(cursor.getString(cursor.getColumnIndex("TOTAL_NUMBER")));
                    detailReport.setTotalMoney(cursor.getInt(cursor.getColumnIndex("TOTAL_MONEY")));

                    listDetailReport.add(detailReport);
                }

                cursor.close();
                sDatabase.close();
                return listDetailReport;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy danh sách chi tiết báo cáo
     *
     * @param startDate ngày bắt đầu lấy
     * @param endDate   ngày kết thức lấy
     * @return danh sách chi tiết báo cáo
     * @created_by nadat on 24/04/2019
     */
    public List<ItemReportDetail> queryListDetailReport(String startDate, String endDate) {
        try {
            if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
                SQLiteDatabase sDatabase = DBHelper.getInstance().getReadableDatabase();
                List<ItemReportDetail> listDetailReport = new ArrayList<>();

                String query = "SELECT id.ItemDishId, id.ItemDishName, iu.ItemUnitName, "
                        + "SUM(isd.NumberOfDish) AS TOTAL_NUMBER, "
                        + "SUM(isd.TotalMoneyDishes) AS TOTAL_MONEY "
                        + "FROM ITEM_DISH id "
                        + "JOIN ITEM_UNIT iu ON iu.ItemUnitId = id.ItemUnitId "
                        + "JOIN ITEM_SALE_DETAIL isd ON isd.ItemDishId = id.ItemDishId "
                        + "JOIN ITEM_SALE isa ON isa.itemSaleId = isd.ItemSaleId "
                        + "WHERE isa.PaymentStatus = 1 AND isa.PaymentDate BETWEEN Datetime('" + startDate + "') AND Datetime('" + endDate + "')"
                        + "GROUP BY id.ItemDishId "
                        + "ORDER BY sum(isd.TotalMoneyDishes) DESC";
                Cursor cursor = sDatabase.rawQuery(query, null);

                while (cursor.moveToNext()) {
                    ItemReportDetail detailReport = new ItemReportDetail();
                    detailReport.setNameDish(cursor.getString(cursor.getColumnIndex("ItemDishName")));
                    detailReport.setUnitDish(cursor.getString(cursor.getColumnIndex("ItemUnitName")));
                    detailReport.setNumberDish(cursor.getString(cursor.getColumnIndex("TOTAL_NUMBER")));
                    detailReport.setTotalMoney(cursor.getInt(cursor.getColumnIndex("TOTAL_MONEY")));

                    listDetailReport.add(detailReport);
                }

                cursor.close();
                sDatabase.close();
                return listDetailReport;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

