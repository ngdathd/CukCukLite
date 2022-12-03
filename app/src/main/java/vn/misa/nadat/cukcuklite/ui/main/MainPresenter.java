package vn.misa.nadat.cukcuklite.ui.main;

import java.util.List;

import vn.misa.nadat.cukcuklite.database.DBItemDishManager;
import vn.misa.nadat.cukcuklite.database.DBItemSaleManager;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;

public class MainPresenter implements IMainContract.IPresenter {
    private IMainContract.IView mIView;

    MainPresenter(IMainContract.IView iView) {
        mIView = iView;
    }

    @Override
    public void getItemSales() {
        List<ItemSale> itemSales = DBItemSaleManager.getInstance().getAllItemSalesUnpaid();
        if (itemSales != null) {
            mIView.showItemSales(itemSales);
        } else {
            mIView.showNotificationItemsEmpty();
        }
    }

    @Override
    public void getItemDishes() {
        List<ItemDish> itemDishes = DBItemDishManager.getInstance().getAllItemDishes();
        if (itemDishes != null) {
            mIView.showItemDishes(itemDishes);
        } else {
            mIView.showNotificationItemsEmpty();
        }
    }

    @Override
    public void deleteItemSale(ItemSale itemSale) {
        if (itemSale == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            if (DBItemSaleManager.getInstance().deleteItemSaleById(itemSale.getIdSale()) != -1) {
                mIView.hideItemSaleDeleted(itemSale);
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }
}
