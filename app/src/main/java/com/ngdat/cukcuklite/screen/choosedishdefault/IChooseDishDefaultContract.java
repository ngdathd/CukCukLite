package com.ngdat.cukcuklite.screen.choosedishdefault;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Dish;

/**
 * MVP interface cho màn hình lựa chọn món ăn mặc định
 * Created at 18/04/2019
 */
public interface IChooseDishDefaultContract {

    interface IView extends IBaseView {
        void showMainScreen();

        void showDish(List<Dish> dishes);
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
