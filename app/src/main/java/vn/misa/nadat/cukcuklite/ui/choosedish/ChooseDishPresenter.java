package vn.misa.nadat.cukcuklite.ui.choosedish;

import android.util.SparseIntArray;

import java.util.List;

import vn.misa.nadat.cukcuklite.database.DBItemDishManager;
import vn.misa.nadat.cukcuklite.database.DBItemSaleManager;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;

/**
 * Presenter của ChooseDishPresenter.
 *
 * @created_by nadat on 20/04/2019
 */
class ChooseDishPresenter implements IChooseDishContract.IPresenter {
    private IChooseDishContract.IView mIView;

    /**
     * Khởi tạo ChooseDishPresenter.
     *
     * @param iView: interface IChooseDishContract.IView
     * @created_by nadat on 20/04/2019
     */
    ChooseDishPresenter(IChooseDishContract.IView iView) {
        mIView = iView;
    }

    /**
     * Lấy ra danh sách món ăn.
     *
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void getItemChooseDishes() {
        List<ItemDish> itemDishes = DBItemDishManager.getInstance().getAllItemDishesActive();
        if (itemDishes == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            mIView.showItemChooseDishes(itemDishes);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Lưu ItemSale mới.
     *
     * @param itemSale:               itemSale chứa số bàn, số người, trạng thái thanh toán
     * @param listNumberDishesInSale: danh sách món ăn và số lượng kèm theo
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void saveItemSale(ItemSale itemSale, SparseIntArray listNumberDishesInSale) {
        if (itemSale == null || listNumberDishesInSale == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            long rowId = DBItemSaleManager.getInstance().insertItemSaleToDB(itemSale, listNumberDishesInSale);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else {
                mIView.showItemSaleInsertedOrUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void getItemDishesInSale(int itemSaleId) {
        List<ItemDish> itemDishes = DBItemSaleManager.getInstance().getItemDishesInSaleById(itemSaleId);
        if (itemDishes == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            mIView.showItemDishesInSale(itemDishes);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }

    }

    @Override
    public void updateItemSale(int itemSaleId, ItemSale itemSale, SparseIntArray listNumberDishesInSale) {
        if (itemSale == null || listNumberDishesInSale == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            long rowId = DBItemSaleManager.getInstance().updateItemSaleToDBById(itemSaleId, itemSale, listNumberDishesInSale);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else {
                mIView.showItemSaleInsertedOrUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void getItemSaleById(int itemSaleId) {
        try {
            ItemSale itemSale = DBItemSaleManager.getInstance().getItemSaleById(itemSaleId);
            if (itemSale == null) {
                mIView.showNotificationError();
            } else {
                mIView.showItemSaleNeedUpdate(itemSale);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void payNewItemSale(ItemSale itemSale, SparseIntArray listNumberDishesInSale, int type) {
        if (itemSale == null || listNumberDishesInSale == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            long rowId = DBItemSaleManager.getInstance().insertItemSaleToDB(itemSale, listNumberDishesInSale);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else {
                mIView.showItemSalePay((int) rowId, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void payOldItemSale(int itemSaleId, ItemSale itemSale, SparseIntArray listNumberDishesInSale, int type) {
        if (itemSale == null || listNumberDishesInSale == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            long rowId = DBItemSaleManager.getInstance().updateItemSaleToDBById(itemSaleId, itemSale, listNumberDishesInSale);
            if (rowId == -1) {
                mIView.showNotificationError();
            } else {
                mIView.showItemSalePay((int) rowId, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }
}
