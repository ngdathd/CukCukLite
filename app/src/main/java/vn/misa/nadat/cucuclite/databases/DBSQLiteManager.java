package vn.misa.nadat.cucuclite.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cucuclite.MvpApp;
import vn.misa.nadat.cucuclite.items.ItemDish;
import vn.misa.nadat.cucuclite.items.ItemUnit;

public class DBSQLiteManager extends SQLiteOpenHelper {
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_COST = "cost";
    static final String COLUMN_SELLING = "selling";
    static final String COLUMN_COLOR = "color";
    static final String COLUMN_ICON = "icon";
    static final String COLUMN_UNIT = "unit";
    private static final String DATABASE_NAME = "DB_SQLITE_UNIT";
    private static final String TABLE_NAME = "unit";
    private static final String UNIT = "unit";
    private static final String TABLE_NAME_DISH = "DISH";
    private static DBSQLiteManager mDBSQLiteManager = new DBSQLiteManager();

    private DBSQLiteManager() {
        super(MvpApp.getInstance(), DATABASE_NAME, null, 1);
    }

    public static DBSQLiteManager getInstance() {
        return mDBSQLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                UNIT + " TEXT primary key)";
        db.execSQL(sqlQuery);
        String script = "CREATE TABLE " + TABLE_NAME_DISH + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                + COLUMN_COST + " TEXT," + COLUMN_SELLING + " TEXT,"
                + COLUMN_COLOR + " INTEGER," + COLUMN_ICON + " TEXT,"
                + COLUMN_UNIT + " TEXT" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertUnitToDatabase(String unit) {
        if (unit == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UNIT, unit);
            long rowId = db.insert(TABLE_NAME, null, values);
            db.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<ItemUnit> getAllItemUnits() {
        List<ItemUnit> itemUnits = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    ItemUnit itemUnit = new ItemUnit();
                    itemUnit.setUnit(cursor.getString(cursor.getColumnIndex(UNIT)));
                    itemUnits.add(itemUnit);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return itemUnits;
        } catch (Exception e) {
            e.printStackTrace();
            return itemUnits;
        }
    }

    public ArrayList<ItemDish> getAllItemDish() {
        ArrayList<ItemDish> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    ItemDish itemDish = new ItemDish();
                    itemDish.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    itemDish.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_COST)));
                    itemDish.setImgColor(cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR)));
                    itemDish.setImgIcon(cursor.getString(cursor.getColumnIndex(COLUMN_ICON)));
                    itemDish.setUnit(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT)));
                    list.add(itemDish);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addItemDish(ItemDish itemsName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, itemsName.getName());
            values.put(COLUMN_COST, itemsName.getPrice());
            values.put(COLUMN_COLOR, itemsName.getImgColor());
            values.put(COLUMN_ICON, itemsName.getImgIcon());
            values.put(COLUMN_UNIT, itemsName.getUnit());
            boolean result = db.insert(TABLE_NAME, null, values) != -1 ? true : false;
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ItemDish updateItemDish(ItemDish itemDish) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, itemDish.getName());
            values.put(COLUMN_COST, itemDish.getPrice());
            values.put(COLUMN_COLOR, itemDish.getImgColor());
            values.put(COLUMN_ICON, itemDish.getImgIcon());
            values.put(COLUMN_UNIT, itemDish.getUnit());
            int result = db.update(TABLE_NAME, values, COLUMN_ID + "=" + String.valueOf(itemDish.getName()), null);
            db.close();
            if (result != -1) return itemDish;
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int deleteFoodItems(ItemDish itemDish) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(itemDish.getName())});
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
