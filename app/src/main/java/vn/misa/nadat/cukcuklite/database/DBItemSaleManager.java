package vn.misa.nadat.cukcuklite.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;
import vn.misa.nadat.cukcuklite.utils.ConstantDB;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Đối tượng quản lý bán hàng
 *
 * @created_by nadat on 24/04/2019
 */
public class DBItemSaleManager {
    private static DBItemSaleManager mDBItemSaleManager = new DBItemSaleManager();

    public static DBItemSaleManager getInstance() {
        return mDBItemSaleManager;
    }

    /**
     * Thêm ItemSale
     *
     * @param itemSale:               itemSale có 1 số thông tin ban đầu
     * @param listNumberDishesInSale: danh sách món ăn và số lượng
     * @return id dòng vừa thêm. -1 là thêm thất bại
     * @created_by nadat on 24/04/2019
     */
    public long insertItemSaleToDB(ItemSale itemSale, SparseIntArray listNumberDishesInSale) {
        if (itemSale == null) {
            return -1;
        }
        try {
            SQLiteDatabase dbSale = DBHelper.getInstance().getWritableDatabase();
            SQLiteDatabase dbSaleDetail = DBHelper.getInstance().getWritableDatabase();

            ContentValues valuesItemSale = new ContentValues();
            ContentValues valuesItemSaleDetail = new ContentValues();

            valuesItemSale.put(ConstantDB.PAYMENT_STATUS, itemSale.getPaymentStatus());
            valuesItemSale.put(ConstantDB.ITEM_SALE_COLOR, itemSale.getItemSaleColor());
            valuesItemSale.put(ConstantDB.NUMBER_OF_PEOPLE, itemSale.getNumberOfPerson());
            valuesItemSale.put(ConstantDB.NUMBER_OF_TABLE, itemSale.getNumberOfTable());
            long rowId = dbSale.insert(ConstantDB.TABLE_ITEM_SALE, null, valuesItemSale);

            for (int i = 0; i < listNumberDishesInSale.size(); i++) {
                int itemDishId = listNumberDishesInSale.keyAt(i);
                int numberOfDish = listNumberDishesInSale.get(itemDishId);
                valuesItemSaleDetail.put(ConstantDB.ITEM_SALE_ID, rowId);
                valuesItemSaleDetail.put(ConstantDB.ITEM_DISH_ID, itemDishId);
                valuesItemSaleDetail.put(ConstantDB.NUMBER_OF_DISH, numberOfDish);
                ItemDish itemDish = DBItemDishManager.getInstance().getItemDishById(itemDishId);
                valuesItemSaleDetail.put(ConstantDB.TOTAL_MONEY_DISHES, numberOfDish * PriceUtils.formatPriceToInt(itemDish.getItemDishPrice()));
                dbSaleDetail.insert(ConstantDB.TABLE_ITEM_SALE_DETAIL, null, valuesItemSaleDetail);
            }
            dbSale.close();
            dbSaleDetail.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Lấy ra các ItemSale chưa thanh toán.
     *
     * @return danh sách ItemSale
     * @created_by nadat on 24/04/2019
     */
    public List<ItemSale> getAllItemSalesUnpaid() {
        List<ItemSale> itemSales = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + ConstantDB.TABLE_ITEM_SALE
                    + " LEFT JOIN " + ConstantDB.TABLE_ITEM_SALE_DETAIL
                    + " ON "
                    + ConstantDB.TABLE_ITEM_SALE + "." + ConstantDB.ITEM_SALE_ID
                    + "="
                    + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.ITEM_SALE_ID
                    + " LEFT JOIN " + ConstantDB.TABLE_ITEM_DISH
                    + " ON "
                    + ConstantDB.TABLE_ITEM_DISH + "." + ConstantDB.ITEM_DISH_ID
                    + "="
                    + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.ITEM_DISH_ID
                    + " WHERE " + ConstantDB.PAYMENT_STATUS + " = 0";
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            ItemSale itemSale = new ItemSale();
            int amount = 0;
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
            int check = 0;
            if (cursor.moveToFirst()) {
                int indexCursor = cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_SALE_ID));

                itemSale.setSaleId(indexCursor);
                itemSale.setNumberOfTable(cursor.getString(cursor.getColumnIndex(ConstantDB.NUMBER_OF_TABLE)));
                itemSale.setNumberOfPerson(cursor.getString(cursor.getColumnIndex(ConstantDB.NUMBER_OF_PEOPLE)));
                itemSale.setPaymentStatus(cursor.getInt(cursor.getColumnIndex(ConstantDB.PAYMENT_STATUS)));
                itemSale.setItemSaleColor(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_SALE_COLOR)));
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_SALE_ID));
                    if (id != indexCursor) {
                        indexCursor = id;
                        itemSale.setTotalMoney(PriceUtils.formatPrice(amount + ""));
                        itemSale.setContent(stringBuilder);
                        itemSales.add(itemSale);

                        itemSale = new ItemSale();
                        amount = 0;
                        stringBuilder = new SpannableStringBuilder();
                        check = 0;

                        itemSale.setSaleId(id);
                        itemSale.setNumberOfTable(cursor.getString(cursor.getColumnIndex(ConstantDB.NUMBER_OF_TABLE)));
                        itemSale.setNumberOfPerson(cursor.getString(cursor.getColumnIndex(ConstantDB.NUMBER_OF_PEOPLE)));
                        itemSale.setPaymentStatus(cursor.getInt(cursor.getColumnIndex(ConstantDB.PAYMENT_STATUS)));
                        itemSale.setItemSaleColor(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_SALE_COLOR)));
                    }
                    String dishName = cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_NAME));
                    String dishPrice = cursor.getString(cursor.getColumnIndex(ConstantDB.PRICE_OF_DISH));

                    amount = amount + PriceUtils.formatPriceToInt(dishPrice) * cursor.getInt(cursor.getColumnIndex(ConstantDB.NUMBER_OF_DISH));

                    SpannableString spannableString = new SpannableString(dishName + " (" + cursor.getString(cursor.getColumnIndex(ConstantDB.NUMBER_OF_DISH)) + ")");
                    spannableString
                            .setSpan(new RelativeSizeSpan(0.8f), dishName.length() + 1, spannableString.length(), 0);
                    spannableString
                            .setSpan(new ForegroundColorSpan(Color.parseColor("#039be5")),
                                    dishName.length() + 1, spannableString.length(), 0);
                    if (check != 0) {
                        stringBuilder.append(", ");
                        stringBuilder.append(spannableString);
                    } else {
                        stringBuilder.append(spannableString);
                        check++;
                    }

                } while (cursor.moveToNext());
                itemSale.setTotalMoney(PriceUtils.formatPrice(amount + ""));
                itemSale.setContent(stringBuilder);
                itemSales.add(itemSale);
            }
            cursor.close();
            db.close();
            return itemSales;
        } catch (Exception e) {
            e.printStackTrace();
            return itemSales;
        }
    }

    /**
     * Lấy ra danh sách món ăn trong ItemSale
     *
     * @param id: id của ItemSale
     * @return danh sách món ăn
     * @created_by nadat on 24/04/2019
     */
    public List<ItemDish> getItemDishesInSaleById(int id) {
        List<ItemDish> itemDishes = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + ConstantDB.TABLE_ITEM_DISH
                    + " INNER JOIN " + ConstantDB.TABLE_ITEM_SALE_DETAIL
                    + " ON "
                    + ConstantDB.TABLE_ITEM_DISH + "." + ConstantDB.ITEM_DISH_ID
                    + "="
                    + ConstantDB.TABLE_ITEM_SALE_DETAIL + "." + ConstantDB.ITEM_DISH_ID
                    + " WHERE " + ConstantDB.ITEM_SALE_ID + "=" + id;
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ItemDish itemDish = new ItemDish();
                    itemDish.setItemDishId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_DISH_ID)));
                    itemDish.setItemDishName(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_DISH_NAME)));
                    itemDish.setItemUnitId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_UNIT_ID)));
                    String priceOfDish = cursor.getString(cursor.getColumnIndex(ConstantDB.PRICE_OF_DISH));
                    itemDish.setItemDishPrice(priceOfDish);
                    itemDish.setInactive(cursor.getInt(cursor.getColumnIndex(ConstantDB.INACTIVE)));
                    int useCount = cursor.getInt(cursor.getColumnIndex(ConstantDB.NUMBER_OF_DISH));
                    itemDish.setUseCount(useCount);
                    itemDish.setTotalPrice(PriceUtils.formatPrice(useCount * PriceUtils.formatPriceToInt(priceOfDish) + ""));
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
     * Sửa ItemSale
     *
     * @param itemSaleId:             id ItemSale cần sửa
     * @param itemSale:               ItemSale chứa 1 số thông tin
     * @param listNumberDishesInSale: danh sách món ăn kèm số lượng
     * @return id dòng vừa sửa. -1 là sửa thất bại
     */
    public long updateItemSaleToDBById(int itemSaleId, ItemSale itemSale, SparseIntArray listNumberDishesInSale) {
        if (itemSale == null) {
            return -1;
        }
        try {
            SQLiteDatabase dbSale = DBHelper.getInstance().getWritableDatabase();
            SQLiteDatabase dbSaleDetail = DBHelper.getInstance().getWritableDatabase();
            SQLiteDatabase dbDeleteSaleDetail = DBHelper.getInstance().getWritableDatabase();

            ContentValues valuesItemSale = new ContentValues();
            ContentValues valuesItemSaleDetail = new ContentValues();

            valuesItemSale.put(ConstantDB.PAYMENT_STATUS, itemSale.getPaymentStatus());
            valuesItemSale.put(ConstantDB.ITEM_SALE_COLOR, itemSale.getItemSaleColor());
            valuesItemSale.put(ConstantDB.NUMBER_OF_PEOPLE, itemSale.getNumberOfPerson());
            valuesItemSale.put(ConstantDB.NUMBER_OF_TABLE, itemSale.getNumberOfTable());
            long rowId = dbSale.update(ConstantDB.TABLE_ITEM_SALE, valuesItemSale,
                    ConstantDB.ITEM_SALE_ID + "=?", new String[]{String.valueOf(itemSaleId)});

            dbDeleteSaleDetail.delete(ConstantDB.TABLE_ITEM_SALE_DETAIL,
                    ConstantDB.ITEM_SALE_ID + " = ?", new String[]{String.valueOf(itemSaleId)});
            for (int i = 0; i < listNumberDishesInSale.size(); i++) {
                int itemDishId = listNumberDishesInSale.keyAt(i);
                int numberOfDish = listNumberDishesInSale.get(itemDishId);
                valuesItemSaleDetail.put(ConstantDB.ITEM_SALE_ID, itemSaleId);
                valuesItemSaleDetail.put(ConstantDB.ITEM_DISH_ID, itemDishId);
                valuesItemSaleDetail.put(ConstantDB.NUMBER_OF_DISH, numberOfDish);
                ItemDish itemDish = DBItemDishManager.getInstance().getItemDishById(itemDishId);
                valuesItemSaleDetail.put(ConstantDB.TOTAL_MONEY_DISHES, numberOfDish * PriceUtils.formatPriceToInt(itemDish.getItemDishPrice()));
                dbSaleDetail.insert(ConstantDB.TABLE_ITEM_SALE_DETAIL, null, valuesItemSaleDetail);
            }
            dbSale.close();
            dbSaleDetail.close();
            dbDeleteSaleDetail.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Lấy ra 1 ItemSaLe
     *
     * @param itemSaleId:id ItemSale
     * @return ItemSale
     * @created_by nadat on 24/04/2019
     */
    public ItemSale getItemSaleById(int itemSaleId) {
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            Cursor cursor = db.query(ConstantDB.TABLE_ITEM_SALE, null, ConstantDB.ITEM_SALE_ID + "=?",
                    new String[]{String.valueOf(itemSaleId)}, null, null, null, null);
            ItemSale itemSale = new ItemSale();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                itemSale.setSaleId(cursor.getInt(cursor.getColumnIndex(ConstantDB.ITEM_SALE_ID)));
                itemSale.setPaymentStatus(cursor.getInt(cursor.getColumnIndex(ConstantDB.PAYMENT_STATUS)));
                itemSale.setItemSaleColor(cursor.getString(cursor.getColumnIndex(ConstantDB.ITEM_SALE_COLOR)));
                itemSale.setNumberOfTable(cursor.getString(cursor.getColumnIndex(ConstantDB.NUMBER_OF_TABLE)));
                itemSale.setNumberOfPerson(cursor.getString(cursor.getColumnIndex(ConstantDB.NUMBER_OF_PEOPLE)));
                cursor.close();
                db.close();
            } else {
                db.close();
                return null;
            }
            return itemSale;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Xóa 1 ItemSale
     *
     * @param itemSaleId: id ItemSale cần xóa
     * @return id dòng vừa xóa. -1 là xóa thất bại
     */
    public long deleteItemSaleById(int itemSaleId) {
        try {
            SQLiteDatabase dbDeleteSaleDetail = DBHelper.getInstance().getWritableDatabase();
            dbDeleteSaleDetail.delete(ConstantDB.TABLE_ITEM_SALE_DETAIL,
                    ConstantDB.ITEM_SALE_ID + " = ?", new String[]{String.valueOf(itemSaleId)});
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            int rowsAffected = db.delete(ConstantDB.TABLE_ITEM_SALE,
                    ConstantDB.ITEM_SALE_ID + " = ?", new String[]{String.valueOf(itemSaleId)});
            dbDeleteSaleDetail.close();
            db.close();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Thanh toán 1 ItemSale
     *
     * @param saleIdPay: id của ItemSale
     * @param datePayed: thời điểm thanh toán
     * @return id dòng vừa thanh toán. -1 là thanh toán thất bại
     */
    public long payItemSalePayedInDB(int saleIdPay, String datePayed) {
        try {
            SQLiteDatabase dbSale = DBHelper.getInstance().getWritableDatabase();
            ContentValues valuesItemSale = new ContentValues();
            valuesItemSale.put(ConstantDB.PAYMENT_STATUS, 1);
            valuesItemSale.put(ConstantDB.PAYMENT_DATE, datePayed);
            long rowId = dbSale.update(ConstantDB.TABLE_ITEM_SALE, valuesItemSale,
                    ConstantDB.ITEM_SALE_ID + "=?", new String[]{String.valueOf(saleIdPay)});
            dbSale.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
