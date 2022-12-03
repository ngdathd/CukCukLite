package vn.misa.nadat.cukcuklite.ui.payment;

import android.util.SparseIntArray;

import java.util.List;

import vn.misa.nadat.cukcuklite.database.DBItemSaleManager;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;

class PaymentPresenter implements IPaymentContract.IPresenter {
    private IPaymentContract.IView mIView;

    PaymentPresenter(IPaymentContract.IView IView) {
        mIView = IView;
    }

    @Override
    public void getItemSalePaymentById(int idSale) {
        ItemSale itemSale = DBItemSaleManager.getInstance().getItemSaleById(idSale);
        if (itemSale == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            mIView.showItemSalePayment(itemSale);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void getItemDishesInSalePaymentById(int idSale) {
        List<ItemDish> itemDishes = DBItemSaleManager.getInstance().getItemDishesInSaleById(idSale);
        if (itemDishes == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            mIView.showItemDishesPayment(itemDishes);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void payItemSalePayed(int saleIdPay, String datePayed) {
        try {
            if (DBItemSaleManager.getInstance().payItemSalePayedInDB(saleIdPay, datePayed) != -1) {
                mIView.showItemSalePayed();
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void deleteItemSalePayment(int idSale) {
        try {
            if (DBItemSaleManager.getInstance().deleteItemSaleById(idSale) != -1) {
                mIView.backChooseDishAct();
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    @Override
    public void restoreItemSalePayment(int saleIdPay, ItemSale itemSale, SparseIntArray sparseIntArray) {
        try {
            if (DBItemSaleManager.getInstance().updateItemSaleToDBById(saleIdPay, itemSale, sparseIntArray) != -1) {
                mIView.backChooseDishAct();
            } else {
                mIView.showNotificationError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }
}
