package vn.misa.nadat.cukcuklite.ui.addeditdish;

import vn.misa.nadat.cukcuklite.database.DBItemDishManager;
import vn.misa.nadat.cukcuklite.database.DBItemUnitManager;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemUnit;

/**
 * Presenter của AddEditDishActivity.
 *
 * @created_by nadat on 16/04/2019
 */
class AddEditDishPresenter implements IAddEditContract.IPresenter {
    private IAddEditContract.IView mIView;

    /**
     * Khởi tạo AddEditDishPresenter.
     *
     * @param iView: interface IAddEditContract.IView
     * @created_by nadat on 16/04/2019
     */
    AddEditDishPresenter(IAddEditContract.IView iView) {
        mIView = iView;
    }

    /**
     * Lấy ra món ăn theo id.
     *
     * @param id: id của món ăn
     * @created_by nadat on 16/04/2019
     */
    @Override
    public void getItemDishById(int id) {
        ItemDish itemDish = DBItemDishManager.getInstance().getItemDishById(id);
        if (itemDish == null) {
            mIView.showNotificationError();
        }
        try {
            mIView.showItemDishNeedUpdate(itemDish);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Lưu món ăn mới.
     *
     * @param itemDish: món ăn mới
     * @created_by nadat on 16/04/2019
     */
    @Override
    public void saveItemDish(ItemDish itemDish) {
        if (itemDish == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            long rowId = DBItemDishManager.getInstance().insertItemDishToDB(itemDish);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else {
                mIView.showItemDishInsertedOrUpdated();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Sửa món ăn theo id.
     *
     * @param id:       id của món ăn cần sửa
     * @param itemDish: món ăn sau khi sửa
     * @created_by nadat on 16/04/2019
     */
    @Override
    public void updateItemDishById(int id, ItemDish itemDish) {
        if (itemDish == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            long rowId = DBItemDishManager.getInstance().updateItemDishById(id, itemDish);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else {
                mIView.showItemDishInsertedOrUpdated();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Lấy ra tên đơn vị đầu tiên theo chữ cái.
     *
     * @created_by nadat on 16/04/2019
     */
    @Override
    public void getFirstItemUnitName() {
        try {
            ItemUnit itemUnit = DBItemUnitManager.getInstance().getFirstItemUnit();
            if (itemUnit != null) {
                mIView.showFirstItemUnitName(itemUnit.getItemUnitID(), itemUnit.getItemUnitName());
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Lấy tên đơn vị của món ăn theo id đơn vị trong món ăn.
     *
     * @param id: id của đơn vị trong món ăn
     * @created_by nadat on 16/04/2019
     */
    @Override
    public void getUnitNameItemDishById(int id) {
        try {
            ItemUnit itemUnit = DBItemUnitManager.getInstance().getItemUnitById(id);
            if (itemUnit != null) {
                mIView.showItemUnitName(itemUnit.getItemUnitID(), itemUnit.getItemUnitName());
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Xóa món ăn theo id.
     *
     * @param id: id món ăn cần xóa
     * @created_by nadat on 16/04/2019
     */
    @Override
    public void deleteItemDishById(int id) {
        try {
            if (DBItemDishManager.getInstance().deleteItemDishById(id) != -1) {
                mIView.showItemDishInsertedOrUpdated();
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void checkItemDishExist(int itemDishId) {
        try {
            if (DBItemDishManager.getInstance().isDishUsedInTableSaleDetail(itemDishId)) {
                mIView.showNotificationDishExist();
            } else {
                mIView.showItemDishCanDeleted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
