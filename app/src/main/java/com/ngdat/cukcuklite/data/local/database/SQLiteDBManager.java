package com.ngdat.cukcuklite.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Lớp khởi tạo và truy xuất dữ liệu từ sqlite database của ứng dụng
 * Created at 20/03/2019
 */
public class SQLiteDBManager extends SQLiteOpenHelper implements IDBUtils {

    private static final String TAG = "SQLiteDBManager";
    private static SQLiteDBManager sInstance;
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;

    /**
     * Phương thức khởi tạo
     *
     * @param context - context
     */
    private SQLiteDBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    /**
     * Phương thức khởi tạo đảm bảo duy nhất 1 đối tượng SQLiteDBManager tồn tại và sử dụng cho toàn
     * bộ ứng dụng
     * Created at 27/03/2019
     *
     * @param context - context
     * @return - đối tượng SQLiteDBManager
     */
    public static SQLiteDBManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SQLiteDBManager.class) {
                if (sInstance == null) {
                    sInstance = new SQLiteDBManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Phương thức lấy đối tương SQLiteDBManager của ứng dụng
     *
     * @return - SQLiteDBManager
     */
    public static SQLiteDBManager getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // cho phép cơ sở dữ liệu có foreign key
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * phương thức mở kết nối database
     * Created at 20/03/2019
     */
    private void openDataBase() {
        try {
            String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
            if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()) {
                return;
            }
            mSQLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * phương thức đóng kết nối database
     * Created at 20/03/2019
     */
    public void closeDatabase() {
        try {
            if (mSQLiteDatabase != null) {
                mSQLiteDatabase.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức copy database
     * Created at 25/03/2019
     */
    public void copyDatabase() {
        try {
            InputStream inputStream = mContext.getAssets().open("database/" + DB_NAME);
            String outFileName = DB_LOCATION + DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm bản ghi vào cở sở dữ liệu
     * Created at 25/03/2019
     *
     * @param tableName     - Tên bảng
     * @param contentValues - Dòng dữ liệu
     * @return - bản ghi có được thêm vào hay không
     */
    public boolean addNewRecord(String tableName, ContentValues contentValues) {
        try {
            openDataBase();
            return mSQLiteDatabase.insert(tableName, null, contentValues) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức dùng để lấy bản ghi từ cơ sở dữ liệu
     * Created at 20/03/2019
     *
     * @param table         - tên bảng
     * @param columns       - danh sách cột giá trị muốn lấy
     * @param selection     - điều kiện
     * @param selectionArgs - giá trị điều kiện
     * @param groupBy       - câu lệnh groupBy
     * @param having        - câu lệnh having
     * @param orderBy       - câu lệnh orderBy
     * @return Giá trị trả về là 1 cursor
     * có thể trả về null
     */
    public Cursor getRecords(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = null;
        try {
            openDataBase();
            cursor = mSQLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            return cursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    /**
     * Phương thức dùng để lấy bản ghi từ cơ sở dữ liệu
     * Created at 20/03/2019
     *
     * @param sql           - Câu lệnh select (giá trị điều kiện được tạo bằng dấu ?)
     * @param selectionArgs - điều kiện where
     * @return Giá trị trả về là 1 cursor
     * có thể trả về null
     */
    public Cursor getRecords(String sql, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            openDataBase();
            cursor = mSQLiteDatabase.rawQuery(sql, selectionArgs);
            return cursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    /**
     * Phương thức dùng để update 1 bản ghi vào cơ sở dữ liệu
     * Created at 20/03/2019
     *
     * @param tableName     - tên bảng
     * @param contentValues - đối tượng muốn chỉnh sửa(giá trị mới)
     * @param whereClause   - điều kiện lọc (dùng dấu ? để tạo điều kiện lọc)
     * @param whereArgs     - tập các giá trị của điều kiện lọc (lấy theo đúng thứ tự)
     */
    public boolean updateRecord(String tableName, ContentValues contentValues, String whereClause, String[] whereArgs) {
        try {
            openDataBase();
            return mSQLiteDatabase.update(tableName, contentValues, whereClause, whereArgs) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức dùng để xóa 1 bản ghi khỏi cơ sở dữ liệu
     * Created at 20/03/2019
     *
     * @param table       - tên bảng
     * @param whereClause - điều kiện lọc (dùng dấu ? để tạo điều kiện lọc)
     * @param whereArgs   - tập các giá trị của điều kiện lọc (lấy theo đúng thứ tự)
     */
    public boolean deleteRecord(String table, String whereClause, String[] whereArgs) {
        try {
            openDataBase();
            mSQLiteDatabase.delete(table, whereClause, whereArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức xóa hết dữ liệu trong database
     * Created at 24/04/2019
     *
     * @return xóa thành công, thất bại
     */
    public boolean clearDatabase() {
        try {
            openDataBase();
            mSQLiteDatabase.execSQL("delete from " + ITableBillDetailUtils.BILL_DETAIL_TBL_NAME);
            mSQLiteDatabase.execSQL("delete from " + ITableBillUtils.BILL_TBL_NAME);
            mSQLiteDatabase.execSQL("delete from " + ITableDishUtils.DISH_TBL_NAME);
            mSQLiteDatabase.execSQL("delete from " + ITableUnitUtils.UNIT_TBL_NAME);
            mSQLiteDatabase.execSQL("delete from " + ITableDataCacheUtils.DATA_CACHE_TBL_NAME);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}