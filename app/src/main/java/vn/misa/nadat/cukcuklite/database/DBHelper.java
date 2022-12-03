package vn.misa.nadat.cukcuklite.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.misa.nadat.cukcuklite.CukCukLiteApp;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.ConstantDB;

/**
 * Class tạo DB.
 *
 * @created_by nadat on 15/04/2019
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mDBHelper = new DBHelper();

    private DBHelper() {
        super(CukCukLiteApp.getInstance(), ConstantDB.DATABASE_NAME, null, 1);
    }

    public static DBHelper getInstance() {
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableItemUnit =
                "CREATE TABLE " + ConstantDB.TABLE_ITEM_UNIT + " ("
                        + ConstantDB.ITEM_UNIT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + ConstantDB.ITEM_UNIT_NAME + " VARCHAR(255) NOT NULL) ";

        String sqlCreateTableItemDish =
                "CREATE TABLE " + ConstantDB.TABLE_ITEM_DISH + " ("
                        + ConstantDB.ITEM_DISH_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + ConstantDB.ITEM_DISH_NAME + " VARCHAR(255) NOT NULL, "
                        + ConstantDB.ITEM_UNIT_ID + " INTEGER NOT NULL, "
                        + ConstantDB.PRICE_OF_DISH + " VARCHAR(255) NOT NULL DEFAULT 0, "
                        + ConstantDB.INACTIVE + " INTEGER NOT NULL DEFAULT 0, "
                        + ConstantDB.ITEM_DISH_COLOR + " VARCHAR(255) NOT NULL, "
                        + ConstantDB.ITEM_DISH_ICON + " VARCHAR(255) NOT NULL)";

        String sqlCreateTableItemSale =
                "CREATE TABLE " + ConstantDB.TABLE_ITEM_SALE + " ("
                        + ConstantDB.ITEM_SALE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + ConstantDB.PAYMENT_STATUS + " INTEGER NOT NULL DEFAULT 0, "
                        + ConstantDB.ITEM_SALE_COLOR + " VARCHAR(255), "
                        + ConstantDB.NUMBER_OF_PEOPLE + " VARCHAR(255), "
                        + ConstantDB.NUMBER_OF_TABLE + " VARCHAR(255), "
                        + ConstantDB.PAYMENT_DATE + " DATETIME )";

        String sqlCreateTableItemSaleDetail =
                "CREATE TABLE " + ConstantDB.TABLE_ITEM_SALE_DETAIL + " ("
                        + ConstantDB.ITEM_SALE_ID + " INTEGER NOT NULL, "
                        + ConstantDB.ITEM_DISH_ID + " INTEGER NOT NULL, "
                        + ConstantDB.NUMBER_OF_DISH + " INTEGER, "
                        + ConstantDB.TOTAL_MONEY_DISHES + " INTEGER, "
                        + "PRIMARY KEY (" + ConstantDB.ITEM_SALE_ID + "," + ConstantDB.ITEM_DISH_ID + "))";

        db.execSQL(sqlCreateTableItemUnit);
        db.execSQL(sqlCreateTableItemDish);
        db.execSQL(sqlCreateTableItemSale);
        db.execSQL(sqlCreateTableItemSaleDetail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConstantDB.TABLE_ITEM_UNIT);
        db.execSQL("DROP TABLE IF EXISTS " + ConstantDB.TABLE_ITEM_DISH);
        db.execSQL("DROP TABLE IF EXISTS " + ConstantDB.TABLE_ITEM_SALE);
        db.execSQL("DROP TABLE IF EXISTS " + ConstantDB.TABLE_ITEM_SALE_DETAIL);
        onCreate(db);
    }

    /**
     * Phương thức copy database
     *
     * @return - copy database có thành công hay không
     * @created_by nadat on 24/04/2019
     */
    public boolean copyDatabase() {
        try {
            InputStream inputStream = CukCukLiteApp.getInstance().getAssets().open("database/" + Constant.DB_NAME);
            String outFileName = Constant.DB_LOCATION + Constant.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
