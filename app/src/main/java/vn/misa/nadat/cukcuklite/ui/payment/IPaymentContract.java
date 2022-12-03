package vn.misa.nadat.cukcuklite.ui.payment;

import android.util.SparseIntArray;

import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;

public interface IPaymentContract {
    interface IView {
        void showItemSalePayment(ItemSale itemSale);

        void showItemDishesPayment(List<ItemDish> itemDishes);

        void showNotificationError();

        void showItemSalePayed();

        void backChooseDishAct();
    }

    interface IPresenter {
        void getItemSalePaymentById(int idSale);

        void getItemDishesInSalePaymentById(int idSale);

        void payItemSalePayed(int saleIdPay, String datePayed);

        void deleteItemSalePayment(int idSale);

        void restoreItemSalePayment(int saleIdPay, ItemSale itemSale, SparseIntArray sparseIntArray);
    }
}
