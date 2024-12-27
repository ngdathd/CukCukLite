package vn.misa.nadat.cucuclite.ui.unit;

import vn.misa.nadat.cucuclite.databases.DBSQLiteManager;
import vn.misa.nadat.cucuclite.items.ItemUnit;

public class UnitPresenter implements IUnitContract.IPresenter {
    private IUnitContract.IView mIView;

    public UnitPresenter(IUnitContract.IView iView) {
        mIView = iView;
    }

    @Override
    public void addUnitToDatabase(String unit) {
        try {
            if (DBSQLiteManager.getInstance().insertUnitToDatabase(unit) != -1) {
                mIView.showSuccess(new ItemUnit(unit));
            } else {
                mIView.showError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
