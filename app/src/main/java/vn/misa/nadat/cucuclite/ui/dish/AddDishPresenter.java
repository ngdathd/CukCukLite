package vn.misa.nadat.cucuclite.ui.dish;

import vn.misa.nadat.cucuclite.databases.DBSQLiteManager;
import vn.misa.nadat.cucuclite.items.ItemDish;

public class AddDishPresenter implements IDishContract.IPresenter {
    private IDishContract.IView mIView;

    public AddDishPresenter(IDishContract.IView IView) {
        mIView = IView;
    }

    @Override
    public void addDish(ItemDish itemDish) {
        if (DBSQLiteManager.getInstance().addItemDish(itemDish)) {
            mIView.onAddSuccess();
        } else {
            mIView.onAddError();
        }
    }
}
