package vn.misa.nadat.cucuclite.ui.dish;

import vn.misa.nadat.cucuclite.items.ItemDish;

public interface IDishContract {
    interface IPresenter {
        void addDish(ItemDish itemDish);
    }

    interface IView {
        void onAddSuccess();

        void onAddError();
    }
}
