package com.ngdat.cukcuklite.data.local.unit;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import com.ngdat.cukcuklite.data.cukcukenum.ResultEnum;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.data.local.database.SQLiteDBManager;
import com.ngdat.cukcuklite.data.models.Unit;

/**
 * Lớp thao tác với các đơn vị món ăn trong cơ sở dũ liệu
 * Created at 27/03/2019
 */
public class UnitDataSource implements IUnitDataSource, IDBUtils.ITableUnitUtils {

    private static final String TAG = "UnitDataSource";
    private static UnitDataSource sInstance;
    private SQLiteDBManager mSQLiteDBManager;
    private List<Unit> mUnits;

    /**
     * Phương thức khởi tạo cho đối tượng UnitDataSource, và danh sách đơn vị
     * Created at 27/03/2019
     */
    private UnitDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
        mUnits = new ArrayList<>();
    }

    /**
     * Phương thức khởi tạo cho đối tượng UnitDataSource truy cập mọi nơi
     * Created at 27/03/2019
     */
    public static UnitDataSource getInstance() {
        if (sInstance == null) {
            synchronized (UnitDataSource.class) {
                if (sInstance == null) {
                    sInstance = new UnitDataSource();
                }
            }
        }
        return sInstance;
    }

    /**
     * Phương thức thêm đơn vị món ăn
     * Created at 27/03/2019
     *
     * @param unit - đơn vị món ăn
     * @return - thêm đơn vị món ăn thàn công hay thất bại
     */
    @Override
    public boolean addUnit(Unit unit) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_UNIT_ID, unit.getUnitId());
            contentValues.put(COLUMN_UNIT_NAME, unit.getUnitName());
            return mSQLiteDBManager.addNewRecord(UNIT_TBL_NAME, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức thêm mới đơn vị món ăn có kiểm tra sự tồn tại của đơn vị
     * Created at 27/03/2019
     *
     * @param unit - Đơn vị
     * @return - thêm mới đơn vị món ăn thành công, thất bại hay đã tồn tại đơn vị
     */
    @Override
    public ResultEnum addUnitToDatabase(Unit unit) {
        if (unit != null) {
            //kiểm tra tên đơn vị đã tồn tại hay chưa
            if (isUnitIfExists(unit.getUnitName())) {
                return ResultEnum.Exists;
            } else {
                if (addUnit(unit)) {
                    mUnits.add(unit);
                    return ResultEnum.Success;
                }
            }
        }
        return ResultEnum.SomethingWentWrong;
    }

    /**
     * Phương thức xóa đơn vị thông qua id của đơn vị
     * Created at 27/03/2019
     *
     * @param unitId - đơn vị
     * @return - xóa đơn vị thành công hay thất bại
     */
    @Override
    public boolean deleteUnitById(String unitId) {
        try {
            if (mSQLiteDBManager.deleteRecord(UNIT_TBL_NAME,
                    COLUMN_UNIT_ID + "=?", new String[]{unitId})) {
                if (mUnits != null) {
                    int size = mUnits.size();
                    for (int i = 0; i < size; i++) {
                        if (mUnits.get(i).getUnitId().equals(unitId)) {
                            mUnits.remove(i);
                            break;
                        }
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật đơn vị
     * Created at 27/03/2019
     *
     * @param unit - đơn vị
     * @return - cập nhật đơn vị thành công hay thất bại
     */
    @Override
    public boolean updateUnit(Unit unit) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_UNIT_NAME, unit.getUnitName());
            return mSQLiteDBManager.updateRecord(UNIT_TBL_NAME, contentValues,
                    COLUMN_UNIT_ID + "=?", new String[]{unit.getUnitId()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật đơn vị có kiểm tra tên của đơn vị
     *
     * @param unit - đơn vị
     * @return - cập nhật đơn vị thành công, thất bại
     */
    @Override
    public ResultEnum updateUnitToDatabase(Unit unit) {
        try {
            if (mUnits != null) {
                int size = mUnits.size();
                boolean unitNameIsExists = false;
                for (int i = 0; i < size; i++) {
                    if (mUnits.get(i).getUnitName().equalsIgnoreCase(unit.getUnitName())
                            && (!mUnits.get(i).getUnitId().equals(unit.getUnitId()))) {
                        unitNameIsExists = true;
                        break;
                    }
                }
                if (unitNameIsExists) {
                    return ResultEnum.Exists;
                } else {
                    if (updateUnit(unit)) {
                        for (int i = 0; i < size; i++) {
                            if (mUnits.get(i).getUnitId().equals(unit.getUnitId())) {
                                mUnits.set(i, unit);
                                break;
                            }
                        }
                        return ResultEnum.Success;
                    }
                }
                return ResultEnum.SomethingWentWrong;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultEnum.SomethingWentWrong;
    }

    /**
     * Phương thức lấy tất cả tên các đơn vị trong đã có trong cơ sở dữ liệu
     * Created at 27/03/2019
     *
     * @return - danh sách tên các đơn vị
     */
    @Override
    public List<String> getAllUnitName() {
        List<String> unitNames = new ArrayList<>();
        try {
            if (mUnits != null) {
                for (Unit unit : mUnits) {
                    unitNames.add(unit.getUnitName());
                }
                return unitNames;
            }
            Cursor cursor = mSQLiteDBManager.getRecords("select " + COLUMN_UNIT_NAME + " from " + UNIT_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String unitName = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME));
                unitNames.add(unitName);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitNames;
    }

    /**
     * Phương thức lấy ra tất cả các đơn vị có trong cở sở dữ liệu
     * Created at 27/03/2019
     *
     * @return - danh sách đơn vị
     */
    @Override
    public List<Unit> getAllUnit() {
        if (mUnits != null && mUnits.size() > 0) {
            return mUnits;
        }
        List<Unit> units = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + UNIT_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Unit unit = new Unit(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME)));
                units.add(unit);
                cursor.moveToNext();
            }
            mUnits.clear();
            mUnits.addAll(units);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return units;
    }

    /**
     * Phương thức kiểm tra đơn vị đã tồn tại trong cơ sở dữ liệu hay chưa
     * Created at 27/03/2019
     *
     * @param unitName - đơn vị
     * @return - đơn vị đã hay chưa tồn tại
     */
    @Override
    public boolean isUnitIfExists(String unitName) {
        try {
            boolean isExists = false;
            if (mUnits != null) {
                for (Unit unit : mUnits) {
                    if (unit.getUnitName().equalsIgnoreCase(unitName)) {
                        isExists = true;
                        break;
                    }
                }
                return isExists;
            }
            Cursor cursor = mSQLiteDBManager.getRecords(UNIT_TBL_NAME, new String[]{COLUMN_UNIT_NAME},
                    "lower(" + COLUMN_UNIT_NAME + ")" + " = ? ", new String[]{unitName.toLowerCase()},
                    null, null, null);
            int unitNameRecord = cursor.getCount();
            cursor.close();
            return unitNameRecord > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức lấy đơn vị với id của dơn vị
     * Created at 05/04/2019
     *
     * @param unitId - id của đơn vị
     * @return - đơn vị
     */
    @Override
    public Unit getUnitById(String unitId) {
        try {
            Unit unit = null;
            if (mUnits != null && mUnits.size() > 0) {
                for (Unit u : mUnits) {
                    if (u.getUnitId().equalsIgnoreCase(unitId)) {
                        unit = u;
                        break;
                    }
                }
                return unit;
            }
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + UNIT_TBL_NAME + " where " + COLUMN_UNIT_ID + "=" + unitId, null);
            cursor.moveToFirst();
            unit = new Unit(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME)));
            cursor.close();
            return unit;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức lấy tên đơn vị qua Id
     * Created at 09/04/2019
     *
     * @param unitId - id của đơn vị
     * @return - tên đơn vị
     */
    @Override
    public String getUnitNameById(String unitId) {
        try {
            String unitName = null;
            if (mUnits != null && mUnits.size() > 0 && unitId != null) {
                for (Unit u : mUnits) {
                    if (u.getUnitId().equals(unitId)) {
                        unitName = u.getUnitName();
                        break;
                    }
                }
                return unitName;
            }
            Cursor cursor = mSQLiteDBManager.getRecords("select " + COLUMN_UNIT_NAME + " from " + UNIT_TBL_NAME + " where " + COLUMN_UNIT_ID + "='" + unitId + "'", null);
            cursor.moveToFirst();
            unitName = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME));
            cursor.close();
            return unitName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xóa toàn bộ đơn vị có sẵn trong database
     * Created at 09/04/2019
     */
    @Override
    public boolean deleteAllUnit() {
        boolean deleteSuccess = false;
        try {
            deleteSuccess = mSQLiteDBManager.deleteRecord(UNIT_TBL_NAME, null, null);
            if (deleteSuccess) {
                removeAllCache();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteSuccess;
    }

    public void removeAllCache() {
        try {
            if (mUnits != null) {
                mUnits.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
