package vn.misa.nadat.cukcuklite.ui.unit;

import java.util.List;

import vn.misa.nadat.cukcuklite.database.DBItemUnitManager;
import vn.misa.nadat.cukcuklite.items.ItemUnit;

/**
 * Presenter của UnitActivity.
 *
 * @created_by nadat on 12/04/2019
 */
class UnitPresenter implements IUnitContract.IPresenter {
    private IUnitContract.IView mIView;

    /**
     * Khởi tạo UnitPresenter.
     *
     * @param iView: interface IUnitContract.IView
     * @created_by nadat on 12/04/2019
     */
    UnitPresenter(IUnitContract.IView iView) {
        mIView = iView;
    }

    /**
     * Insert ItemUnit vào DB.
     *
     * @param unitName: tên đơn vị
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void saveItemUnit(String unitName) {
        if (unitName == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            ItemUnit itemUnit = new ItemUnit(unitName);
            long rowId = DBItemUnitManager.getInstance().insertItemUnitToDB(unitName);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else if (rowId == -2) {
                mIView.showNotificationDuplicate(unitName);
            } else {
                itemUnit.setItemUnitID((int) rowId);
                mIView.showItemUnitInserted(itemUnit);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Sửa tên đơn vị của ItemUnit.
     *
     * @param id:          id của đơn vị cần sửa
     * @param newNameUnit: tên mới của đơn vị
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void updateItemUnit(int id, String newNameUnit) {
        if (newNameUnit == null) {
            mIView.showNotificationItemsEmpty();
        }
        try {
            long rowId = DBItemUnitManager.getInstance().updateItemUnitById(id, newNameUnit);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else if (rowId == -2) {
                mIView.showNotificationDuplicate(newNameUnit);
            } else {
                mIView.showItemUnitUpdated(id, newNameUnit);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Lấy ra toàn bộ ItemUnit
     *
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void getItemUnits() {
        List<ItemUnit> itemUnits = DBItemUnitManager.getInstance().getAllItemUnits();
        if (itemUnits != null) {
            mIView.showItemUnits(itemUnits);
        } else {
            mIView.showNotificationItemsEmpty();
        }
    }

    /**
     * Xóa 1 ItemUnit.
     *
     * @param itemUnit: ItemUnit được xóa
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void deleteItemUnit(ItemUnit itemUnit) {
        try {
            if (DBItemUnitManager.getInstance().deleteItemUnitById(itemUnit.getItemUnitID()) != -1) {
                mIView.hideItemUnitDeleted(itemUnit);
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Kiểm tra đơn vị đã được dùng hay chưa.
     *
     * @param itemUnit: đơn vị cần kiểm tra
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void checkItemUnitExist(ItemUnit itemUnit) {
        try {
            if (DBItemUnitManager.getInstance().isUnitUsedInTableDish(itemUnit.getItemUnitID())) {
                mIView.showNotificationUnitExist(itemUnit);
            } else {
                mIView.showItemUnitCanDeleted(itemUnit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
