package vn.misa.nadat.cucuclite.ui.unit;

import vn.misa.nadat.cucuclite.items.ItemUnit;

public interface IUnitContract {
    interface IPresenter {
        void addUnitToDatabase(String unit);
    }

    interface IView{
        void showSuccess(ItemUnit itemUnit);

        void showError();
    }
}
