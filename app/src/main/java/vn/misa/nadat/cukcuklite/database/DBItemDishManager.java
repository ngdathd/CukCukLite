package vn.misa.nadat.cukcuklite.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.utils.ConstantDB;

/**
 * Đối tượng quản lý bảng Item_Dish.
 *
 * @created_by nadat on 15/04/2019
 */
public class DBItemDishManager {
    private static DBItemDishManager mDBItemDishManager = new DBItemDishManager();

    public static DBItemDishManager getInstance() {
        return mDBItemDishManager;
    }

    /**
     * Thêm ItemDish vào DB.
     *
     * @param itemDish: món ăn mới
     * @return index dòng được thêm. Bằng -1 là thêm thất bại.
     * @created_by nadat on 15/04/2019
     */
    public long insertItemDishToDB(ItemDish itemDish) {
        if (itemDish == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ConstantDB.ITEM_DISH_NAME, itemDish.getItemDishName());
            values.put(ConstantDB.PRICE_OF_DISH, itemDish.getItemDishPrice());
            values.put(ConstantDB.ITEM_UNIT_ID, itemDish.getItemUnitId());
            values.put(ConstantDB.ITEM_DISH_COLOR, itemDish.getItemDishColor());
            values.put(ConstantDB.ITEM_DISH_ICON, itemDish.getItemDishIcon());
            values.put(ConstantDB.INACTIVE, 0);
            long rowId = db.insert(ConstantDB.TABLE_ITEM_DISH, null, values);
            db.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Sửa món ăn theo id.
     *
     * @param id:       id của món ăn cần sửa
     * @param itemDish: món ăn sau khi sửa
     * @return index dòng được sửa. Bằng -1 là thêm thất bại.
     * @created_by nadat on 16/04/2019
     */
    public long updateItemDishById(int id, ItemDish itemDish) {
        if (itemDish == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ConstantDB.ITEM_DISH_NAME, itemDish.getItemDishName());
            values.put(ConstantDB.PRICE_OF_DISH, itemDish.getItemDishPrice());
            values.put(ConstantDB.ITEM_UNIT_ID, itemDish.getItemUnitId());
            values.put(ConstantDB.ITEM_DISH_COLOR, itemDish.getItemDishColor());
            values.put(ConstantDB.ITEM_DISH_ICON, itemDish.getItemDishIcon());
            values.put(ConstantDB.INACTIVE, itemDish.isInactive());
            long rowId = db.update(ConstantDB.TABLE_ITEM_DISH, values,
                    ConstantDB.ITEM_DISH_ID + "=?", new String[]{String.valueOf(id)});
            db.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Lấy ra ItemDish theo id.
     *
     * @param id: id của món ăn cần tìm
     * @return ItemDish
     * @created_by nadat on 15/04/2019
     */
    public ItemDish getItemDishById(int id) {
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.query(ConstantDB.TABLE_ITEM_DISH, null, ConstantDB.ITEM_DISH_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            ItemDish itemDish = new ItemDish();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                itemDish.setItemDishId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_DISH_ID)));
                itemDish.setItemDishName(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_NAME)));
                itemDish.setItemUnitId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_ID)));
                itemDish.setItemDishPrice(cursor.getString(cursor.getColumnIndex(ConstantDB.PRICE_OF_DISH)));
                itemDish.setInactive(cursor.getInt(cursor.getColumnIndex(ConstantDB.INACTIVE)));
                itemDish.setItemDishColor(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_COLOR)));
                itemDish.setItemDishIcon(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_ICON)));
                cursor.close();
//                db.close();
            } else {
//                db.close();
                return null;
            }
            return itemDish;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ra toàn bộ ItemDish trong DB.
     *
     * @return danh sách ItemDish
     * @created_by nadat on 15/04/2019
     */
    public List<ItemDish> getAllItemDishes() {
        List<ItemDish> itemDishes = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + ConstantDB.TABLE_ITEM_DISH
                    + " ORDER BY " + ConstantDB.ITEM_DISH_NAME + " ASC";
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    ItemDish itemDish = new ItemDish();
                    itemDish.setItemDishId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_DISH_ID)));
                    itemDish.setItemDishName(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_NAME)));
                    itemDish.setItemUnitId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_ID)));
                    itemDish.setItemDishPrice(cursor.getString(cursor.getColumnIndex(ConstantDB.PRICE_OF_DISH)));
                    itemDish.setInactive(cursor.getInt(cursor.getColumnIndex(ConstantDB.INACTIVE)));
                    itemDish.setItemDishColor(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_COLOR)));
                    itemDish.setItemDishIcon(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_ICON)));
                    itemDishes.add(itemDish);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return itemDishes;
        } catch (Exception e) {
            e.printStackTrace();
            return itemDishes;
        }
    }

    /**
     * Lấy ra toàn bộ ItemDish còn bán trong DB.
     *
     * @return danh sách ItemDish
     * @created_by nadat on 15/04/2019
     */
    public List<ItemDish> getAllItemDishesActive() {
        List<ItemDish> itemDishes = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + ConstantDB.TABLE_ITEM_DISH
                    + " WHERE " + ConstantDB.INACTIVE + " = 0"
                    + " ORDER BY " + ConstantDB.ITEM_DISH_NAME + " ASC";
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    ItemDish itemDish = new ItemDish();
                    itemDish.setItemDishId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_DISH_ID)));
                    itemDish.setItemDishName(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_NAME)));
                    itemDish.setItemUnitId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_ID)));
                    itemDish.setItemDishPrice(cursor.getString(cursor.getColumnIndex(ConstantDB.PRICE_OF_DISH)));
                    itemDish.setInactive(cursor.getInt(cursor.getColumnIndex(ConstantDB.INACTIVE)));
                    itemDish.setItemDishColor(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_COLOR)));
                    itemDish.setItemDishIcon(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_ICON)));
                    itemDishes.add(itemDish);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return itemDishes;
        } catch (Exception e) {
            e.printStackTrace();
            return itemDishes;
        }
    }

    /**
     * Xóa 1 ItemDish
     *
     * @param itemDishId: id của ItemDish được xóa
     * @return index dòng được xóa
     * @created_by nadat on 15/04/2019
     */
    public int deleteItemDishById(int itemDishId) {
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            int rowsAffected = db.delete(ConstantDB.TABLE_ITEM_DISH,
                    ConstantDB.ITEM_DISH_ID + " = ?", new String[]{String.valueOf(itemDishId)});
            db.close();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Kiểm tra món ăn đã được sử dụng hay chưa.
     *
     * @param itemDishId: id của món ăn
     * @return true nếu món ăn đã được sử dụng
     * @created_by nadat on 21/04/2019
     */
    public boolean isDishUsedInTableSaleDetail(int itemDishId) {
        boolean isDishUsed = false;
        try {
            String query = "SELECT * FROM " + ConstantDB.TABLE_ITEM_SALE_DETAIL
                    + " WHERE " + ConstantDB.ITEM_DISH_ID + " = " + itemDishId;
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                isDishUsed = true;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDishUsed;
    }
}
