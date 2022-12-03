package vn.misa.nadat.cukcuklite.ui.main;

import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;

public interface IMainContract {
    interface IView {
        void showItemSales(List<ItemSale> itemSales);

        void showItemDishes(List<ItemDish> itemDishes);

        void showNotificationItemsEmpty();

        void showNotificationError();

        void hideItemSaleDeleted(ItemSale itemSale);
    }

    interface IPresenter {
        void getItemSales();

        void getItemDishes();

        void deleteItemSale(ItemSale itemSale);
    }
}
