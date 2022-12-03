package vn.misa.nadat.cukcuklite.ui.addeditdish;

import vn.misa.nadat.cukcuklite.items.ItemDish;

/**
 * @created_by nadat on 16/04/2019
 */
public interface IAddEditContract {
    interface IView {
        void showItemDishNeedUpdate(ItemDish itemDish);

        void showFirstItemUnitName(int itemUnitId, String itemUnitName);

        void showNotificationError();

        void showItemDishInsertedOrUpdated();

        void showItemUnitName(int itemUnitId, String itemUnitName);

        void showItemDishCanDeleted();

        void showNotificationDishExist();
    }

    interface IPresenter {
        void getItemDishById(int id);

        void saveItemDish(ItemDish itemDish);

        void updateItemDishById(int id, ItemDish itemDish);

        void getFirstItemUnitName();

        void getUnitNameItemDishById(int id);

        void deleteItemDishById(int id);

        void checkItemDishExist(int itemDishId);
    }
}
