package com.ngdat.cukcuklite.screen.selectunit;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Unit;

public interface IUnitContract {
    interface IView extends IBaseView {
        void showUnit(List<Unit> units, int lastSelectPosition);

        void unitNameEmpty();

        void addUnitFailed(int message);

        void addUnitSuccess(String unitId);

        void updateUnitSuccess(String unitId);

        void deleteUnitSuccess();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void onStart(String unitId);

        void addUnit(Unit unit);

        void updateUnit(Unit unit);

        void deleteUnit(String unitId);
    }
}
