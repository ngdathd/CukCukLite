package vn.misa.nadat.cukcuklite.ui.unit;

import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemUnit;

/**
 * @created_by nadat on 12/04/2019
 */
public interface IUnitContract {
    interface IView {
        void showItemUnits(List<ItemUnit> itemUnits);

        void showItemUnitInserted(ItemUnit itemUnit);

        void showItemUnitUpdated(int id, String newNameUnit);

        void hideItemUnitDeleted(ItemUnit itemUnit);

        void showItemUnitCanDeleted(ItemUnit itemUnit);

        void showNotificationUnitExist(ItemUnit itemUnit);

        void showNotificationItemsEmpty();

        void showNotificationDuplicate(String unitName);

        void showNotificationError();
    }

    interface IPresenter {
        void saveItemUnit(String unit);

        void updateItemUnit(int id, String newNameUnit);

        void getItemUnits();

        void deleteItemUnit(ItemUnit itemUnit);

        void checkItemUnitExist(ItemUnit itemUnit);
    }
}
