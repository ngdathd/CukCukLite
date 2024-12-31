package com.ngdat.cukcuklite.screen.adddish;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.models.Unit;

/**
 * MVP interface cho màn hình thêm món ăn
 * Created at 9/04/2019
 */
public interface IAddDishContract {
    interface IView extends IBaseView {

        void dishNameEmpty();

        void addDishSuccess();

        void addDishFailed(int error);

        void setUnit(Unit unit);

        void upDateDishSuccess();

        void deleteDishSuccess();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void addDish(Dish dish);

        void updateDish(Dish dish);

        void deleteDish(String dishId);

        String getUnitName(String unitId);
    }
}