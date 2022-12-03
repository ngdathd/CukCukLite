package vn.misa.nadat.cukcuklite.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemUnit;
import vn.misa.nadat.cukcuklite.utils.ConstantDB;

/**
 * Đối tượng quản lý bảng Item_Unit.
 *
 * @created_by nadat on 12/04/2019
 */
public class DBItemUnitManager {
    private static DBItemUnitManager mDBItemUnitManager = new DBItemUnitManager();

    public static DBItemUnitManager getInstance() {
        return mDBItemUnitManager;
    }

    /**
     * Thêm ItemUnit vào DB.
     *
     * @param itemUnitName: tên đơn vị mới
     * @return index dòng được thêm. Bằng -1 là thêm thất bại, -2 là bị trùng lặp
     * @created_by nadat on 12/04/2019
     */
    public long insertItemUnitToDB(String itemUnitName) {
        if (itemUnitName == null) {
            return -1;
        }
        try {
            if (isDuplicateItemUnitName(itemUnitName)) {
                return -2;
            }
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ConstantDB.ITEM_UNIT_NAME, itemUnitName);
            long rowId = db.insert(ConstantDB.TABLE_ITEM_UNIT, null, values);
            db.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Sửa tên đơn vị.
     *
     * @param id               : id của đơn vị cần sửa
     * @param newItemUnitName: tên mới của đơn vị
     * @return index dòng được sửa. Bằng -1 là thêm thất bại, -2 là bị trùng lặp
     * @created_by nadat on 16/04/2019
     */
    public long updateItemUnitById(int id, String newItemUnitName) {
        if (newItemUnitName == null) {
            return -1;
        }
        try {
            if (isDuplicateItemUnitName(newItemUnitName)) {
                return -2;
            }
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ConstantDB.ITEM_UNIT_NAME, newItemUnitName);
            return db.update(ConstantDB.TABLE_ITEM_UNIT, values,
                    ConstantDB.ITEM_UNIT_ID + "=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Kiểm tra itemUnitName đã tồn tại chưa
     *
     * @param itemUnitName: tên đơn vị cần kiểm tra
     * @return true nếu đã có tên đơn vị trước đó
     * @created_by nadat on 12/04/2019
     */
    private boolean isDuplicateItemUnitName(String itemUnitName) {
        try {
            SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
            Cursor cursor = db.query(ConstantDB.TABLE_ITEM_UNIT, null, ConstantDB.ITEM_UNIT_NAME + "=?",
                    new String[]{String.valueOf(itemUnitName)}, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.close();
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Kiểm tra đơn vị đã được sử dụng ở ItemDish chưa.
     *
     * @param idItemUnit: id ItemUnit cần kiểm tra
     * @return true nếu đơn vị đã được sử dụng
     * @created_by nadat on 12/04/2019
     */
    public boolean isUnitUsedInTableDish(int idItemUnit) {
        boolean isUnitUsed = false;
        try {
            String query = "SELECT * FROM " + ConstantDB.TABLE_ITEM_DISH
                    + " WHERE " + ConstantDB.ITEM_UNIT_ID + " = " + idItemUnit;
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                isUnitUsed = true;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUnitUsed;
    }

    /**
     * Lấy ra ItemUnit theo id.
     *
     * @param id: id của đơn vị cần tìm
     * @return ItemUnit
     * @created_by nadat on 15/04/2019
     */
    public ItemUnit getItemUnitById(int id) {
        try {
            SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
            Cursor cursor = db.query(ConstantDB.TABLE_ITEM_UNIT, null, ConstantDB.ITEM_UNIT_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            ItemUnit itemUnit = new ItemUnit();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                itemUnit.setItemUnitID(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_ID)));
                itemUnit.setItemUnitName(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_NAME)));
                cursor.close();
                db.close();
            } else {
                db.close();
                return null;
            }
            return itemUnit;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ra đơn vị đầu tiên có trong DB mà được sắp xếp theo ABC.
     *
     * @return ItemUnit
     * @created_by nadat on 15/04/2019
     */
    public ItemUnit getFirstItemUnit() {
        ItemUnit itemUnit = new ItemUnit();
        try {
            String selectQuery = "SELECT * FROM " + ConstantDB.TABLE_ITEM_UNIT
                    + " ORDER BY " + ConstantDB.ITEM_UNIT_NAME + " ASC LIMIT 1";
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                itemUnit.setItemUnitID(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_ID)));
                itemUnit.setItemUnitName(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_NAME)));
            }
            cursor.close();
            db.close();
            return itemUnit;
        } catch (Exception e) {
            e.printStackTrace();
            return itemUnit;
        }
    }

    /**
     * Lấy ra toàn bộ ItemUnit trong DB.
     *
     * @return danh sách ItemUnit
     * @created_by nadat on 15/04/2019
     */
    public List<ItemUnit> getAllItemUnits() {
        List<ItemUnit> itemUnits = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + ConstantDB.TABLE_ITEM_UNIT
                    + " ORDER BY " + ConstantDB.ITEM_UNIT_NAME + " ASC";
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    ItemUnit itemUnit = new ItemUnit();
                    itemUnit.setItemUnitID(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_ID)));
                    itemUnit.setItemUnitName(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_NAME)));
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

    /**
     * Xóa 1 ItemUnit
     *
     * @param itemUnitId: id của ItemUnit được xóa
     * @return index dòng được xóa
     * @created_by nadat on 15/04/2019
     */
    public int deleteItemUnitById(int itemUnitId) {
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            int rowsAffected = db.delete(ConstantDB.TABLE_ITEM_UNIT,
                    ConstantDB.ITEM_UNIT_ID + " = ?", new String[]{String.valueOf(itemUnitId)});
            db.close();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
