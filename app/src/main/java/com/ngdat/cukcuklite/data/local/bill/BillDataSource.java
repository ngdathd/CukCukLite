package com.ngdat.cukcuklite.data.local.bill;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.ngdat.cukcuklite.data.cukcukenum.BillSateEnum;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.data.local.database.SQLiteDBManager;
import com.ngdat.cukcuklite.data.local.dish.DishDataSource;
import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;
import com.ngdat.cukcuklite.data.models.Order;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.DateUtil;

import static com.ngdat.cukcuklite.data.local.database.IDBUtils.ITableBillDetailUtils.BILL_DETAIL_TBL_NAME;
import static com.ngdat.cukcuklite.data.local.database.IDBUtils.ITableBillDetailUtils.COLUMN_BILL_DETAIL_ID;
import static com.ngdat.cukcuklite.data.local.database.IDBUtils.ITableBillDetailUtils.COLUMN_DISH_ID;
import static com.ngdat.cukcuklite.data.local.database.IDBUtils.ITableBillDetailUtils.COLUMN_QUANTITY;

/**
 * Lớp thao tác truy xuất hóa đơn và hóa đơn chi tiết trong cơ sở dữ liệu
 * Created at 12/04/2019
 */
public class BillDataSource implements IBillDataSource, IDBUtils.ITableBillUtils {
    private static final String TAG = "BillDataSource";
    private static BillDataSource sInstance;
    private SQLiteDBManager mSQLiteDBManager;
    private DishDataSource mDishDataSource;
    private HashMap<String, Order> orderHashMap;

    /**
     * Phương thức khởi tạo cho lớp
     * Created at 18/04/2019
     */
    private BillDataSource() {
        try {
            mSQLiteDBManager = SQLiteDBManager.getInstance();
            mDishDataSource = DishDataSource.getInstance();
            orderHashMap = new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy Instance của lớp
     * Created at 18/04/2019
     *
     * @return - Instance của lớp
     */
    public static BillDataSource getInstance() {
        if (sInstance == null) {
            synchronized (BillDataSource.class) {
                if (sInstance == null) {
                    sInstance = new BillDataSource();
                }
            }
        }
        return sInstance;
    }

    /**
     * Phương thức khởi tạo danh sách hóa đơn chi tiết mặc định gồm
     * id của hóa đơn và các món ăn có thể có trong hóa đơn
     * Created at 12/04/2019
     *
     * @param billId - id của hóa đơn
     * @return - danh sách hóa đơn chi tiết mặc định
     */
    @Override
    public List<BillDetail> initNewBillDetailList(String billId) {
        try {
            if (billId != null) {
                List<BillDetail> billDetails = new ArrayList<>();
                List<String> dishIds = mDishDataSource.getAllDishId();
                if (dishIds != null) {
                    for (int i = 0; i < dishIds.size(); i++) {
                        BillDetail billDetail = new BillDetail.Builder()
                                .setBillDetailId(UUID.randomUUID().toString())
                                .setBillId(billId)
                                .setDishId(dishIds.get(i)).build();
                        billDetails.add(billDetail);
                    }
                    return billDetails;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Phương thức khởi tạo danh sách hóa đơn chi tiết chưa được lưu thông qua bill Id
     * Created at 12/04/2019
     *
     * @param billId - id của hóa đơn
     * @return - danh sách hóa đơn chi tiết của hóa đơn chưa được lưu
     */
    @Override
    public List<BillDetail> initBillDetailList(String billId) {
        try {
            if (billId != null) {
                List<BillDetail> billDetailDefault = initNewBillDetailList(billId);
                List<BillDetail> billDetailInDB = getAllBillDeTailByBillId(billId);
                if (billDetailDefault != null && billDetailInDB != null) {
                    int sizeDefault = billDetailDefault.size();
                    int sizeInIB = billDetailInDB.size();
                    for (int i = 0; i < sizeDefault; i++) {
                        for (int j = 0; j < sizeInIB; j++) {
                            if (billDetailDefault.get(i).getDishId().equals(billDetailInDB.get(j).getDishId())) {
                                billDetailDefault.set(i, billDetailInDB.get(j));
                                break;
                            }
                        }
                    }
                    return billDetailDefault;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Lấy hóa đơn thông qua id của hóa đơn
     * Created at 18/04/2019
     *
     * @param billId - id của hóa đơn
     * @return - Hóa đơn
     */
    @Override
    public Bill getBillById(String billId) {
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + BILL_TBL_NAME +
                    " where " + COLUMN_STATE + "=0 and " + COLUMN_BILL_ID + "=" + "'" + billId + "'", null);
            cursor.moveToFirst();
            Bill bill = new Bill.Builder().setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)))
                    .setBillNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_NUMBER)))
                    .setTotalMoney(cursor.getInt((cursor.getColumnIndex(COLUMN_TOTAL_MONEY))))
                    .setNumberCustomer(cursor.getInt((cursor.getColumnIndex(COLUMN_NUMBER_CUSTOMER))))
                    .setTableNumber(cursor.getInt((cursor.getColumnIndex(COLUMN_TABLE_NUMBER))))
                    .setDateCreated(DateUtil.getTimeMilisecondsFromDateString(cursor.getString((cursor.getColumnIndex(COLUMN_DATE_CREATED)))))
                    .build();
            cursor.moveToNext();
            cursor.close();
            return bill;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cập nhật hóa đơn vào cơ sở dữ liệu
     * Created at 12/04/2019
     *
     * @param bill - hóa đơn
     * @return - thêm hóa đơn thành công/thất bại
     */
    @Override
    public boolean updateBill(Bill bill, List<BillDetail> validBillDetailList) {
        try {
            if (bill != null) {
                String billId = bill.getBillId();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_ID, billId);
                contentValues.put(COLUMN_BILL_NUMBER, bill.getBillNumber());
                contentValues.put(COLUMN_DATE_CREATED, String.valueOf(bill.getDateCreated()));
                contentValues.put(COLUMN_TABLE_NUMBER, bill.getTableNumber());
                contentValues.put(COLUMN_NUMBER_CUSTOMER, bill.getNumberCustomer());
                contentValues.put(COLUMN_TOTAL_MONEY, bill.getTotalMoney());
                contentValues.put(COLUMN_CUSTOMER_PAY, bill.getCustomerPay());
                contentValues.put(COLUMN_STATE, AppConstants.UN_PAID);
                if (mSQLiteDBManager.updateRecord(BILL_TBL_NAME, contentValues
                        , COLUMN_BILL_ID + "=?", new String[]{billId})) {
                    //xóa hết bản ghi hóa đơn chi tiết cũ -> thêm bản ghi hóa đơn chi tiết mới
                    if (removeAllBillDetaiListByBillId(billId)) {
                        if (addBillDetailList(validBillDetailList)) {
                            //thêm order vào cache
                            addOrderToCache(getOrderFromBill(bill));
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thúc lấy toàn bộ id của món ăn đã sử dụng cho việc thanh toán
     * Created at 16/04/2019
     *
     * @return - danh sách id của món ăn đã sử dụng
     */
    @Override
    public List<String> getAllDishIdFromAllBillDetail() {
        List<String> listDishId = null;
        try {
            listDishId = new ArrayList<>();
            String sql = String.format(
                    "SELECT %s FROM " + BILL_DETAIL_TBL_NAME, COLUMN_DISH_ID);
            Cursor cursor = mSQLiteDBManager.getRecords(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String dishId = cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID));
                listDishId.add(dishId);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDishId;
    }

    /**
     * Lấy danh số lượng hóa đơn đã thanh toán
     * Created at 16/04/2019
     *
     * @return - số lượng hóa đơn đã thanh toán
     */
    @Override
    public int countBillWasPaid() {
        try {
            String sql = String.format("SELECT count(*) FROM %s WHERE %s = '%s' ", BILL_TBL_NAME, COLUMN_STATE, AppConstants.PAID);
            Cursor cursor = mSQLiteDBManager.getRecords(sql, null);
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
     * Phương thức thanh toán hóa đơn
     * Created at 17/04/2019
     *
     * @param bill - hóa đơn
     */
    @Override
    public boolean payBill(Bill bill) {
        try {
            if (bill != null) {
                Calendar calendar = Calendar.getInstance();
                String billId = bill.getBillId();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_ID, billId);
                contentValues.put(COLUMN_BILL_NUMBER, bill.getBillNumber());
                contentValues.put(COLUMN_DATE_CREATED, DateUtil.getDateFormat(calendar.getTime()));
                contentValues.put(COLUMN_TABLE_NUMBER, bill.getTableNumber());
                contentValues.put(COLUMN_NUMBER_CUSTOMER, bill.getNumberCustomer());
                contentValues.put(COLUMN_TOTAL_MONEY, bill.getTotalMoney());
                contentValues.put(COLUMN_CUSTOMER_PAY, bill.getCustomerPay());
                contentValues.put(COLUMN_STATE, AppConstants.PAID);
                if (mSQLiteDBManager.updateRecord(BILL_TBL_NAME, contentValues
                        , COLUMN_BILL_ID + "=?", new String[]{billId})) {
                    removeOrderInCache(billId);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức xóa bỏ hết các bản ghi hóa đơn chi tiết thông qua hóa đơn id
     * Created at 16/04/2019
     *
     * @param billId - hóa đơn id
     * @return - xóa thành công hay thất bại
     */
    private boolean removeAllBillDetaiListByBillId(String billId) {
        try {
            return mSQLiteDBManager.deleteRecord(BILL_DETAIL_TBL_NAME,
                    COLUMN_BILL_ID + "=?", new String[]{billId});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Thêm hóa đơn vào cơ sở dữ liệu
     * Created at 12/04/2019
     *
     * @param bill        - hóa đơn
     * @param billDetails - hóa đơn chi tiết
     * @return - thêm hóa đơn thành công/thất bại
     */
    @Override
    public boolean addBill(Bill bill, List<BillDetail> billDetails) {
        try {
            if (bill != null) {
                Log.d(TAG, "addBill: ");
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_ID, bill.getBillId());
                contentValues.put(COLUMN_BILL_NUMBER, bill.getBillNumber());
                contentValues.put(COLUMN_DATE_CREATED, DateUtil.getDateFormat(Calendar.getInstance().getTime()));
                contentValues.put(COLUMN_TABLE_NUMBER, bill.getTableNumber());
                contentValues.put(COLUMN_NUMBER_CUSTOMER, bill.getNumberCustomer());
                contentValues.put(COLUMN_TOTAL_MONEY, bill.getTotalMoney());
                contentValues.put(COLUMN_CUSTOMER_PAY, bill.getCustomerPay());
                contentValues.put(COLUMN_STATE, AppConstants.UN_PAID);
                if (mSQLiteDBManager.addNewRecord(BILL_TBL_NAME, contentValues)) {
                    if (addBillDetailList(billDetails)) {
                        addOrderToCache(getOrderFromBill(bill));
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Thêm hóa đơn vào cơ sở dữ liệu
     * Created at 12/04/2019
     *
     * @param bill - hóa đơn
     * @return - thêm hóa đơn thành công/thất bại
     */
    @Override
    public boolean addBill(Bill bill) {
        try {
            if (bill != null) {
                Log.d(TAG, "addBill: ");
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_ID, bill.getBillId());
                contentValues.put(COLUMN_BILL_NUMBER, bill.getBillNumber());
                contentValues.put(COLUMN_DATE_CREATED, DateUtil.getDateFormat(Calendar.getInstance().getTime()));
                contentValues.put(COLUMN_TABLE_NUMBER, bill.getTableNumber());
                contentValues.put(COLUMN_NUMBER_CUSTOMER, bill.getNumberCustomer());
                contentValues.put(COLUMN_TOTAL_MONEY, bill.getTotalMoney());
                contentValues.put(COLUMN_CUSTOMER_PAY, bill.getCustomerPay());
                contentValues.put(COLUMN_STATE, AppConstants.UN_PAID);
                if (mSQLiteDBManager.addNewRecord(BILL_TBL_NAME, contentValues)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addOrderToCache(Order order) {
        orderHashMap.put(order.getBillId(), order);
    }


    /**
     * Thêm hóa đơn chi tiết vào cơ sở dữ liệu
     * Created at 12/04/2019
     *
     * @param billDetail - hóa đơn chi tiết
     * @return - thêm hóa đơn chi tiết thành công/thất bại
     */
    @Override
    public boolean addBillDetail(BillDetail billDetail) {
        try {
            if (billDetail != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_DETAIL_ID, billDetail.getBillDetailId());
                contentValues.put(COLUMN_BILL_ID, billDetail.getBillId());
                contentValues.put(COLUMN_DISH_ID, billDetail.getDishId());
                contentValues.put(COLUMN_QUANTITY, billDetail.getQuantity());
                contentValues.put(COLUMN_TOTAL_MONEY, billDetail.getTotalMoney());
                return mSQLiteDBManager.addNewRecord(BILL_DETAIL_TBL_NAME, contentValues);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Thêm danh sách hóa đơn chi tiết vào cơ sở dữ liệu
     * Created at 12/04/2019
     *
     * @param billDetails - danh sách hóa đơn chi tiết
     * @return - thêm danh sách hóa đơn chi tiết thành công hay thất bại
     */
    @Override
    public boolean addBillDetailList(List<BillDetail> billDetails) {
        try {
            boolean addSuccess = true;
            if (billDetails != null) {
                int size = billDetails.size();
                for (int i = 0; i < size; i++) {
                    if (!addBillDetail(billDetails.get(i))) {
                        addSuccess = false;
                        break;
                    }
                }
                return addSuccess;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức lấy hết danh sách hóa đơn chi tiết thông qua id của hóa đơn
     * Created at 12/04/2019
     *
     * @param billId - id của của hóa đơn
     * @return - danh sách hóa đơn chi tiết
     */
    @Override
    public List<BillDetail> getAllBillDeTailByBillId(String billId) {
        List<BillDetail> billDetails = new ArrayList<>();
        try {
            String sql = String.format(
                    "SELECT %s.%s,%s,%s,%s,%s,%s FROM " + BILL_DETAIL_TBL_NAME + ","
                            + DishDataSource.DISH_TBL_NAME + " where " + COLUMN_BILL_ID
                            + "=" + "'" + billId + "'" + " and %s.%s = %s.%s",
                    BILL_DETAIL_TBL_NAME,
                    COLUMN_DISH_ID,
                    DishDataSource.COLUMN_DISH_NAME,
                    COLUMN_BILL_ID,
                    COLUMN_QUANTITY,
                    COLUMN_TOTAL_MONEY,
                    COLUMN_BILL_DETAIL_ID,
                    BILL_DETAIL_TBL_NAME,
                    COLUMN_DISH_ID,
                    DishDataSource.DISH_TBL_NAME,
                    COLUMN_DISH_ID
            );
            Log.d(TAG, "getAllBillDeTailByBillId: " + sql);
            Cursor cursor = mSQLiteDBManager.getRecords(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BillDetail billDetail = new BillDetail.Builder()
                        .setBillDetailId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DETAIL_ID)))
                        .setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)))
                        .setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                        .setDishId(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID)))
                        .setTotalMoney(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_MONEY)))
                        .setName(cursor.getString(cursor.getColumnIndex(DishDataSource.COLUMN_DISH_NAME)))
                        .build();
                billDetails.add(billDetail);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billDetails;
    }

    /**
     * Phương thức lấy danh sách các hóa đơn
     * Created at 13/04/2019
     *
     * @return - danh sách hóa đơn
     */
    @Override
    public List<Bill> getAllBillUnpaid() {
        try {
            List<Bill> bills = new ArrayList<>();
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + BILL_TBL_NAME + " where " + COLUMN_STATE + "=0", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill.Builder().setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)))
                        .setBillNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_NUMBER)))
                        .setState(BillSateEnum.UNPAID)
                        .setTotalMoney(cursor.getInt((cursor.getColumnIndex(COLUMN_TOTAL_MONEY))))
                        .setNumberCustomer(cursor.getInt((cursor.getColumnIndex(COLUMN_NUMBER_CUSTOMER))))
                        .setTableNumber(cursor.getInt((cursor.getColumnIndex(COLUMN_TABLE_NUMBER))))
                        .setDateCreated(DateUtil.getTimeMilisecondsFromDateString(cursor.getString((cursor.getColumnIndex(COLUMN_DATE_CREATED)))))
                        .build();
                bills.add(bill);
                cursor.moveToNext();
            }
            cursor.close();
            return bills;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy danh sách gọi món
     * Created at 13/04/2019
     *
     * @return - danh sách gọi món
     */
    @Override
    public List<Order> getAllOrder() {
        try {
            if (orderHashMap.size() > 0) {
                return new ArrayList<>(orderHashMap.values());
            }
            List<Bill> bills = getAllBillUnpaid();
            orderHashMap = new HashMap<>();
            if (bills != null) {
                for (Bill bill : bills) {
                    Log.d(TAG, "getAllOrder: " + bill.toString());
                    addOrderToCache(getOrderFromBill(bill));
                }
                return new ArrayList<>(orderHashMap.values());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức lấy order từ hóa đơn
     * Created at 16/04/2019
     *
     * @param bill - hóa đơn
     * @return - order
     */
    private Order getOrderFromBill(Bill bill) {

        List<BillDetail> billDetails = getAllBillDeTailByBillId(bill.getBillId());
        StringBuilder content = new StringBuilder();
        if (billDetails != null) {
            for (BillDetail billDetail : billDetails) {
                content.append(billDetail.getName());
                content.append(" <font color=#0973b9>(").append(billDetail.getQuantity()).append(")</font>").append(", ");
            }
        }

        content = content.replace(content.length() - 2, content.length(), ".");
        return new Order.Builder().setBillId(bill.getBillId())
                .setContent(content.toString())
                .setNumberCustomer(bill.getNumberCustomer())
                .setTableNumber(bill.getTableNumber())
                .setTotalMoney(bill.getTotalMoney())
                .setDateCreated(bill.getDateCreated()).build();
    }


    /**
     * Phương thức hủy hóa đơn
     * Created at 18/04/2019
     *
     * @param billId - id của hóa đơn
     * @return - hủy hóa đơn thành công/thất bại
     */
    @Override
    public boolean cancelOrder(String billId) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_STATE, AppConstants.CANCEL);
            if (mSQLiteDBManager.updateRecord(BILL_TBL_NAME, contentValues,
                    COLUMN_BILL_ID + "=?", new String[]{billId})) {
                removeOrderInCache(billId);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Loại bỏ order ra khỏi bộ nhớ cache
     * Created at 15/04/2019
     *
     * @param billId - hóa đơn id
     */
    private void removeOrderInCache(String billId) {
        try {
            if (orderHashMap != null && orderHashMap.size() > 0) {
                orderHashMap.remove(billId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAllCache() {
        try {
            if (orderHashMap != null && orderHashMap.size() > 0) {
                orderHashMap.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
