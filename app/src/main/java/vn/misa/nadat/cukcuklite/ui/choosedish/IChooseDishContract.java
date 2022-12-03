package vn.misa.nadat.cukcuklite.ui.choosedish;

import android.util.SparseIntArray;

import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;

/**
 * @created_by nadat on 20/04/2019
 */
public interface IChooseDishContract {
    interface IView {
        void showItemChooseDishes(List<ItemDish> itemDishes);

        void showItemSaleInsertedOrUpdate();

        void showItemDishesInSale(List<ItemDish> itemDishes);

        void showNotificationError();

        void showItemSaleNeedUpdate(ItemSale itemSale);

        void showItemSalePay(int rowId, int type);
    }

    interface IPresenter {
        void getItemChooseDishes();

        void saveItemSale(ItemSale itemSale, SparseIntArray listNumberDishesInSale);

        void getItemDishesInSale(int itemSaleId);

        void updateItemSale(int itemSaleId, ItemSale itemSale, SparseIntArray listNumberDishesInSale);

        void getItemSaleById(int itemSaleId);

        void payNewItemSale(ItemSale itemSale, SparseIntArray listNumberDishesInSale, int type);

        void payOldItemSale(int itemSaleId, ItemSale itemSale, SparseIntArray listNumberDishesInSale, int type);
    }
}
