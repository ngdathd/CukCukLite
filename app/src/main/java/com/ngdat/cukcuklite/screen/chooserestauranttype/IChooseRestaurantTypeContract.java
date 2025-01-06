package com.ngdat.cukcuklite.screen.chooserestauranttype;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.models.RestaurantType;
import com.ngdat.cukcuklite.data.models.Unit;

/**
 * MVP interface cho màn hình lựa chọn quán ăn/nhà hàng
 * Created at 18/04/2019
 */
public interface IChooseRestaurantTypeContract {
    interface IView extends IBaseView {
        void showListRestaurantType(List<RestaurantType> restaurantTypeList);

        void showDishDefaultActivity();
    }

    interface IPresenter extends IBasePresenter<IView> {

        void insertAllUnit(List<Unit> units);

        void insertAllDish(List<Dish> dishes);
    }
}
